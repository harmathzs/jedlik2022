// src/components/Home.jsx
import React from 'react';
import { useNavigate } from 'react-router-dom';

export default function Home() {
  return (
    <div
      className="d-flex flex-column justify-content-center align-items-center vh-100"
      style={{
        minHeight: '100vh',
        width: '100vw',
        backgroundImage: "url('/real-estate-agent.png')",
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        backgroundRepeat: 'no-repeat',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center'
      }}
    >
      <div className="bg-white bg-opacity-75 p-4 rounded shadow text-center">
        <h1 className="mb-4">Üdvözöljük!</h1>
        <button
          className="btn btn-primary mb-3"
          onClick={() => useNavigate('/offers')}
        >
          Nézze meg kínálatunkat
        </button>
        <br />
        <button
          className="btn btn-success"
          onClick={() => useNavigate('/newad')}
        >
          Hirdessen nálunk!
        </button>
      </div>
    </div>
  );
}
