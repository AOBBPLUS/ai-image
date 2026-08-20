package com.myproj.code.config;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.graph.KeyStrategy;
import com.alibaba.cloud.ai.graph.KeyStrategyFactory;
import com.alibaba.cloud.ai.graph.StateGraph;
import com.alibaba.cloud.ai.graph.action.AsyncEdgeAction;
import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.exception.GraphStateException;
import com.alibaba.cloud.ai.graph.state.strategy.ReplaceStrategy;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class EvaluationOptimizer {
    @Resource
    private DashScopeChatModel dashScopeChatModel;

    @Bean("writeClient")
    public ChatClient getWriteChatClient() {
        return ChatClient.builder(dashScopeChatModel).defaultSystem("你是一名擅长使用简单易懂的方法去写作的搞笑文案写作家。").build();
    }

    @Bean("evaClient")
    public ChatClient getEvaChatClient() {
        return ChatClient.builder(dashScopeChatModel).defaultSystem("你是专业的文案评估师，熟知各类文案写作规范与技巧，能精准评判文案内容的逻辑性、创新性以及语言表达的流畅性").build();
    }

    @Bean("writeAgent")
    public ReactAgent getWriteAgent() {
        return ReactAgent.builder().name("writer") // agent名称
                .model(dashScopeChatModel) //LLM
                .instruction("你是一位经验丰富的科技文案写作者，完成客户的需求：{input}").outputKey("content").build();
    }

    @Bean("evaAgent")
    public ReactAgent getEvaAgent() {
        return ReactAgent.builder().name("evaluationOptimizer").model(dashScopeChatModel).instruction("你是十分严格的文案评估师，熟知各类文案写作规范与技巧，" + "能精准评判文案内容的逻辑性、创新性以及语言表达的流畅性，" + "确认通过在最后输出'WORK_SUCCESS'，" + "不通过在最后输出'WORK_FAIL'，" + "最少需要一次修正，" + "作为评估师只需要提出建议不需要修改文稿" + "下面是写作的文案：{content}").outputKey("result").build();
    }

    @Bean
    public StateGraph getGraph(ReactAgent writeAgent, ReactAgent evaAgent) throws GraphStateException {
        KeyStrategyFactory keyStrategyFactory = () -> {
            HashMap<String, KeyStrategy> keyStrategies = new HashMap<>();
            keyStrategies.put("input", new ReplaceStrategy());
            return keyStrategies;
        };
        // 创建工作流
        StateGraph stateGraphWorkflow = new StateGraph(keyStrategyFactory);
        //注册节点
        stateGraphWorkflow.addNode(writeAgent.name(), writeAgent.asNode(true, true));
        stateGraphWorkflow.addNode(evaAgent.name(), evaAgent.asNode(true, true));
        //开始节点
        stateGraphWorkflow.addEdge(StateGraph.START, writeAgent.name());
        //固定流程
        stateGraphWorkflow.addEdge(writeAgent.name(), evaAgent.name());
        stateGraphWorkflow.addConditionalEdges(  //条件分支
                evaAgent.name(), AsyncEdgeAction.edge_async(state -> {
                    AssistantMessage result = (AssistantMessage) state.data().get("result");
                    String text = result.getText();
                    System.out.println("输出：" + result);
                    if (text.contains("WORK_SUCCESS")) {
                        System.out.println("评审通过");
                        return "通过";
                    } else {
                        System.out.println("评审未通过");
                        return "修改";
                    }
                }), Map.of("通过", StateGraph.END, "修改", writeAgent.name())

        );
        return stateGraphWorkflow;

    }
}
