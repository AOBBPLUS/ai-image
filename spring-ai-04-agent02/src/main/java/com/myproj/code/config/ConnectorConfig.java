package com.myproj.code.config;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.agent.flow.agent.SequentialAgent;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ConnectorConfig {
    @Resource
    private DashScopeChatModel dashScopeChatModel;

    @Bean("writerAgent")
    public ReactAgent writerAgent() {
        return ReactAgent.builder()
                .name("writerAgent")
                .model(dashScopeChatModel)
                .description("专业写作Agent")
                .instruction("你是一个知名的作家，擅长写作和创作。请根据用户的提问进行回答：{input}。")
                .outputKey("article")
                .build();
    }

    @Bean("reviewerAgent")
    public ReactAgent reviewerAgent() {
        return ReactAgent.builder()
                .name("reviewerAgent")
                .model(dashScopeChatModel)
                .description("专业评审Agent")
                .instruction("你是一个知名的评论家，擅长对文章进行评论和修改。" +
                        "对于散文类文章，请确保文章中必须包含对于西湖风景的描述。待评论文章： {article}" +
                        "最终只返回修改后的文章，不要包含任何评论信息。")
                .outputKey("reviewed_article")
                .build();
    }

    @Bean("sequentialAgent")
    public SequentialAgent sequentialAgent(ReactAgent writerAgent,ReactAgent reviewerAgent){
        return SequentialAgent.builder()
                .name("sequentialAgent")
                .description("根据用户给定的主题写一篇文章，然后将文章交给评论员进行评论")
                .subAgents(List.of(writerAgent,reviewerAgent))
                .build();
    }

}
