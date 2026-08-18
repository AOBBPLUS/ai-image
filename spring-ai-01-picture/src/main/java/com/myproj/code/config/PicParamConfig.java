package com.myproj.code.config;

import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Data
@Configuration
public class PicParamConfig {


    @Value("${parameters.n}")
    private Integer n;

    @Value("${parameters.prompt_extend}")
    private Boolean promptExtent;

    @Value("${parameters.watermark}")
    private Boolean waterMark;

    @Value("${parameters.size}")
    private String size;

    @Bean(name = "params")
    public Map<String, Object> getParams() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("prompt_extend", promptExtent);
        params.put("watermark", waterMark);
        params.put("n", n);
        params.put("size", size);
        return params;
    }

    @Bean
    public MultiModalConversation getMultiModalConversation(){
        return new MultiModalConversation();
    }

}
