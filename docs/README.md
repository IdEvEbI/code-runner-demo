# CodeRunner 开发记录

## 一、目标

开发一个支持**多种编程语言的在线代码编辑和运行平台**，用户可以选择编程语言，在线编写代码，并查看运行结果。

### 1. 技术选型

1. **前端**：`React`、`Monaco Editor`、`Axios`
2. **后端**：`Spring Boot`、`RestTemplate`、`Judge0 API`
3. **部署**：`Vercel`、`Heroku`

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

### 6. 总结

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
```

### 7. 创建控制器类

- 创建 `CodeExecutionController` 类，定义**提交代码**和**获取结果**

的接口：

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

### 9. 总结

通过本章的学习，我们完成了后端部分的开发，主要包括以下几点：

1. 初始化 Spring Boot 项目，配置必要的依赖和应用属性。
2. 定义模型类，用于表示代码提交请求和执行结果。
3. 实现与 Judge0 API 的交互，通过 `RestTemplate` 发送 HTTP 请求。
4. 使用全局异常处理器统一处理异常，简化控制器代码。
5. 创建服务类和控制器类，定义提交代码和获取结果的接口。

这样，我们的前后端联调可以实现用户在线编写代码，并获取运行结果的功能。在实际开发中，可以根据需求进一步优化和扩展功能。

<!-- TODO: 后续内容没有检查 -->

### 5. 整合前后端

前端通过 `axios` 发送请求到后端，并显示运行结果。确保前后端接口匹配，能够正常通信。

以上是完整的后端代码实现和配置步骤，包括了从项目初始化到最终运行的所有必要步骤。

# Code-runner-Demo 开发记录

## 一、目标

开发一个支持**多种编程语言的在线代码编辑和运行平台**，用户可以选择编程语言，在线编写代码，并查看运行结果。

### 1. 技术选型

1. **前端**：`React`、`Monaco Editor`、`Axios`
2. **后端**：`Spring Boot`、`RestTemplate`、`Judge0 API`
3. **部署**：`Vercel`、`Heroku`

### 2. 项目结构

```text
code-editor-platform/
├── docs/
├── frontend/
│   ├── public/
│   ├── src/
│   ├── package.json
│   ├── ...
├── backend/
│   ├── src/
│   ├── pom.xml
│   ├── ...
```

## 二、前端开发

### 1. 初始化 React 项目

- 使用 Create React App 创建项目

```bash
npx create-react-app frontend
cd frontend
```

- 项目目录结构介绍

### 2. 安装和配置 Monaco Editor

- 安装 Monaco Editor

```bash
npm install @monaco-editor/react
```

- 配置 Monaco Editor

```jsx
import Editor from '@monaco-editor/react';
```

### 3. 创建代码编辑器组件

- 编写 CodeEditor 组件

```jsx
import React, { useState } from 'react';
import Editor from '@monaco-editor/react';

const languages = ['java', 'c', 'cpp', 'python', 'javascript', 'typescript'];

function CodeEditor() {
  const [language, setLanguage] = useState('javascript');
  const [code, setCode] = useState('');

  return (
    <div>
      <select onChange={(e) => setLanguage(e.target.value)} value={language}>
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

- 实现语言选择功能

### 4. 创建代码运行功能

- 编写 RunCode 按钮组件

```jsx
import axios from 'axios';
import React, { useState } from 'react';

function RunCode({ language, code }) {
  const [output, setOutput] = useState('');

  const handleRun = async () => {
    const response = await axios.post('/api/run', { language, code });
    setOutput(response.data.output);
  };

  return (
    <div>
      <button onClick={handleRun}>Run</button>
      <pre>{output}</pre>
    </div>
  );
}

export default RunCode;
```

- 使用 Axios 发送 HTTP 请求到后端
- 显示代码运行结果

#### 三、后端部分

##### 1. 初始化 Spring Boot 项目

- 使用 Spring Initializr 创建项目
  - 添加 Spring Web 依赖
- 项目目录结构介绍

##### 2. 配置 Judge0 API 客户端

- 创建 CodeExecutionService 服务类

```java
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Service
public class CodeExecutionService {

    private final String JUDGE0_API_URL = "https://api.judge0.com/submissions?base64_encoded=false&wait=true";
    private final RestTemplate restTemplate;

    public CodeExecutionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String executeCode(String language, String sourceCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = String.format(
            "{\"source_code\": \"%s\", \"language_id\": %d, \"stdin\": \"\"}",
            sourceCode, getLanguageId(language)
        );

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(JUDGE0_API_URL, requestEntity, String.class);

        return responseEntity.getBody();
    }

    private int getLanguageId(String language) {
        switch (language) {
            case "java":
                return 62;
            case "c":
                return 50;
            case "cpp":
                return 54;
            case "python":
                return 71;
            case "javascript":
                return 63;
            case "typescript":
                return 74;
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }
}
```

- 配置 RestTemplate

##### 3. 创建代码执行接口

- 创建 CodeExecutionController 控制器类

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CodeExecutionController {

    private final CodeExecutionService codeExecutionService;

    @Autowired
    public CodeExecutionController(CodeExecutionService codeExecutionService) {
        this.codeExecutionService = codeExecutionService;
    }

    @PostMapping("/run")
    public String runCode(@RequestBody CodeExecutionRequest request) {
        return codeExecutionService.executeCode(request.getLanguage(), request.getCode());
    }
}

class CodeExecutionRequest {
    private String language;
    private String code;

    // Getters and setters
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
```

- 编写 CodeExecutionRequest 请求类

##### 4. 测试后端 API

- 使用 Postman 或类似工具测试 API

#### 四、前后端集成

##### 1. 配置前端请求后端服务

- 修改前端 Axios 配置

```jsx
axios.defaults.baseURL = 'http://localhost:8080';
```

- 测试前后端集成

##### 2. 部署前端应用

- 使用 Vercel 或 Netlify 部署 React 应用

##### 3. 部署后端应用

- 使用 Heroku 或 DigitalOcean 部署 Spring Boot 应用
- 自行部署 Judge0 实例（可选）

#### 五、结论

##### 1. 项目总结

我们成功开发了一个支持多种编程语言的在线代码编辑和运行平台，涵盖了前后端开发的各个步骤。

##### 2. 未来扩展思路

- 添加用户管理功能
- 支持更多编程语言
- 提高安全性

这个提纲涵盖了项目的各个方面，可以作为详细教程的基础。根据这个提纲，我们可以逐步开发完整的教程。
