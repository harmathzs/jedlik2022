import React, { useState, useEffect } from 'react';
import { Table } from 'react-bootstrap';
import '../../Bootstrap UI/bootstrap.min.css';
import './App.css';

export default function Offers() {
  const [kategoriak, setKategoriak] = useState([]);
  const [ingatlanok, setIngatlanok] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        // Párhuzamosan lefuttatjuk mindkét fetch kérést
        const [kategoriaResponse, ingatlanResponse] = await Promise.all([
          fetch('http://localhost:5000/api/kategoriak'),
          fetch('http://localhost:5000/api/ingatlan')
        ]);

        const kategoriakData = await kategoriaResponse.json();
        const ingatlanokData = await ingatlanResponse.json();

        setKategoriak(kategoriakData);
        setIngatlanok(ingatlanokData);
      } catch (err) {
        console.warn(err);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  // Ezek a useEffect-ek fogják jelezni az állapotváltozásokat
  useEffect(() => {
    if (kategoriak.length > 0) {
      console.log('Kategóriák betöltve:', kategoriak);
    }
  }, [kategoriak]);

  useEffect(() => {
    if (ingatlanok.length > 0) {
      console.log('Ingatlanok betöltve:', ingatlanok);
    }
  }, [ingatlanok]);

  return (
    <div>
      {loading ? (
        <div>Betöltés...</div>
      ) : (
        <>
          {/* Kategóriák megjelenítése */}
          <div>
            <h3>Kategóriák</h3>
            <ul>
              {kategoriak.map(kategoria => (
                <li key={kategoria.id}>{kategoria.nev}</li>
              ))}
            </ul>
          </div>

          {/* Ingatlanok megjelenítése */}
          <div>
            <h3>Ingatlanok</h3>
            <ul>
              {ingatlanok.map(ingatlan => (
                <li key={ingatlan.id}>
                  {ingatlan.cim} - {ingatlan.ar} Ft
                </li>
              ))}
            </ul>
          </div>
        </>
      )}
    </div>
  );
}
