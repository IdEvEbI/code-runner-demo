package com.example.coderunner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 模型类，表示代码提交请求的内容
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeSubmission {
    private String source_code; // base64 编码后的源代码
    private int language_id; // 编程语言ID
    private String stdin = ""; // 标准输入
    private String compiler_options = ""; // 编译器选项
    private String command_line_arguments = ""; // 命令行参数
    private boolean redirect_stderr_to_stdout = true; // 是否重定向标准错误到标准输出
}
