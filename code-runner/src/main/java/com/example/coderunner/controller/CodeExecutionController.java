package com.example.coderunner.controller;

import com.example.coderunner.model.CodeResponse;
import com.example.coderunner.model.CodeSubmission;
import com.example.coderunner.model.SubmissionToken;
import com.example.coderunner.service.CodeExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CodeExecutionController {

    private final CodeExecutionService codeExecutionService;

    @PostMapping("/run")
    public ResponseEntity<CodeResponse> runCode(@RequestBody CodeSubmission codeSubmission) {
        try {
            // 提交代码获取 token
            SubmissionToken tokenResponse = codeExecutionService.submitCode(codeSubmission);
            // 使用 token 获取运行结果
            CodeResponse response = codeExecutionService.fetchResult(tokenResponse.getToken());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
