package com.myproj.code;

import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.agent.flow.agent.SupervisorAgent;
import com.alibaba.cloud.ai.graph.exception.GraphRunnerException;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest(classes = StartApp.class)
public class OrchestratorTest {

    @Resource
    private SupervisorAgent supervisorAgent;

    @Test
    public void test01() throws GraphRunnerException {
        Optional<OverAllState> invoke = supervisorAgent.invoke("请写一篇关于国庆出游的计划并以英文的形式给我。");
        System.out.println(invoke.get().value("translator_output"));
    }
}
