package com.myproj.code;

import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversation;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationParam;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationResult;
import com.alibaba.dashscope.common.MultiModalMessage;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.exception.UploadFileException;
import com.alibaba.dashscope.utils.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = StartApp.class)
public class MainTest {

    static {
        Constants.baseHttpApiUrl = "https://ws-2o9uw28fguatnoo9.cn-beijing.maas.aliyuncs.com/api/v1";
    }
    @Value("${spring.ai.dashscope.api-key}")
    private String apiKey;
    @Test
    public void test01(){


        // [方法一] 使用公网图像URL
        String imageUrl = "https://alidocs.oss-cn-zhangjiakou.aliyuncs.com/res/yBRq1ZPYEaXdyOdv/img/33a80a19-7ac7-4c64-b0fa-7d685b7046a0.png";

        // [方法二] 使用Base64编码图像
        // String imageUrl = encodeFile("/path/to/your/image.png");

        MultiModalConversation conv = new MultiModalConversation();
        MultiModalMessage userMessage = MultiModalMessage.builder()
                .role(Role.USER.getValue())
                .content(Arrays.asList(
                        Collections.singletonMap("image", imageUrl),
                        Collections.singletonMap("text", "帮我生成一张充满高级感的都市风格女性写真，画面中人物完美保留输入图片中这位年轻女性的面部特征与一头柔顺的黑色长发。人物换上一套彰显高雅气质的都市职场穿搭，场景设定在一家装修现代简约的高端咖啡店内。")
                ))
                .build();
        MultiModalConversationParam param = MultiModalConversationParam.builder()
                .apiKey("sk-ws-H.EIXXMMI.uETh.MEQCIAMXw95pODFPG7M3B1hh2NyXdhDJ0guXBzrR8RrsjliXAiByelQ39sPXkeByjGnutYZMr95tayClXq9VtKYP2Uk7Qw")
                .model("qwen-image-3.0-pro")
                .n(1)
                .messages(Arrays.asList(userMessage))
                .parameter("prompt_extend", true)
                .parameter("watermark",false)
                .build();
        try {
            MultiModalConversationResult result = conv.call(param);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test02(){
        MultiModalConversation conv = new MultiModalConversation();
        MultiModalMessage userMessage = MultiModalMessage.builder()
                .role(Role.USER.getValue())
                .content(Arrays.asList(
                        Collections.singletonMap("text", "帮我生成一张中世纪风格的村庄图片")
                ))
                .build();
        MultiModalConversationParam param = MultiModalConversationParam.builder()
                .apiKey("sk-ws-H.EIXXMMI.uETh.MEQCIAMXw95pODFPG7M3B1hh2NyXdhDJ0guXBzrR8RrsjliXAiByelQ39sPXkeByjGnutYZMr95tayClXq9VtKYP2Uk7Qw")
                .model("qwen-image-3.0-pro")
                .n(1)
                .messages(Arrays.asList(userMessage))
                .parameter("prompt_extend", true)
                .parameter("watermark",false)
                .build();
        try {
            MultiModalConversationResult result = conv.call(param);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test03() throws NoApiKeyException, UploadFileException {
        MultiModalConversation conv = new MultiModalConversation();
        MultiModalMessage userMessage = MultiModalMessage.builder().role(Role.USER.getValue())
                .content(Arrays.asList(
                        Collections.singletonMap("image", "https://help-static-aliyun-doc.aliyuncs.com/file-manage-files/zh-CN/20241022/emyrja/dog_and_girl.jpeg"),
                        Collections.singletonMap("text", "图中描绘的是什么景象?"))).build();
        MultiModalConversationParam param = MultiModalConversationParam.builder()
                // 若没有配置环境变量，请用百炼API Key将下行替换为：.apiKey("sk-xxx")
                // 各地域的API Key不同。获取API Key：https://help.aliyun.com/zh/model-studio/get-api-key
                .apiKey(apiKey)
                .model("qwen3.8-max")  // 此处以qwen3.8-max为例，可按需更换模型名称。模型列表：https://help.aliyun.com/zh/model-studio/models
                .messages(Arrays.asList(userMessage))
                .build();
        MultiModalConversationResult result = conv.call(param);
        System.out.println(result.getOutput().getChoices().get(0).getMessage().getContent().get(0).get("text"));
    }

    @Test
    public void test04() throws NoApiKeyException, UploadFileException {
        MultiModalConversation conv = new MultiModalConversation();
        MultiModalMessage userMessage = MultiModalMessage.builder().role(Role.USER.getValue())
                .content(Arrays.asList(
                        Collections.singletonMap("image", "https://help-static-aliyun-doc.aliyuncs.com/file-manage-files/zh-CN/20241022/emyrja/dog_and_girl.jpeg"),
                        Collections.singletonMap("image", "https://dashscope.oss-cn-beijing.aliyuncs.com/images/tiger.png"),
                        Collections.singletonMap("text", "这些图描绘了什么内容?"))).build();
        MultiModalConversationParam param = MultiModalConversationParam.builder()
                // 若没有配置环境变量，请用百炼API Key将下行替换为：.apiKey("sk-xxx")
                // 各地域的API Key不同。获取API Key：https://help.aliyun.com/zh/model-studio/get-api-key
                .apiKey(apiKey)
                .model("qwen3.8-max")  // 此处以qwen3.8-max为例，可按需更换模型名称。模型列表：https://help.aliyun.com/zh/model-studio/models
                .messages(Arrays.asList(userMessage))
                .build();
        MultiModalConversationResult result = conv.call(param);
        System.out.println(result.getOutput().getChoices().get(0).getMessage().getContent().get(0).get("text"));
    }

    @Test
    public void test05() throws NoApiKeyException, UploadFileException {
        MultiModalConversation conv = new MultiModalConversation();
        // fps 可参数控制视频抽帧频率，表示每隔 1/fps 秒抽取一帧，完整用法请参见：https://help.aliyun.com/zh/model-studio/use-qwen-by-calling-api?#2ed5ee7377fum
        Map<String, Object> params = new HashMap<>();
        params.put("video", "https://help-static-aliyun-doc.aliyuncs.com/file-manage-files/zh-CN/20241115/cqqkru/1.mp4");
        params.put("fps", 2);
        MultiModalMessage userMessage = MultiModalMessage.builder().role(Role.USER.getValue())
                .content(Arrays.asList(
                        params,
                        Collections.singletonMap("text", "这段视频的内容是什么?"))).build();
        MultiModalConversationParam param = MultiModalConversationParam.builder()
                // 若没有配置环境变量，请用百炼API Key将下行替换为：.apiKey("sk-xxx")
                // 各地域的API Key不同。获取API Key：https://help.aliyun.com/zh/model-studio/get-api-key
                .apiKey(apiKey)
                .model("qwen3.8-max")
                .messages(Arrays.asList(userMessage))
                .build();
        MultiModalConversationResult result = conv.call(param);
        System.out.println(result.getOutput().getChoices().get(0).getMessage().getContent().get(0).get("text"));
    }
}
