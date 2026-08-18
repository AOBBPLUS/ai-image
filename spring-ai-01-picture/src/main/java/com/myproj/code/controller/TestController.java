package com.myproj.code.controller;

import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.exception.UploadFileException;
import com.myproj.code.dto.PicForm;
import com.myproj.code.service.IPicService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/pic")
public class TestController {
    @Resource
    private IPicService picService;

    @PostMapping("/images")
    public String getImages(@RequestBody PicForm picForm) throws NoApiKeyException, UploadFileException {
        return picService.getImages(picForm);
    }
}
