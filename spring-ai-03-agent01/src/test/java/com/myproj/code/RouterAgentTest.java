package com.myproj.code;

import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.agent.flow.agent.LlmRoutingAgent;
import com.alibaba.cloud.ai.graph.exception.GraphRunnerException;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest(classes = StartApp.class)
public class RouterAgentTest {
    @Resource
    private LlmRoutingAgent llmRoutingAgent;

    @Test
    public void test01() throws GraphRunnerException {
        Optional<OverAllState> invoke = llmRoutingAgent.invoke("请帮我写一篇关于国庆旅游的攻略。");
        System.out.println(invoke.get().value("writer_output"));
//        llmRoutingAgent.invoke("请帮我使用英文翻译一下文字："+writerOutput);
    }
}
