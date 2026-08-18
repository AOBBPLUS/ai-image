package com.myproj.code.service.impl;

import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversation;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationParam;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationResult;
import com.alibaba.dashscope.common.MultiModalMessage;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.exception.UploadFileException;
import com.alibaba.dashscope.utils.Constants;
import com.myproj.code.dto.PicForm;
import com.myproj.code.service.IPicService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class PicServiceImpl implements IPicService {
    @Value("${baseUrl}")
    private String baseUrl;

    @Resource
    private MultiModalConversation multiModalConversation;

    @Resource(name = "params")
    private Map<String, Object> params;

    @Override
    public String getImages(PicForm picForm) throws NoApiKeyException, UploadFileException {
        Constants.baseHttpApiUrl = baseUrl;
        MultiModalMessage userMessage = MultiModalMessage.builder()
                .role(Role.USER.getValue())
                .content(Arrays.asList(
                        Collections.singletonMap("text", picForm.getText())
                ))
                .build();
        MultiModalConversationResult call = multiModalConversation.call(MultiModalConversationParam.builder()
                .apiKey("sk-ws-H.EIXXMMI.uETh.MEQCIAMXw95pODFPG7M3B1hh2NyXdhDJ0guXBzrR8RrsjliXAiByelQ39sPXkeByjGnutYZMr95tayClXq9VtKYP2Uk7Qw")
                .model("qwen-image-3.0-pro")
                .messages(Arrays.asList(userMessage))
                .parameters(params)
                .build());
        return call.getOutput().getChoices().get(0).getMessage().getContent().get(0).get("image").toString();
    }
}
