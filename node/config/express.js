const express = require('express');
const helmet = require('helmet');
const morgan = require('morgan');
const cors = require('cors');

const router = require('../modules'); // 엔드포인트 설정
const morganConfig = require('./morgan'); // morgan 설정
const whitelist = [
  'http://localhost:8080/*',
  'http://localhost:8081/*',
  'http://15.165.241.113:8081/*',
];

const corsOptions = {
  origin: function (origin, callback) {
    if (whitelist.indexOf(origin) !== -1) {
      // 만일 whitelist 배열에 origin인자가 있을 경우
      callback(null, true); // cors 허용
    } else {
      callback(new Error('Not Allowed Origin!')); // cors 비허용
    }
  },
};

const app = express();

// 미들웨어
app.set('trust proxy', true);
app.use(helmet());
app.use(express.json()); // JSON 처리 설정
app.use(express.urlencoded({ extended: true })); // URL-encoded 처리 설정
app.use(express.raw()); // Buffer 처리 설정
app.use(express.text());
app.use(cors({ origin: '*' }));

// 로그
if (process.env.NODE_ENV === 'production') {
  app.use(morgan('combined', morganConfig));
} else {
  app.use(morgan('dev'));
}

// 라우팅
app.use(`/`, router);

module.exports = app;
