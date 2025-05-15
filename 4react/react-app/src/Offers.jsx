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
      <h2>Ingatlanok</h2>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Kategória</th>
            <th>Leírás</th>
            <th>Hirdetés dátuma</th>
            <th>Tehermentes</th>
            <th>Fénykép</th>
          </tr>
        </thead>
        <tbody>
          {ingatlanok.map(ing => (
            <tr key={ing.id}>
              <td>{ing.kategoriaNev}</td>
              <td>{ing.leiras}</td>
              <td>{ing.hirdetesDatuma}</td>
              <td>{ing.tehermentes ? "Igen" : "Nem"}</td>
              <td>
                <img src={ing.kepUrl} alt={ing.kategoriaNev} style={{maxWidth: "100px"}} />
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
}
