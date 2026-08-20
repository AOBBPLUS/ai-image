package com.myproj.code.config;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EvaluationOptimizer {
    @Resource
    private DashScopeChatModel dashScopeChatModel;

    @Bean("writeClient")
    public ChatClient getWriteChatClient() {
        return ChatClient.builder(dashScopeChatModel)
                .defaultSystem("你是一名擅长使用简单易懂的方法去写作的搞笑文案写作家。")
                .build();
    }

    @Bean("evaClient")
    public ChatClient getEvaChatClient() {
        return ChatClient.builder(dashScopeChatModel)
                .defaultSystem("你是专业的文案评估师，熟知各类文案写作规范与技巧，能精准评判文案内容的逻辑性、创新性以及语言表达的流畅性")
                .build();
    }

}
