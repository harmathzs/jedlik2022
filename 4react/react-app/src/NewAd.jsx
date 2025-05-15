import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../../Bootstrap UI/bootstrap.min.css';

export default function NewAd() {
    const navigate = useNavigate(); // 2. Hook inicializálása

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

    async function handleSubmit(event) {
        console.log('handleSubmit event.target', event?.target);

        event.preventDefault();
        const form = event.target;

        const formData = {
            kategoriaId: parseInt(form.kategoriaId.value, 10),
            leiras: form.leiras.value,
            hirdetesDatuma: form.hirdetesDatuma.value,
            tehermentes: form.tehermentes.checked,
            kepUrl: form.kepUrl.value
        };

        try {
        const response = await fetch('http://localhost:5000/api/ujingatlan', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(formData)
        });

        if (!response.ok) {
            const errorData = await response.json();
            setErrorText(errorData.message || 'Sikertelen küldés');
            setShowError(true);
        } else {
            setShowError(false);
            // Sikeres küldés után például átirányítás vagy üzenet
            console.log('Hirdetés sikeresen elküldve!');
            form.reset();
            navigate('/offers'); // 3. Átirányítás sikeres küldés után
        }
        } catch (error) {
            setErrorText('Hálózati hiba: '+error);
            setShowError(true);
        }
    }

    return (
        <div className="container">
            <h2 className="mb-4 text-center">Új hirdetés elküldése</h2>
            <form onSubmit={handleSubmit}>
                <div className="row">
                    <div className="offset-lg-3 offset-md-2 col-lg-6 col-md-8 col-12">
                        <div className="mb-3">
                            <label htmlFor="category" className="form-label">Ingatlan kategóriája</label>
                            <select className="form-select" name="kategoriaId" defaultValue="0">
                                <option value="0">Kérem válasszon</option>
                                {kategoriak.map(kat => (
                                    <option key={kat.id} value={kat.id}>
                                    {kat.megnevezes}
                                    </option>
                                ))}
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
                            {/* curl.exe -X POST http://localhost:5000/api/ujingatlan -H "Content-Type: application/json" -d "@data.json" */}
                            <button className="btn btn-primary px-5">Küldés</button>
                        </div>

                        {showError && (
                        <div className="alert alert-danger alert-dismissible" role="alert">
                            <strong>{errorText}</strong>
                            <button
                            type="button"
                            className="btn-close"
                            
                            aria-label="Bezárás"
                            ></button>
                        </div>
                        )}

                    </div>
                </div>
            </form>
        </div>
    );
}