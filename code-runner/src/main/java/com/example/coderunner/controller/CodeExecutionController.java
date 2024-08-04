package com.example.coderunner.controller;

import com.example.coderunner.model.CodeResponse;
import com.example.coderunner.model.CodeSubmission;
import com.example.coderunner.model.SubmissionToken;
import com.example.coderunner.service.CodeExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
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