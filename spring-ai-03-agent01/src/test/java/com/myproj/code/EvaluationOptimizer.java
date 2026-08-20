package com.myproj.code;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.test.context.SpringBootTest;

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
}
