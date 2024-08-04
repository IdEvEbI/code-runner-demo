package com.example.coderunner.service;

import com.example.coderunner.exception.CodeExecutionException;
import com.example.coderunner.model.CodeResponse;
import com.example.coderunner.model.CodeSubmission;
import com.example.coderunner.model.SubmissionToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

        // 手动构建 JSON 字符串
        String jsonPayload = String.format(
                "{\"source_code\":\"%s\",\"language_id\":%d,\"stdin\":\"%s\",\"compiler_options\":\"%s\",\"command_line_arguments\":\"%s\",\"redirect_stderr_to_stdout\":%b}",
                base64EncodedSourceCode,
                codeSubmission.getLanguage_id(),
                codeSubmission.getStdin(),
                codeSubmission.getCompiler_options(),
                codeSubmission.getCommand_line_arguments(),
                codeSubmission.isRedirect_stderr_to_stdout()
        );

        HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

        log.info("开始提交代码 Judge0 API：{}", jsonPayload);
        try {
            SubmissionToken token = restTemplate.postForObject(judge0ApiUrl + "submissions?base64_encoded=true&wait=false", request, SubmissionToken.class);
            log.info("接收到 TOKEN：{}", token);
            return token;
        } catch (Exception e) {
            log.error("Error while submitting code to Judge0 API: {}", e.getMessage());
            throw new CodeExecutionException("提交代码出错：", e);
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
        log.info("开始获取执行结果 {}", resultUrl);

        try {
            CodeResponse result = restTemplate.getForObject(resultUrl, CodeResponse.class);
            return result;
        } catch (Exception e) {
            throw new CodeExecutionException("获取执行结果出错", e);
        }
    }
}