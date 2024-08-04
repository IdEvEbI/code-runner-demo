package com.example.coderunner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 模型类，表示从 Judge0 API 返回的提交 token
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionToken {
    private String token;
}