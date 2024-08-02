import axios from 'axios';
import React, { useState } from 'react';
import './RunCode.css'; // 引入样式文件

function RunCode({ language, code }) {
  const [output, setOutput] = useState('');

  const handleRun = async () => {
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