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