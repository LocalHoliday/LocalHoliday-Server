const Joi = require('joi');
const CustomError = require('./CustomError');

exports.signupSchema = Joi.object({
  body: Joi.object({
    email: Joi.string()
      .pattern(/^[\w\.-]+@[\w-]+(\.\w{2,4}){1,2}$/)
      .max(200)
      .error(new CustomError('INVALID_EMAIL')),
    password: Joi.string().max(255).required().error(new CustomError('INVALID_PASSWORD')),
    name: Joi.string().max(100).required().error(new CustomError('INVALID_NAME')),
    phone: Joi.string().max(11).required().error(new CustomError('INVALID_PHONE')),
    nickname: Joi.string().max(100).required().error(new CustomError('INVALID_NICKNAME')),
    place: Joi.string().max(300).required().error(new CustomError('INVALID_PLACE')),
  }).unknown(true),
}).unknown(true);

exports.signinSchema = Joi.object({
  body: Joi.object({
    email: Joi.string()
      .pattern(/^[\w\.-]+@[\w-]+(\.\w{2,4}){1,2}$/)
      .max(200)
      .error(new CustomError('INVALID_EMAIL')),
    password: Joi.string().max(255).required().error(new CustomError('INVALID_PASSWORD')),
  }).unknown(true),
}).unknown(true);
