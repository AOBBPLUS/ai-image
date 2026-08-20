package com.myproj.code;

import com.alibaba.cloud.ai.graph.CompileConfig;
import com.alibaba.cloud.ai.graph.CompiledGraph;
import com.alibaba.cloud.ai.graph.NodeOutput;
import com.alibaba.cloud.ai.graph.StateGraph;
import com.alibaba.cloud.ai.graph.exception.GraphStateException;
import com.alibaba.cloud.ai.graph.streaming.StreamingOutput;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest(classes = StartApp.class)
public class EvaluationOptimizer {

    @Resource(name = "writeClient")
    private ChatClient writeClient;

    @Resource(name = "evaClient")
    private ChatClient evaClient;

    @Test
    public void test01() {
        String prompt = "请编写关于印度人的笑话。";
        String content = writeClient.prompt(prompt).call().content();
        boolean isPass = false;
        if (!isPass) {
            String result = evaClient.prompt("评审当前文案，如果通过则返回pass，否则返回不通过，而且同时返回改进的建议:" + content).call().content();
            if (result.contains("pass")) {
                isPass = true;
                System.out.println("评审通过，内容为：" + result);
            } else {
                System.out.println("进行优化");
                content = writeClient.prompt("请参考以下评估意见修改文案:" + result + "原文案：" + content).call().content();
            }
        }
        System.out.println("评审最终结果为：" + content);
    }
    @Resource
    private StateGraph evaluationOptimizerWorkflow;

    @Test
    public void text02() throws GraphStateException {
        // 编译执行工作流
        CompiledGraph compiledGraph = evaluationOptimizerWorkflow.compile(
                CompileConfig.builder().build()
        );
        NodeOutput lastOutput = compiledGraph.stream(Map.of("input", "请撰写一篇关于科技发展趋势的短文，内容涵盖人工智能和物联网，字数在 500 字左右，重点突出创新应用"))
                .doOnNext(nodeOutput -> {
                    if (nodeOutput instanceof StreamingOutput<?> streamingOutput) {
                        System.out.println("从节点输出：" + streamingOutput.node() + ":"
                                + streamingOutput.agent() + ":"
                                + streamingOutput.message().getText());
                    }
                })
                .blockLast();
        // 由于在评估后面添加的END，所以最后一次输出默认是评估的结果，这里通过data的方式获取到其中的content的结果，也就是作者写的文章
        AssistantMessage content = (AssistantMessage) lastOutput.state().data().get("content");
        System.out.println("最后一次输出：\n" + content.getText());
    }
}
