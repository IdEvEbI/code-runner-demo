package com.example.coderunner.model;

import lombok.Data;

/**
 * 模型类，表示代码执行的响应内容
 */
@Data
public class CodeResponse {
    private String stdout; // 标准输出
    private String stderr; // 标准错误
    private String compile_output; // 编译输出
    private String message; // 消息
    private Status status; // 运行状态
    private double time; // 运行时间
    private int memory; // 内存使用

    @Data
    public static class Status {
        private int id; // 状态ID
        private String description; // 状态描述
    }
}