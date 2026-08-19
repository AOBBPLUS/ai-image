package com.myproj.code.advisor;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.AdvisorChain;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;

@Component
public class ModelChangeAdvisor implements BaseAdvisor {
    @Override
    public ChatClientRequest before(ChatClientRequest chatClientRequest, AdvisorChain advisorChain) {
        DashScopeChatOptions chatOptions =(DashScopeChatOptions) chatClientRequest.prompt().getOptions();
        ChatClientRequest newChatClientRequest = chatClientRequest;
        String contents = chatClientRequest.prompt().getContents();
        if(contents.length()>10){
            //长度>10切换模型
            chatOptions.setModel("deepseek-v4-pro-0813");
            newChatClientRequest  = chatClientRequest.mutate().prompt(
                   Prompt.builder()
                           .content(contents)
                           .chatOptions(chatOptions)
                           .build()
            ).build();
        }
        return newChatClientRequest;
    }

    @Override
    public ChatClientResponse after(ChatClientResponse chatClientResponse, AdvisorChain advisorChain) {
        return chatClientResponse;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
