package com.myproj.code.config;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.agent.flow.agent.LlmRoutingAgent;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RouterModel {
    @Resource
    private DashScopeChatModel dashScopeChatModel;

    @Bean("writerAgent")
    public ReactAgent writerAgent() {
        return ReactAgent.builder()
                .name("writer_agent")
                .model(dashScopeChatModel)
                .description("擅长撰写国内旅游的攻略")
                .instruction("你是一个知名的旅行家，擅长撰写国内旅游的攻略。请根据用户的提问进行回答。")
                .outputKey("writer_output")
                .build();
    }

    @Bean("reviewerAgent")
    public ReactAgent reviewerAgent() {
        return ReactAgent.builder()
                .name("reviewer_agent")
                .model(dashScopeChatModel)
                .description("擅长对文章进行评论、修改和润色")
                .instruction("你是一个知名的评论家，擅长对文章进行评论和修改。" +
                        "对于散文类文章，请确保文章中必须包含对于西湖风景的描述。")
                .outputKey("reviewer_output")
                .build();
    }

    @Bean("translatorAgent")
    public ReactAgent translatorAgent() {
        return ReactAgent.builder()
                .name("translator_agent")
                .model(dashScopeChatModel)
                .description("擅长将文章翻译成各种语言")
                .instruction("你是一个专业的翻译家，能够准确地将文章翻译成目标语言。")
                .outputKey("translator_output")
                .build();
    }

    @Bean
    public LlmRoutingAgent llmRoutingAgent(ReactAgent writerAgent, ReactAgent reviewerAgent, ReactAgent translatorAgent) {
        return LlmRoutingAgent.builder()
                .description("根据用户输入，智能选择对应Agent处理")
                .name("route_agent")
                .subAgents(List.of(writerAgent, reviewerAgent, translatorAgent))
                .model(dashScopeChatModel)
                .build();
    }
}
