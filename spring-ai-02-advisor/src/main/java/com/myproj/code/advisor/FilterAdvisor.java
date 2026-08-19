package com.myproj.code.advisor;

import cn.hutool.dfa.WordTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.AdvisorChain;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FilterAdvisor implements BaseAdvisor {

    private static final Logger logger = LoggerFactory.getLogger(FilterAdvisor.class);

    private static final WordTree WORD_TREE = new WordTree();

    static {
        List<String> words = List.of("嘻嘻", "哈哈");
        WORD_TREE.addWords(words);
    }
    @Override
    public ChatClientRequest before(ChatClientRequest chatClientRequest, AdvisorChain advisorChain) {
        String text = chatClientRequest.prompt().getUserMessage().getText();
        if(WORD_TREE.isMatch(text) ){
            logger.info("【拦截信息】：用户输入包含违禁词：{}",WORD_TREE.matchAll(text));
            throw new RuntimeException("用户输入内容违反规定！");
        }
        return chatClientRequest;
    }

    @Override
    public ChatClientResponse after(ChatClientResponse chatClientResponse, AdvisorChain advisorChain) {
        return chatClientResponse;
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
