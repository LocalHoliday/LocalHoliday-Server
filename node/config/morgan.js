const rfs = require('rotating-file-stream');

function getFilename() {
  return `${new Date().toISOString().split('T')[0]}-access.log`;
}

const accessLogStream = rfs.createStream(getFilename, {
  interval: '1d',
  path: 'logs',
});

const morganConfig = { stream: accessLogStream };

module.exports = morganConfig;
