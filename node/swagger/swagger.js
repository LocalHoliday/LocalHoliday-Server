const swaggerUi = require('swagger-ui-express');
const swaggereJsdoc = require('swagger-jsdoc');

const options = {
  swaggerDefinition: {
    openapi: '3.0.0',
    info: {
      version: '1.0.0',
      title: 'Local Holiday',
      description: 'Node.js Swaager swagger-jsdoc 방식 RestFul API 클라이언트 UI',
    },
    servers: [
      {
        url: 'http://localhost:8080', // 요청 URL
      },
    ],
  },
  apis: ['../modules/*.js', '../modules/common/*.js', '../modules/auth/*.js'], //Swagger 파일 연동
};
const specs = swaggereJsdoc(options);

module.exports = { swaggerUi, specs };
