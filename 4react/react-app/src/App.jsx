import { useState } from 'react'
import { Routes, Route } from 'react-router-dom';
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import '../../Bootstrap UI/bootstrap.min.css'
import './App.css'

function App() {
  //const [count, setCount] = useState(0)

  return (
    <Routes>
      <Route path="/" element={<Openpage />} />
      <Route path="/offers" element={<Offers />} />
      <Route path="/newad" element={<NewAd />} />
    </Routes>
  );
}

export default App
