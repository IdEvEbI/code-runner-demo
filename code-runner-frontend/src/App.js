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