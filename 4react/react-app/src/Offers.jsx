import React, { useState, useEffect } from 'react';
import { Table } from 'react-bootstrap';
import '../../Bootstrap UI/bootstrap.min.css';
import './App.css';

export default function Offers() {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(()=>{
      async function fetchKategoriak() {
        const res = await fetch('http://localhost:5000/api/kategoriak');
        const resBody = await res.json();
        console.log('fetchKategoriak resBody', resBody);
        setLoading(false);
      }
      async function fetchIngatlanok() {
        const res = await fetch('http://localhost:5000/api/ingatlan');
        const resBody = await res.json();
        console.log('fetchIngatlanok resBody', resBody);
        setLoading(false);
      }

      try {
        fetchKategoriak();
        fetchIngatlanok();
      } catch (err) {
        console.warn(err);
      }
    }, []);

    

    return (
      <div className="container mt-4">
        <h2>Ingatlanok listája</h2>
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Cím</th>
              <th>Típus</th>
              <th>Ár</th>
              <th>Méret</th>
            </tr>
          </thead>
          <tbody>
          </tbody>
        </Table>
      </div>
    );

  /* 
            {data.map(item => (
            <tr key={item.id}>
              <td>{item.cim}</td>
              <td>{item.tipus}</td>
              <td>{item.ar} Ft</td>
              <td>{item.m2} m²</td>
            </tr>
          ))}

  */
}