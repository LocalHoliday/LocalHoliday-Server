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

exports.postClipSchema = Joi.object({
  body: Joi.object({
    targetId: Joi.string().max(255).required().error(new CustomError('INVALID_TARGET_ID')),
    job: Joi.boolean().required().error(new CustomError('INVALID_JOB_TYPE')),
  }).unknown(true),
}).unknown(true);

exports.verifyEmailSchema = Joi.object({
  query: Joi.object({
    email: Joi.string()
      .pattern(/^[\w\.-]+@[\w-]+(\.\w{2,4}){1,2}$/)
      .max(200)
      .error(new CustomError('INVALID_EMAIL')),
  }).unknown(true),
}).unknown(true);

exports.verifyNickNameSchema = Joi.object({
  query: Joi.object({
    nickname: Joi.string().max(100).required().error(new CustomError('INVALID_NICKNAME')),
  }).unknown(true),
}).unknown(true);

exports.getJobSchema = Joi.object({
  query: Joi.object({
    place: Joi.string().max(100).required().error(new CustomError('INVALID_PLACE')),
  }).unknown(true),
}).unknown(true);
