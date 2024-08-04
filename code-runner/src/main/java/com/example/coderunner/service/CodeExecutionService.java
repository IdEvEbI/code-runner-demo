package com.example.coderunner.service;

import com.example.coderunner.model.CodeResponse;
import com.example.coderunner.model.CodeSubmission;
import com.example.coderunner.model.SubmissionToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
@Log4j2
@RequiredArgsConstructor
public class CodeExecutionService {

    @Value("${judge0.api.url}")
    private String judge0ApiUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    /**
     * 提交代码到 Judge0 API 获取 token
     *
     * @param codeSubmission 包含代码和相关参数的对象
     * @return SubmissionToken 提交代码后从 Judge0 API 返回的 token
     */
    public SubmissionToken submitCode(CodeSubmission codeSubmission) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 使用 base64 编码源代码
        String base64EncodedSourceCode = Base64.getEncoder().encodeToString(codeSubmission.getSource_code().getBytes());

        // 创建提交对象
        CodeSubmission submission = new CodeSubmission(
                base64EncodedSourceCode,
                codeSubmission.getLanguage_id(),
                codeSubmission.getStdin(),
                codeSubmission.getCompiler_options(),
                codeSubmission.getCommand_line_arguments(),
                codeSubmission.isRedirect_stderr_to_stdout()
        );

        try {
            // 手动序列化为 JSON 字符串
            String jsonRequest = objectMapper.writeValueAsString(submission);
            log.info("Request JSON: {}", jsonRequest);

            // 构建请求实体
            HttpEntity<String> request = new HttpEntity<>(jsonRequest, headers);

            // 发出 POST 请求
            ResponseEntity<String> response = restTemplate.postForEntity(judge0ApiUrl + "submissions?base64_encoded=true&wait=false", request, String.class);

            log.info("Received response from Judge0 API: {}", response);

            // 检查响应状态码
            if (response.getStatusCode() == HttpStatus.CREATED) {
                return objectMapper.readValue(response.getBody(), SubmissionToken.class);
            } else {
                log.error("Error response from Judge0 API: {}", response);
                throw new RuntimeException("Failed to get token from Judge0 API");
            }
        } catch (Exception e) {
            log.error("Error while submitting code to Judge0 API: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to submit code to Judge0 API", e);
        }
    }

    /**
     * 使用 token 从 Judge0 API 获取代码运行结果
     *
     * @param token 提交代码后从 Judge0 API 返回的 token
     * @return CodeResponse 从 Judge0 API 获取的运行结果
     */
    public CodeResponse fetchResult(String token) {
        String resultUrl = judge0ApiUrl + "submissions/" + token;
        log.info("Fetching result from Judge0 API: {}", resultUrl);

        // 循环请求以等待结果
        CodeResponse result;
        while (true) {
            result = restTemplate.getForObject(resultUrl, CodeResponse.class);
            if (result != null && result.getStatus().getId() != 1 && result.getStatus().getId() != 2) {
                break;
            }
            try {
                Thread.sleep(1000); // 等待 1 秒钟再请求
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return result;
    }
}
