import { Routes, Route } from 'react-router-dom';
import '../../Bootstrap UI/bootstrap.min.css';
import Openpage from './Openpage';
import Offers from './Offers';
import NewAd from './NewAd';
import './App.css';

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
