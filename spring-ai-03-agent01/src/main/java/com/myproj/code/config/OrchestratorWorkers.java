package com.myproj.code.config;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.agent.flow.agent.SupervisorAgent;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

//编排者
@Configuration
public class OrchestratorWorkers {
    @Resource
    private DashScopeChatModel chatModel;

    @Bean
    public ReactAgent mainAgent() {
        return ReactAgent.builder()
                .description("内容创作任务的主协调者，负责理解需求、任务分解、调用专家并汇总结果")
                .instruction("""
                        你是内容创作任务的主管（Orchestrator），拥有以下专家团队：
                        - writer_agent：专业写作专家，擅长创作中文文章、散文、诗歌等
                        - translator_agent：专业翻译专家，能将文本准确翻译成英文或其他语言
                        
                        你的核心职责：
                        1. 仔细理解用户需求
                        2. 把任务分解成清晰的子步骤
                        3. 决定下一步需要调用哪些专家（可以一个或多个）
                        4. 只有所有子任务完成并拿到结果后，才给出最终答案
                        
                        输出规则（必须严格遵守，**只输出 JSON，不要写任何其他文字**）：
                        - 如果需要调用专家，输出一个 JSON 数组，列出要调用的专家名称，例如：
                          ["writer_agent"]
                          或
                          ["writer_agent", "translator_agent"]
                        - 如果任务已完成，所有结果已收集，输出空数组：
                          []
                          然后在下一次思考中直接给出最终完整答案。
                        
                        禁止自己直接写文章或翻译，必须通过调用工具完成。
                        永远只输出合法的 JSON 数组，不要加任何前缀、后缀、解释或 markdown。
                        
                        示例：
                        用户：写一篇关于春天的短文
                        你输出：["writer_agent"]
                        
                        用户：写一篇短文并翻译成英文
                        第一步：["writer_agent"]
                        第二步（拿到中文后）：["translator_agent"]
                        第三步（拿到英文后）：[]
                        然后输出最终中英文对照答案。
                        """)
                .model(chatModel)
                .outputKey("supervisor_plan")
                .name("main-agent")
                .build();
    }

    @Bean
    public SupervisorAgent supervisorAgent(ReactAgent mainAgent, ReactAgent writerAgent, ReactAgent translatorAgent) {
        return SupervisorAgent.builder()
                .name("supervisor_agent")
                .model(chatModel)
                .description("内容创作任务的监督协调者，负责分解任务、调度 writer 和 translator 等专家")
                .mainAgent(mainAgent)
                .subAgents(List.of(writerAgent, translatorAgent))
                .build();
    }

}
