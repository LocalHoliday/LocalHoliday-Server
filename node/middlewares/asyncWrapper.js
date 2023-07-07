const knex = require('../database/knex');

module.exports = (fn, trx = true) => {
  return async (req, res, next) => {
    try {
      if (trx) req.trx = await knex.transaction();
      await fn(req, res, next);
      if (trx) await req.trx.commit();
      return next();
    } catch (err) {
      if (trx) await req.trx.rollback();
      return next(err);
    }
  };
};
