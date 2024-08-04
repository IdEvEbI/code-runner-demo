package com.example.coderunner.model;

import lombok.Data;

/**
 * 模型类，表示从 Judge0 API 返回的提交 token
 */
@Data
public class SubmissionToken {
    private String token;
}