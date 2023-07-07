const crypto = require('crypto');
const redis = require('../database/redis');
const { REGEX } = require('../utils');
const CustomError = require('../utils/CustomError');

/**
 * JWT 생성 후 Redis 등록
 * @param {string} tokenType 종류(access, refresh)
 * @param {string} userId 사용자 UUID
 * @param {string} client 클라이언트 종류(app, web)
 * @param {number} ttl 유효시간
 * @returns {string} JWT
 */
async function createToken(tokenType, userId, client) {
  // JWT 생성
  const header = {
    alg: 'HS256',
    typ: 'JWT',
  };
  const encodedHeader = Buffer.from(JSON.stringify(header), 'utf8')
    .toString('base64')
    .replace(/\=/g, '');
  const encodedPayload = Buffer.from(
    JSON.stringify({
      token: tokenType,
      uid: userId,
      clt: client,
    }),
    'utf8'
  )
    .toString('base64')
    .replace(/\=/g, '');
  const signature = crypto
    .createHmac('sha256', process.env.JWT_SECRET)
    .update(encodedHeader + '.' + encodedPayload)
    .digest('base64')
    .replace(/\=/g, '');
  const token = encodedHeader + '.' + encodedPayload + '.' + signature;

  // Redis 등록
  const redisKey = `${userId}:${client}:${tokenType}`;
  await redis.SETEX(redisKey, parseInt(process.env.REDIS_ACCESS_TOKEN_TTL), token);
  return token;
}

/**
 * JWT 검증
 * @param {string} token JWT
 * @returns {string} JWT payload
 */
async function validateToken(token, refresh = false) {
  if (!token.match(REGEX.JWT)) throw new CustomError('INVALID_TOKEN', 401);
  const [header, payload, signature] = token.split('.');
  const testSignature = crypto
    .createHmac('sha256', process.env.JWT_SECRET)
    .update(header + '.' + payload)
    .digest('base64')
    .replace(/\=/g, '');

  /* 조작된 토큰에 대해서는 별도 처리없이 접근 거부 응답 */
  if (testSignature !== signature) throw new CustomError('INVALID_TOKEN', 401);

  const decodedPayload = JSON.parse(Buffer.from(payload, 'base64').toString('utf8'));

  /* Redis 등록 토큰 검증 */
  const redisData = await redis.GET(
    `${decodedPayload.uid}:${decodedPayload.clt}:${decodedPayload.token}`
  );
  if (!redisData || redisData !== token) {
    throw new CustomError(`INVALID_${decodedPayload.token?.toUpperCase()}_TOKEN`, 401);
  }

  return decodedPayload;
}

module.exports = {
  createToken,
  validateToken,
};
