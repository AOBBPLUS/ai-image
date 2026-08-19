package com.myproj.code.config;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.myproj.code.advisor.CurrentLimitAdvisor;
import com.myproj.code.advisor.FilterAdvisor;
import com.myproj.code.advisor.LogAdvisor;
import com.myproj.code.advisor.ModelChangeAdvisor;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {
    @Resource
    private DashScopeChatModel dashScopeChatModel;

    @Resource
    private LogAdvisor logAdvisor;

    @Resource
    private FilterAdvisor filterAdvisor;

    @Resource
    private CurrentLimitAdvisor currentLimitAdvisor;

    @Resource
    private ModelChangeAdvisor modelChangeAdvisor;

    @Bean
    public ChatClient getChatClient() {
        return ChatClient.builder(dashScopeChatModel)
                .defaultAdvisors(logAdvisor, filterAdvisor, currentLimitAdvisor, modelChangeAdvisor)
                .build();
    }
}
