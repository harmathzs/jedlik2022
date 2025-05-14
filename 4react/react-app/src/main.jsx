import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import Openpage from './openpage.jsx';

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <Openpage />
  </StrictMode>,
)
