# CodeRunner 开发记录

## 一、目标

开发一个支持**多种编程语言的在线代码编辑和运行平台**，用户可以选择编程语言，在线编写代码，并查看运行结果。

### 1. 技术选型

1. **前端**：`React`、`Monaco Editor`、`Axios`
2. **后端**：`Spring Boot`、`RestTemplate`、`Judge0 API`
3. **附录**：`Judge0 环境部署`

### 2. 项目结构

```text
code-runner/
├── docs/
├── code-runner-frontend/
│   ├── public/
│   ├── src/
│   ├── package.json
│   ├── ...
├── code-runner/
│   ├── src/
│   ├── pom.xml
│   ├── ...
```

## 二、前端开发

本章的目标是开发前端部分，使用户能够**选择编程语言**、**编写代码**，**在线运行代码**查看结果。我们将使用 `React` 结合 `Monaco Editor` 实现代码编辑功能，并预留运行代码功能的接口，在后端开发完成后再做整合。

### 1. 初始化 React 项目

首先，我们使用 **Create React App** 创建项目，并简单介绍一下项目的目录结构。

- 使用 Create React App 创建项目

```bash
npx create-react-app code-runner-frontend
cd code-runner-frontend
```

- 项目目录结构介绍：

  - `public/`: 存放静态文件
  - `src/`: 存放源代码
  - `package.json`: 项目依赖配置

### 2. 安装和配置 Monaco Editor

接下来，我们安装 `Monaco Editor` 和 `Axios`，并配置代码编辑器组件。

- 安装 `Monaco Editor` 和 `Axios`

```bash
npm install @monaco-editor/react axios
```

- 创建 `src/components/CodeEditor.js` 文件，并编写以下代码：

```jsx
import React from 'react';
import Editor from '@monaco-editor/react';
import './CodeEditor.css'; // 引入样式文件

// 定义支持的编程语言
const languages = ['java', 'c', 'cpp', 'python', 'javascript', 'typescript'];

function CodeEditor({ language, setLanguage, code, setCode }) {
  return (
    <div className="code-editor">
      <select onChange={(e) => setLanguage(e.target.value)} value={language} className="language-select">
        {languages.map((lang) => (
          <option key={lang} value={lang}>{lang}</option>
        ))}
      </select>
      <Editor
        height="50vh"
        language={language}
        value={code}
        onChange={(value) => setCode(value)}
      />
    </div>
  );
}

export default CodeEditor;
```

- 创建 `src/components/CodeEditor.css` 文件，并添加一些基本样式：

```css
.code-editor {
  margin: 20px;
}

.language-select {
  margin-bottom: 10px;
  padding: 5px;
}
```

### 3. 创建代码运行功能

为了实现代码运行功能，我们预留一个运行按钮和输出显示区域，并使用假数据进行展示。后端开发完成后再整合实际运行功能。

- 创建 `src/components/RunCode.js` 文件，并编写以下代码：

```jsx
import axios from 'axios';
import React, { useState } from 'react';
import './RunCode.css'; // 引入样式文件

function RunCode({ language, code }) {
  const [output, setOutput] = useState('');

  // 预留运行代码的处理逻辑，后续会改为实际调用后端 API
  const handleRun = async () => {
    // TODO: 调整为实际调用后端 API，获取运行结果
    const response = await axios.post('/api/run', { language, code });
    setOutput(response.data.output);
  };

  return (
    <div className="run-code">
      <button onClick={handleRun} className="run-button">Run</button>
      <pre className="output">{output}</pre>
    </div>
  );
}

export default RunCode;
```

- 创建 `src/components/RunCode.css` 文件，并添加一些基本样式：

```css
.run-code {
  margin: 20px;
}

.run-button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  cursor: pointer;
}

.run-button:hover {
  background-color: #0056b3;
}

.output {
  margin-top: 10px;
  background-color: #f5f5f5;
  padding: 10px;
  border: 1px solid #ddd;
}
```

### 4. 整合组件并测试

我们将之前创建的组件整合到主应用组件中，并进行基础测试。

- 在 `src/App.js` 文件中，整合以上组件：

```jsx
import React, { useState } from 'react';
import CodeEditor from './components/CodeEditor';
import RunCode from './components/RunCode';
import './App.css'; // 引入全局样式文件

function App() {
  const [language, setLanguage] = useState('javascript');
  const [code, setCode] = useState('');

  return (
    <div className="app">
      <h1>CodeRunner</h1>
      <CodeEditor language={language} setLanguage={setLanguage} code={code} setCode={setCode} />
      <RunCode language={language} code={code} />
    </div>
  );
}

export default App;
```

- 创建 `src/App.css` 文件，并添加一些全局样式：

```css
.app {
  text-align: center;
}

h1 {
  margin-top: 20px;
  color: #333;
}
```

- 运行以下命令启动前端开发服务器：

```bash
npm start
```

此时，可以在浏览器中访问 [http://localhost:3000](http://localhost:3000) 查看效果，应该可以看到一个基本的代码编辑器界面，并可以选择编程语言和运行代码。

### 5. 基础测试

在开发环境中进行基础测试，确保代码编辑器和运行功能可以正常工作。建议使用以下步骤进行测试：

1. 打开浏览器，访问 [http://localhost:3000](http://localhost:3000)。
2. 在代码编辑器中选择编程语言并输入代码。

> 提示：点击 `Run` 按钮，查看运行结果功能，需要等后端代码开发完成后再做整合。

### 6. 小结

通过本章的学习，我们完成了前端部分的开发，主要包括以下几个方面：

1. 初始化 `React` 项目并配置必要的依赖。
2. 使用 `Monaco Editor` 实现代码编辑功能，并通过选择框实现编程语言的切换。
3. 预留代码运行功能的接口，待后端开发完成后再做整合。

本章为后端开发奠定了基础，前后端协同工作，使用户能够在线编写和运行代码，提升了代码运行平台的用户体验。

## 三、后端开发

本章目标是开发一个支持多种编程语言的在线代码编辑和运行平台的后端。我们将使用 Spring Boot 框架，通过与 Judge0 API 交互，实现代码的在线编写和执行。

### 1. 初始化 Spring Boot 项目

- 使用 `IDEA` 创建项目，基础配置如下：
  - **名称**: `code-runner`
  - **语言**: `Java`
  - **类型**: `Maven`
  - **组**: `com.example`
  - **工件**: `code-runner`
  - **软件包名称**: `com.example.coderunner`
  - **Spring Boot**: `3.3.2`
  - **Dependencies**:
    - `Spring Web`
    - `Spring Boot DevTools`
    - `Lombok`

- 在 `pom.xml` 文件中添加 `log4j2` 依赖并刷新：

  ```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-log4j2</artifactId>
  </dependency>
  ```

### 2. 配置 Judge0 API 客户端

在 `application.properties` 中配置 Judge0 API 的 URL：

```properties
spring.application.name=code-runner
judge0.api.url=http://192.168.227.149:2358/
```

### 3. 定义 RestTemplate

`RestTemplate` 是 Spring 提供的用于发送 HTTP 请求和接收响应的模板类。我们将其定义为一个 Bean，以便在整个应用中使用。

- 修改 `CodeRunnerApplication` 增加 `RestTemplate` 定义：

```java
package com.example.coderunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CodeRunnerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeRunnerApplication.class, args);
    }

    /**
     * 配置 RestTemplate Bean，用于发送 HTTP 请求
     *
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

### 4. 定义模型类

- 创建 `SubmissionToken` 类表示从 **Judge0 API** 返回的提交 `token`：

```java
package com.example.coderunner.model;

import lombok.Data;

/**
 * 模型类，表示从 Judge0 API 返回的提交 token
 */
@Data
public class SubmissionToken {
    private String token;
}
```

- 创建 `CodeSubmission` 类表示**代码提交请求**的内容：

```java
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
```

- 创建 `CodeResponse` 类表示代码执行的响应内容：

```java
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
```

### 5. 全局异常管理

为了简化控制器代码，并在应用中统一处理异常，我们将使用 Spring 的全局异常处理机制。

- 定义**自定义异常类** `CodeExecutionException`，用于程序**运行时抛出**自定义异常：

```java
package com.example.coderunner.exception;

public class CodeExecutionException extends RuntimeException {
    public CodeExecutionException(String message) {
        super(message);
    }

    public CodeExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

- 定义**全局异常处理器** `GlobalExceptionHandler`，用于对程序中的运行时异常做集中处理，简化控制器代码：

```java
package com.example.coderunner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * 全局异常处理器，统一处理程序中的异常
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理 CodeExecutionException 异常
     *
     * @param ex 异常对象
     * @param request Web 请求对象
     * @return 包含错误信息和状态码的 ResponseEntity
     */
    @ExceptionHandler(CodeExecutionException.class)
    public ResponseEntity<String> handleCodeExecutionException(CodeExecutionException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 处理所有其他异常
     *
     * @param ex 异常对象
     * @param request Web 请求对象
     * @return 包含错误信息和状态码的 ResponseEntity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex, WebRequest request) {
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

### 6. 创建服务类

- 创建 `CodeExecutionService` 类，用于处理**与 Judge0 API 的交互**：

```java
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

        log.info("开始提交代码到 Judge0 API：{}", jsonPayload);
        try {
            SubmissionToken token = restTemplate.postForObject(judge0ApiUrl + "submissions?base64_encoded=true&wait=false", request, SubmissionToken.class);
            log.info("接收到 TOKEN：{}", token);
            return token;
        } catch (Exception e) {
            log.error("提交代码到 Judge0 API 时出错：{}", e.getMessage());
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
```

### 7. 创建控制器类

- 创建 `CodeExecutionController` 类，定义**提交代码**和**获取结果**的接口：

```java
package com.example.coderunner.controller;

import com.example.coderunner.exception.CodeExecutionException;
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
public class CodeExecutionController {

    private final CodeExecutionService codeExecutionService;

    /**
     * 提交代码并获取 token
     *
     * @param codeSubmission 包含代码和相关参数的对象
     * @return ResponseEntity 包含 token 的响应
     */
    @PostMapping("/submit")
    public ResponseEntity<SubmissionToken> submitCode(@RequestBody CodeSubmission codeSubmission) {
        SubmissionToken tokenResponse = codeExecutionService.submitCode(codeSubmission);
        return ResponseEntity.ok(tokenResponse);
    }

    /**
     * 使用 token 获取代码执行结果
     *
     * @param token 提交代码后从 Judge0 API 返回的 token
     * @return ResponseEntity 包含代码执行结果的响应
     */
    @GetMapping("/result/{token}")
    public ResponseEntity<CodeResponse> getResult(@PathVariable String token) {
        CodeResponse response = codeExecutionService.fetchResult(token);
        return ResponseEntity.ok(response);
    }
}
```

### 8. 测试接口

现在可以使用 Postman 测试新的接口。

1. 提交代码获取 token：

   - URL: `http://localhost:8080/api/submit`
   - 方法: POST
   - 请求体:

   ```json
   {
     "source_code": "console.log('hello, world')",
     "language_id": 63,
     "stdin": "",
     "compiler_options": "",
     "command_line_arguments": "",
     "redirect_stderr_to_stdout": true
   }
   ```

   - 响应示例:

   ```json
   {
     "token": "ec5e4ca1-4a62-45c9-956e-855572226261"
   }
   ```

2. 使用 token 获取代码执行结果：

   - URL: `http://localhost:8080/api/result/{token}`
   - 方法: GET

   - 响应示例:

   ```json
   {
     "stdout": "hello, world\n",
     "stderr": null,
     "compile_output": null,
     "message": null,
     "status": {
       "id": 3,
       "description": "Accepted"
     },
     "time": 0.035,
     "memory": 7108
   }
   ```

### 9. 小结

通过本章的学习，我们完成了后端部分的开发，主要包括以下几点：

1. 初始化 Spring Boot 项目，配置必要的依赖和应用属性。
2. 定义模型类，用于表示代码提交请求和执行结果。
3. 实现与 Judge0 API 的交互，通过 `RestTemplate` 发送 HTTP 请求。
4. 使用全局异常处理器统一处理异常，简化控制器代码。
5. 创建服务类和控制器类，定义提交代码和获取结果的接口。

这样，我们的前后端联调可以实现用户在线编写代码，并获取运行结果的功能。在实际开发中，可以根据需求进一步优化和扩展功能。

## 四、前后端整合

本章目标是实现前后端的整合，使用户能够在前端编写代码并发送到后端执行，最终获取代码运行结果。我们将介绍前端和后端的调整和优化步骤，确保前后端联调顺畅。

### 1. 前端部分

在前端部分，我们需要完成以下几项工作：

1. 配置后端 API 地址。
2. 修改代码运行功能，使其能够**发送请求到后端 API 并获取代码运行结果**，并做一系列的优化，包括样式。

#### 1.1 配置后端 API 地址

在项目根目录下创建一个 `.env` 文件，配置后端 API 的地址：

```env
REACT_APP_API_URL=http://localhost:8080/api
```

#### 1.2 修改代码运行功能

- 修改 `src/components/RunCode.js` 文件，使其能够发送请求到后端 API 并获取代码运行结果：

```jsx
import axios from 'axios';
import React, { useState } from 'react';
import './RunCode.css'; // 引入样式文件

function RunCode({ language, code }) {
  const [output, setOutput] = useState('');
  const [isRunning, setIsRunning] = useState(false);

  const handleRun = async () => {
    if (!code.trim()) {
      setOutput('请输入要运行的代码。');
      return;
    }

    setIsRunning(true);
    setOutput('正在运行中...');

    try {
      // 发送代码到后端获取 token
      const submitResponse = await axios.post(`${process.env.REACT_APP_API_URL}/submit`, {
        source_code: code,
        language_id: languageToId(language),
        stdin: '',
        compiler_options: '',
        command_line_arguments: '',
        redirect_stderr_to_stdout: true
      });

      const { token } = submitResponse.data;

      // 使用 token 获取运行结果
      const resultResponse = await axios.get(`${process.env.REACT_APP_API_URL}/result/${token}`);
      const result = resultResponse.data;

      // 处理不同类型的输出
      setOutput(result.stdout || result.stderr || result.compile_output || result.message || '没有检测到输出内容。');
    } catch (error) {
      setOutput('错误: ' + error.message);
    } finally {
      setIsRunning(false);
    }
  };

  const languageToId = (language) => {
    const languageMap = {
      'java': 62,
      'c': 50,
      'cpp': 54,
      'python': 71,
      'javascript': 63,
      'typescript': 74
    };
    return languageMap[language];
  };

  return (
    <div className="run-code">
      <button onClick={handleRun} className="run-button" disabled={isRunning || !code.trim()}>
        {isRunning ? '正在运行中...' : '运行'}
      </button>
      <pre className="output">{output}</pre>
    </div>
  );
}

export default RunCode;
```

- 修改 `src/components/RunCode.css` 文件，增加**运行按钮被禁用**的样式：

```css
.run-button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}
```

### 2. 后端部分

在后端部分，我们需要完成以下几项工作：

1. 在 `CodeExecutionController` 增加 `@CrossOrigin(origins = "http://localhost:3000")` 解决跨域问题。
2. 增加 `UTF-8` 编码，以解决传递给 Judge API 的代码中包含中文的问题，其中包括：
   1. 修改 `CodeExecutionService` 中的 `submitCode` 方法，使 `HttpHeaders` 接收 UTF-8 编码。
   2. 修改 `CodeExecutionService` 中的 `submitCode` 方法，使 `base64EncodedSourceCode` 增加 `StandardCharsets.UTF_8`。
   3. 修改 `CodeExecutionService` 中的 `fetchResult` 方法，增加 `?base64_encoded=true` 请求参数，并增加了 base64 解码。
3. 修改 `CodeExecutionService` 中的 `fetchResult` 方法，增加 `while` 循环避免前端请求发送太快，`Judge0 API` 没有足够的时间计算出结果。

#### 2.1 解决跨域问题

在 `CodeExecutionController` 类上增加 `@CrossOrigin` 注解：

```java
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CodeExecutionController {
```

#### 2.2 创建 Base64 工具类

- 创建 `Base64Util` 工具类负责 `base64` 的编解码工作：

```java
package com.example.coderunner.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Util {

    public static String encode(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String decode(String input) {
        return new String(Base64.getDecoder().decode(input), StandardCharsets.UTF_8);
    }
}
```

#### 2.3 修改 `submitCode` 方法

- 修改 `CodeExecutionService` 调用 `submitCode`，增加 `UTF-8` 编码的接收：

```java
/**
 * 提交代码到 Judge0 API 获取 token
 *
 * @param codeSubmission 包含代码和相关参数的对象
 * @return SubmissionToken 提交代码后从 Judge0 API 返回的 token
 */
public SubmissionToken submitCode(CodeSubmission codeSubmission) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
    headers.setAccept(Collections.singletonList(new MediaType("application", "json", StandardCharsets.UTF_8)));

    // 使用 Base64 编码源代码
    String base64EncodedSourceCode = Base64Util.encode(codeSubmission.getSource_code());

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

    log.info("开始提交代码到 Judge0 API：{}", jsonPayload);
    try {
        SubmissionToken token = restTemplate.postForObject(judge0ApiUrl + "submissions?base64_encoded=true&wait=false", request, SubmissionToken.class);
        log.info("接收到 TOKEN：{}", token);
        return token;
    } catch (Exception e) {
        log.error("提交代码到 Judge0 API 时出错：{}", e.getMessage());
        throw new CodeExecutionException("提交代码出错：", e);
    }
}
```

#### 2.4 修改 `fetchResult` 方法

- 修改 `CodeExecutionService` 调用 `fetchResult`，增加循环避免前端请求太快，查询不到结果问题，同时增加 `base64` 解码功能：

```java
/**
 * 使用 token 从 Judge0 API 获取代码运行结果
 *
 * @param token 提交代码后从 Judge0 API 返回的 token
 * @return CodeResponse 从 Judge0 API 获取的运行结果
 */
public CodeResponse fetchResult(String token) {
    String resultUrl = judge0ApiUrl + "submissions/" + token + "?base64_encoded=true";
    log.info("开始获取执行结果 {}", resultUrl);

    while (true) {
        try {
            CodeResponse result = restTemplate.getForObject(resultUrl, CodeResponse.class);
            log.info("从 Judge0 API 获取的原始结果：{}", result);

            if (result != null && result.getStatus().getId() != 1) {
                // 解码 Base64 字段
                result.setStdout(Base64Util.decode(result.getStdout()));
                result.setStderr(Base64Util.decode(result.getStderr()));
                result.setCompile_output(Base64Util.decode(result.getCompile_output()));
                result.setMessage(Base64Util.decode(result.getMessage()));

                log.info("解码后的结果：{}", result);
                return result;
            }
            Thread.sleep(1000); // 等待 1 秒钟再请求
        } catch (Exception e) {
            log.error("从 Judge0 API 获取结果时出错：{}", e.getMessage());
            throw new CodeExecutionException("获取执行结果出错", e);
        }
    }
}
```

### 3. 小结

通过本章的学习，我们完成了前后端的整合，具体工作包括：

1. 前端配置后端 API 地址，修改代码运行功能和代码编辑功能。
2. 后端解决跨域问题，修改提交和获取代码结果的方法，使其支持 `UTF-8` 编码和 `base64` 编解码。

通过这些调整，用户能够在前端编写代码并发送到后端执行，最终获取代码运行结果。这样不仅提升了代码运行平台的用户体验，还确保了代码执行的稳定性和准确性。

## 五、附录：Judge0 环境部署

本章目标是通过 Docker 完成 Judge0 环境的部署，使其能够作为后端服务接受代码提交并返回执行结果。本文将详细介绍从下载镜像到启动 Judge0 环境的具体步骤。

### 1. 介绍

Judge0 是一个开源的在线判题系统，支持多种编程语言的代码编译和运行。为了确保我们的系统能够处理和运行用户提交的代码，我们需要在本地或服务器上配置 Judge0 环境。以下步骤将指导你如何使用 Docker 镜像部署 Judge0 环境，包括 Redis、Postgres 和 Judge0 三个部分。

### 2. 步骤

#### 2.1 在主机上下载镜像

首先，我们需要在主机上下载所需的 Docker 镜像。

1. **下载 Redis 镜像**：

    ```bash
    docker pull redis:7.2.4
    ```

2. **下载 Postgres 镜像**：

    ```bash
    docker pull postgres:16.2
    ```

3. **下载 Judge0 镜像**：

    ```bash
    docker pull judge0/judge0:1.13.1
    ```

#### 2.2 保存镜像

下载完成后，我们将镜像保存为文件，以便传输到其他虚拟机或服务器。

1. **保存 Redis 镜像**：

    ```bash
    docker save -o redis_7.2.4.tar redis:7.2.4
    ```

2. **保存 Postgres 镜像**：

    ```bash
    docker save -o postgres_16.2.tar postgres:16.2
    ```

3. **保存 Judge0 镜像**：

    ```bash
    docker save -o judge0_1.13.1.tar judge0/judge0:1.13.1
    ```

#### 2.3 传输镜像到虚拟机

使用 `scp` 或其他工具将镜像文件传输到虚拟机。例如：

```bash
scp redis_7.2.4.tar postgres_16.2.tar judge0_1.13.1.tar your_username@your_vm_ip:/path/to/destination
```

#### 2.4 在虚拟机上加载镜像

在虚拟机上加载镜像：

1. **加载 Redis 镜像**：

    ```bash
    docker load -i /path/to/destination/redis_7.2.4.tar
    ```

2. **加载 Postgres 镜像**：

    ```bash
    docker load -i /path/to/destination/postgres_16.2.tar
    ```

3. **加载 Judge0 镜像**：

    ```bash
    docker load -i /path/to/destination/judge0_1.13.1.tar
    ```

#### 2.5 启动 Redis 和 Postgres

接下来，启动 Redis 和 Postgres 服务：

1. **启动 Redis**：

    ```bash
    docker run -d --name redis --network judge0-network redis:7.2.4
    ```

2. **启动 Postgres**：

    ```bash
    docker run -d --name judge0-postgres --network judge0-network \
      -e POSTGRES_USER=judge0 \
      -e POSTGRES_PASSWORD=itheima \
      -e POSTGRES_DB=judge0 \
      postgres:16.2
    ```

#### 2.6 启动 Judge0

最后，启动 Judge0 服务：

1. **启动 Judge0**：

    ```bash
    docker run -d --name judge0-server --network judge0-network --privileged \
      -e POSTGRES_HOST=judge0-postgres \
      -e POSTGRES_PORT=5432 \
      -e POSTGRES_USER=judge0 \
      -e POSTGRES_PASSWORD=itheima \
      -e POSTGRES_DB=judge0 \
      -e REDIS_HOST=redis \
      -e REDIS_PORT=6379 \
      -p 2358:2358 judge0/judge0:1.13.1
    ```

2. **初始化 Judge0 环境**：

    ```bash
    docker exec -it judge0-server sh -c "sudo mkdir -p /box && sudo chown judge0:judge0 /box"
    docker exec -it judge0-server sh -c "sudo /usr/local/bin/isolate --init"
    docker exec -it judge0-server sh -c "sudo mkdir -p /api/log && sudo touch /api/log/development.log && sudo chmod 0664 /api/log/development.log && sudo chown judge0:judge0 /api/log/development.log"
    ```

3. **启动 Resque Worker**：

    ```bash
    docker exec -it judge0-server sh -c "nohup rake resque:work QUEUE=* > /api/log/resque.log 2>&1 &"
    ```

4. **进入容器以确保 Resque Worker 在后台运行**：

    ```bash
    docker exec -it judge0-server /bin/sh
    nohup rake resque:work QUEUE=* &
    exit
    ```

5. **检查 Resque Worker 是否在后台运行**：

    ```bash
    docker exec -it judge0-server ps aux | grep resque
    ```

### 3. 测试提交

部署完成后，我们可以测试提交代码以确保 Judge0 正常工作。

1. **提交代码**：

    ```bash
    curl -v -X POST 'http://192.168.227.149:2358/submissions?base64_encoded=true&wait=false' \
      -H 'Content-Type: application/json' \
      --data-raw '{"source_code":"Y29uc29sZS5sb2coImhlbGxvLCB3b3JsZCIpOw==","language_id":63,"stdin":"","compiler_options":"","command_line_arguments":"","redirect_stderr_to_stdout":true}'
    ```

2. **检查提交状态**：

    ```bash
    curl 'http://192.168.227.149:2358/submissions/{token}'
    ```

将 `{token}` 替换为实际的令牌值。

### 4. 小结

通过本章的学习，我们完成了 Judge0 环境的 Docker 部署，具体步骤包括：

1. 在主机上下载所需的 Docker 镜像。
2. 保存镜像文件并传输到虚拟机。
3. 在虚拟机上加载镜像并启动 Redis、Postgres 和 Judge0 服务。
4. 初始化 Judge0 环境并启动 Resque Worker。
5. 测试提交代码，确保 Judge0 正常工作。

这些步骤确保了我们的系统能够处理和运行用户提交的代码，为前后端整合提供了坚实的基础。如果在部署过程中遇到任何问题，请及时进行排查和调整。

## 总结

通过本文档的学习和实践，我们完成了一个支持多种编程语言的在线代码编辑和运行平台的开发和部署。主要包括以下几个方面：

1. **前端开发**：使用 `React` 和 `

Monaco Editor` 实现代码编辑和运行功能。
2. **后端开发**：使用 `Spring Boot` 实现与 `Judge0 API` 的交互，处理代码提交和获取执行结果。
3. **前后端整合**：确保前后端能够顺畅地联调，用户可以在线编写代码并查看运行结果。
4. **Judge0 环境部署**：通过 Docker 部署 Judge0 环境，使系统能够处理和运行用户提交的代码。

通过本项目的开发，我们不仅掌握了前后端技术的基本使用，还学会了如何进行前后端的整合，以及如何通过 Docker 部署后台服务。希望通过这些内容，大家能够对前后端开发、整合以及后台服务的部署有一个更全面的了解。
