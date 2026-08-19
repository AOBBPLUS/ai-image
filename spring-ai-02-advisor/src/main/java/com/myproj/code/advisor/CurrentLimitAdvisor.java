package com.myproj.code.advisor;

import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.AdvisorChain;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.stereotype.Component;

@Component
public class CurrentLimitAdvisor implements BaseAdvisor {

    private static final Logger logger = LoggerFactory.getLogger(CurrentLimitAdvisor.class);

    //生成令牌,没鸟两个
    private final RateLimiter rateLimiter = RateLimiter.create(2.0);

    @Override
    public ChatClientRequest before(ChatClientRequest chatClientRequest, AdvisorChain advisorChain) {
        String userInput = chatClientRequest.prompt().getUserMessage().getText();
        if(!rateLimiter.tryAcquire()){
            logger.info("【限流】：请求被拒绝，用户输入: {}",userInput);
            throw new RuntimeException("系统繁忙，请稍后再试（限流触发）");
        }
        return chatClientRequest;
    }

    @Override
    public ChatClientResponse after(ChatClientResponse chatClientResponse, AdvisorChain advisorChain) {
        return chatClientResponse;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
