package com.myproj.code;

import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.agent.flow.agent.SequentialAgent;
import com.alibaba.cloud.ai.graph.exception.GraphRunnerException;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest(classes = StartApp.class)
public class ConnectorTest {

    @Resource
    private SequentialAgent sequentialAgent;

    @Test
    public void test01() throws GraphRunnerException {
        Optional<OverAllState> result = sequentialAgent.invoke("帮我写一个100字左右的散文");
        if (result.isPresent()) {
            OverAllState state = result.get();
            // 访问第一个Agent的输出
            state.value("article").ifPresent(article -> {
                if (article instanceof AssistantMessage) {
                    System.out.println("原始文章: " + ((AssistantMessage) article).getText());
                }
            });
            // 访问第二个Agent的输出
            state.value("reviewed_article").ifPresent(reviewedArticle -> {
                if (reviewedArticle instanceof AssistantMessage) {
                    System.out.println("评审后文章: " + ((AssistantMessage) reviewedArticle).getText());
                }
            });
        }
        System.out.println(result);
    }

}
