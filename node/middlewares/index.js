const asyncWrapper = require('./asyncWrapper');
const { authenticator, authSchema } = require('./authenticator');

const validator = schema => {
  return async (req, res, next) => {
    try {
      const validation = await schema.validateAsync(req);
      return next(validation.error);
    } catch (err) {
      return next(err);
    }
  };
};

module.exports = {
  auth: (refresh = false) => [validator(authSchema), authenticator(refresh)],
  asyncWrapper,
  validator,
};
