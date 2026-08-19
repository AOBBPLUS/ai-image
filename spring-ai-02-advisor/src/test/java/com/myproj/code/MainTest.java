package com.myproj.code;

import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversation;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationParam;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationResult;
import com.alibaba.dashscope.common.MultiModalMessage;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.exception.UploadFileException;
import com.alibaba.dashscope.utils.Constants;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = StartApp.class)
public class MainTest {

    @Resource
    private ChatClient chatClient;

    @Test
    public void test01() {
        ChatClient.CallResponseSpec call = chatClient.prompt()
                .user("今天天气如何？")
                .call();
        System.out.println(call.content());
    }

    @Test
    public void test02() {
        ChatClient.CallResponseSpec call = chatClient.prompt()
                .user("今天天气如何？嘻嘻")
                .call();
        System.out.println(call.content());
    }

    // 使用多线程的方式来请求测试限流
    @Test
    public void test03() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(chatClient.prompt()
                        .user("你好,嘻嘻")
                        .call()
                        .content());
            }).start();
        }
    }

    @Test
    public void test04() {
        System.out.println(chatClient.prompt()
                .user("你好啊，介绍一下你自己")
                .call()
                .content());
    }
}
