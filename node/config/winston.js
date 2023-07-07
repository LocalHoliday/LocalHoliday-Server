const { createLogger, format, transports } = require('winston');
const DailyRotateFile = require('winston-daily-rotate-file');

const winston = createLogger({
  level: 'info',
  format: format.combine(
    format.timestamp({
      format: 'YYYY-MM-DD HH:mm:ss',
    }),
    format.errors({ stack: true }),
    format.splat(),
    format.json()
  ),
  defaultMeta: { service: 'PROJECT_NAME' },
  transports: [
    new DailyRotateFile({
      level: 'error',
      datePattern: 'YYYY-MM-DD',
      dirname: 'logs',
      filename: '%DATE%.error.log',
      level: 'error',
    }),
    new DailyRotateFile({
      level: 'info',
      datePattern: 'YYYY-MM-DD',
      dirname: 'logs',
      filename: '%DATE%.log',
    }),
  ],
});

if (process.env.NODE_ENV !== 'production') {
  winston.add(
    new transports.Console({
      format: format.combine(format.colorize(), format.simple()),
    })
  );
}

module.exports = winston;
