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