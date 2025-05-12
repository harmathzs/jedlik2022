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

app.get('/', (req, res) => {
  res.send('Hello World!');
});

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

app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});
