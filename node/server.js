process.env.PWD = __dirname;
require('dotenv').config();

const app = require('./config/express');
const redis = require('./database/redis');

app.set('port', process.env.PORT || 8080);
let isDisableKeepAlive = false;
app.use((req, res, next) => {
  if (isDisableKeepAlive) {
    res.set('Connection', 'close');
  }
  next();
});

// 에러 핸들링
app.use(async (err, req, res, next) => {
  if (process.env.NODE_ENV !== 'production') console.error(err);
  const status = err?.status || 500;
  const message = err?.message || '서버 내부 에러';
  return res.status(status).json({ message });
});

// 서버 프로세스 시작
const server = app.listen(app.get('port'), () => {
  process.send('ready');
  console.log(`Server started on port ${app.get('port')}`);
});

server.setTimeout(5000);

// 처리되지 않은 에러 핸들링
process.on('unhandledRejection', (reason, promise) => {
  console.error('Unhandled Rejection at: ', promise, '\nReason:', reason);
});

// 프로세스 재시작
process.on('SIGINT', () => {
  isDisableKeepAlive = true;
  server.close(() => {
    console.log('Server closed');
    redis.quit();
    process.exit(0);
  });
});

module.exports = server;
