export async function fetchData(setKategoriak) {
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