package com.myproj.code.service;

import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.exception.UploadFileException;
import com.myproj.code.dto.PicForm;

import java.util.List;

public interface IPicService {
    String getImages(PicForm picForm) throws NoApiKeyException, UploadFileException;
}
