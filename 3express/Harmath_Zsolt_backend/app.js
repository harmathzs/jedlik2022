const express = require('express');
const mysql = require('mysql2');
const app = express();
const port = 3000;

const connection = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: '',
  database: 'ingatlan'
});

connection.connect(err => {
  if (err) {
    console.error('Database connection failed:', err.stack);
    return;
  }
  console.log('Connected to MySQL database.');
});

// Add this line to parse JSON request bodies
app.use(express.json());

// curl http://localhost:3000
app.get('/', (req, res) => {
  res.send('Hello World!');
});

// curl http://localhost:3000/api/ingatlan
app.get('/api/ingatlan', (req, res) => {
    connection.query('SELECT ingatlanok.id, kategoriak.id, kategoriak.nev AS kategoria, leiras, hirdetesDatuma, tehermentes, ar, kepUrl FROM ingatlanok INNER JOIN kategoriak ON ingatlanok.kategoria=kategoriak.id', (err, results) => {
        if (err) {
            res.status(500).json({ error: 'Database error' });
            return;
        }
        // Convert tehermentes from 0/1 to false/true
        const refactoredResults = results.map(row => ({
            ...row,
            tehermentes: !!row.tehermentes // Converts 0 to false, 1 to true
        }));
        res.status(200).json(refactoredResults);
    });
});

// curl.exe -X POST http://localhost:3000/api/ingatlan -H "Content-Type: application/json" -d "@data.json"
app.post('/api/ingatlan', (req, res) => {
  let result = {result: 'no result'};

  //console.log('req', req);
  console.log('req.body', req?.body);

  let id = +req?.body?._id;
  let kategoria = +req?.body?.kategoria;
  let leiras = req?.body?.leiras;
  let hirdetesDatuma = req?.body?.hirdetesDatuma;
  let tehermentes = req?.body?.tehermentes;
  let ar = +req?.body?.ar;
  let kepUrl = req?.body?.kepUrl;

  if (!kategoria || !leiras || !hirdetesDatuma || tehermentes==null || !ar || !kepUrl) {
    return res.status(400).json('Hiányos adatok.');
  }

  let newIngatlan = {
    id: id,
    kategoria: kategoria,
    leiras: leiras,
    hirdetesDatuma: hirdetesDatuma,
    tehermentes: tehermentes,
    ar: ar,
    kepUrl: kepUrl,
  };
  console.log('newIngatlan', newIngatlan);

  let idStr = id ? 'id,' : '';
  let idQmark = id ? '?,' : '';

  let sql = `
    INSERT INTO ingatlanok 
    (${idStr} kategoria, leiras, hirdetesDatuma, tehermentes, ar, kepUrl)
    VALUES (${idQmark} ?, ?, ?, ?, ?, ?)
    ON DUPLICATE KEY UPDATE
      kategoria = VALUES(kategoria),
      leiras = VALUES(leiras),
      hirdetesDatuma = VALUES(hirdetesDatuma),
      tehermentes = VALUES(tehermentes),
      ar = VALUES(ar),
      kepUrl = VALUES(kepUrl)
  `;

  let values = id ? [id,] : [];
  
  values = [...values,

    kategoria,
    leiras,
    new Date(hirdetesDatuma),
    tehermentes ? 1 : 0,  // Convert boolean to MySQL TINYINT (0/1)
    ar,
    kepUrl
  ];

  connection.query(sql, values, (err, result) => {
    if (err) {
      console.error('Database error:', err);
      return res.status(400).json('Hiányos adatok.');
    } else {
      console.log('result', result);
      return res.status(201).json({Id: result?.insertId ?? id});
    }
  });
});

// curl.exe -X DELETE http://localhost:3000/api/ingatlan/99
app.delete('/api/ingatlan/:id', (req, res) => {
  const id = req?.params?.id;
  
  // 1. Use parameterized query with ? placeholder
  const sql = 'DELETE FROM ingatlanok WHERE id = ?';
  
  // 2. Pass parameters as an array
  connection.query(sql, [id], (err, results) => {
    if (err) {
      // 3. Handle database errors first
      console.error('Database error:', err);
      return res.status(500).json({ error: 'Internal server error' });
    }
    
    // 4. Check if any rows were affected
    if (results.affectedRows === 0) {
      return res.status(404).json('Az ingatlan nem létezik.' );
    }
    
    // 5. Proper 204 No Content response
    console.log('204 No Content');
    res.sendStatus(204);
  });
});


app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});
