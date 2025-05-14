// src/components/Home.jsx
import React from 'react';
import { useNavigate } from 'react-router-dom';

export default function Home() {
  //const navigate = useNavigate(); // Navigációs függvény a route-okhoz

  return (
    <div
      className="d-flex flex-column justify-content-center align-items-center vh-100"
      style={{
        backgroundImage: "url('/real-estate-agent.png')",
        backgroundSize: 'cover',
        backgroundPosition: 'center'
      }}
    >
      <div className="bg-white bg-opacity-75 p-4 rounded shadow text-center">
        <h1 className="mb-4">Üdvözöljük!</h1>
        <button
          className="btn btn-primary mb-3"
          onClick={() => navigate('/offers')}
        >
          Nézze meg kínálatunkat
        </button>
        <br />
        <button
          className="btn btn-success"
          onClick={() => navigate('/newad')}
        >
          Hirdessen nálunk!
        </button>
      </div>
    </div>
  );
}
