package com.myproj.code.dto;

import lombok.Data;

import java.util.List;

@Data
public class PicForm {
    private String text;
    private List<String> pics;
}
