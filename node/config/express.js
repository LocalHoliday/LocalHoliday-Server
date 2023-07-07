const express = require('express');
const helmet = require('helmet');
const morgan = require('morgan');
const cors = require('cors');

const router = require('../modules'); // 엔드포인트 설정
const morganConfig = require('./morgan'); // morgan 설정

const app = express();

// 미들웨어
app.set('trust proxy', true);
app.use(helmet());
app.use(express.json()); // JSON 처리 설정
app.use(express.urlencoded({ extended: true })); // URL-encoded 처리 설정
app.use(express.raw()); // Buffer 처리 설정
app.use(express.text());
app.use(cors());

// 로그
if (process.env.NODE_ENV === 'production') {
  app.use(morgan('combined', morganConfig));
} else {
  app.use(morgan('dev'));
}

// 라우팅
app.use(`/`, router);

module.exports = app;
