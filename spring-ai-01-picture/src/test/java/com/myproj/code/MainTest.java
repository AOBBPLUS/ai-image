package com.myproj.code;

import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversation;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationParam;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationResult;
import com.alibaba.dashscope.common.MultiModalMessage;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.utils.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;

@SpringBootTest(classes = StartApp.class)
public class MainTest {

    @Test
    public void test01(){
        Constants.baseHttpApiUrl = "https://ws-2o9uw28fguatnoo9.cn-beijing.maas.aliyuncs.com/api/v1";

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
        Constants.baseHttpApiUrl = "https://ws-2o9uw28fguatnoo9.cn-beijing.maas.aliyuncs.com/api/v1";

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

}
