package com.myproj.code.advisor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.AdvisorChain;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.stereotype.Component;

@Component
public class LogAdvisor implements BaseAdvisor {

    private static final Logger logger = LoggerFactory.getLogger(LogAdvisor.class);

    /**
     * 调用LLM之前
     *
     * @param chatClientRequest
     * @param advisorChain
     * @return
     */
    @Override
    public ChatClientRequest before(ChatClientRequest chatClientRequest, AdvisorChain advisorChain) {
        logger.info("【请求日志】，用户输入{}，调用LLM为{}", chatClientRequest.prompt().getUserMessage().getText(), chatClientRequest.prompt().getOptions().getModel());
        return chatClientRequest;
    }

    /**
     * 调用LLM之后
     *
     * @param chatClientResponse
     * @param advisorChain
     * @return
     */
    @Override
    public ChatClientResponse after(ChatClientResponse chatClientResponse, AdvisorChain advisorChain) {
        logger.info("【响应日志】：AI响应：{}", chatClientResponse.chatResponse().getResult().getOutput().getText());
        return chatClientResponse;
    }

    /**
     * 执行优先级，非负数，越小优先级越高
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 2;
    }
}
