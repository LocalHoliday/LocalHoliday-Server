const Joi = require('joi');

const CustomError = require('../utils/CustomError');
const { validateToken } = require('../utils/jwt');

const authenticator = (refresh = false) => {
  return async (req, res, next) => {
    try {
      const { authorization } = req.headers;
      const [type, token] = authorization.split(' ');

      if (type.toLowerCase() !== 'bearer') {
        // 타입 검사 실패
        throw new CustomError('INVALID_TOKEN', 401);
      }

      // JWT 유효성 검사
      req.user = await validateToken(token, refresh);

      return next();
    } catch (error) {
      return next(error);
    }
  };
};

const authSchema = Joi.object({
  headers: Joi.object({
    authorization: Joi.string().required().error(new CustomError('UNAUTHORIZED', 401)),
  }).unknown(true),
}).unknown(true);

module.exports = { authenticator, authSchema };
