import React, { useState, useEffect } from 'react';
import '../../Bootstrap UI/bootstrap.min.css';

export default function NewAd() {
    const [kategoriak, setKategoriak] = useState([]);
    const [showError, setShowError] = useState(false);
    const [errorText, setErrorText] = useState('Hiba szövege');

    useEffect(() => {
        async function fetchData() {
          try {
            // Párhuzamosan lefuttatjuk mindkét fetch kérést
            const [kategoriaResponse, ingatlanResponse] = await Promise.all([
              fetch('http://localhost:5000/api/kategoriak'),
              fetch('http://localhost:5000/api/ingatlan')
            ]);
    
            const kategoriakData = await kategoriaResponse.json();
            const ingatlanokData = await ingatlanResponse.json();
    
            setKategoriak(kategoriakData);
            //setIngatlanok(ingatlanokData);
          } catch (err) {
            console.warn(err);
          } finally {
            //setLoading(false);
          }
        };
    
        fetchData();
      }, []);

    useEffect(() => {
        if (kategoriak.length > 0) {
          console.log('Kategóriák betöltve:', kategoriak);
        }
      }, [kategoriak]);

    return (
        <div className="container">
            <h2 className="mb-4 text-center">Új hirdetés elküldése</h2>
            <div className="row">
                <div className="offset-lg-3 offset-md-2 col-lg-6 col-md-8 col-12">
                    <div className="mb-3">
                        <label htmlFor="category" className="form-label">Ingatlan kategóriája</label>
                        <select className="form-select" name="kategoriaId">
                            <option value="0">Kérem válasszon</option>
                            <option value="1">Ház</option>
                            <option value="2">Lakás</option>
                            <option value="3">Építési telek</option>
                            <option value="4">Garázs</option>
                            <option value="5">Mezőgazdasági terület</option>
                            <option value="6">Ipari ingatlan</option>
                        </select>
                    </div>

                    <div className="mb-3">
                        <label htmlFor="date" className="form-label">Hirdetés dátuma</label>
                        <input type="date" className="form-control" name="hirdetesDatuma" readOnly={true} defaultValue={new Date().toISOString().slice(0, 10)} />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="description" className="form-label">Ingatlan leírása</label>
                        <textarea className="form-control" name="leiras" rows="3"></textarea>
                    </div>
                    <div className="form-check mb-3">
                        <input className="form-check-input" type="checkbox" name="tehermentes" defaultChecked="true" />
                        <label className="form-check-label" htmlFor="creditFree">Tehermentes ingatlan</label>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="pictureUrl" className="form-label">Fénykép az ingatlanról</label>
                        <input type="url" className="form-control" name="kepUrl" />
                    </div>
                    <div className="mb-3 text-center">
                        <button className="btn btn-primary px-5">Küldés</button>
                    </div>

                    {showError && (
                    <div className="alert alert-danger alert-dismissible" role="alert">
                        <strong>{errorText}</strong>
                        <button
                        type="button"
                        className="btn-close"
                        onClick={() => setShowError(false)}
                        aria-label="Bezárás"
                        ></button>
                    </div>
                    )}

                </div>
            </div>
        </div>
    );
}