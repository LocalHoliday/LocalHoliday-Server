const knex = require('knex')({
  client: 'mysql2',
  connection: {
    host: process.env.DB_HOST,
    port: process.env.DB_PORT,
    user: process.env.DB_USER,
    password: process.env.DB_PASS,
    database: process.env.DB_NAME,
    // timezone: 'Z', // UTC 기준으로 반환
    timezone: '-09:00', // KST 기준으로 반환
  },
  asyncStackTraces: true,
  pool: { min: Number(process.env.DB_POOL_MIN) || 0, max: Number(process.env.DB_POOL_MAX) || 10 },
  acquireConnectionTimeout: 5000,
});

module.exports = knex;
