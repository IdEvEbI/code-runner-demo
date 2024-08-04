package com.example.coderunner.controller;

import com.example.coderunner.exception.CodeExecutionException;
import com.example.coderunner.model.CodeResponse;
import com.example.coderunner.model.CodeSubmission;
import com.example.coderunner.model.SubmissionToken;
import com.example.coderunner.service.CodeExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CodeExecutionController {

    private final CodeExecutionService codeExecutionService;

    @PostMapping("/submit")
    public ResponseEntity<SubmissionToken> submitCode(@RequestBody CodeSubmission codeSubmission) {
        SubmissionToken tokenResponse = codeExecutionService.submitCode(codeSubmission);

        return ResponseEntity.ok(tokenResponse);
    }

    @GetMapping("/result/{token}")
    public ResponseEntity<CodeResponse> getResult(@PathVariable String token) {
        CodeResponse response = codeExecutionService.fetchResult(token);

        return ResponseEntity.ok(response);
    }
}