/* eslint-disable no-console */
const { v4: uuid } = require('uuid');
const bcrypt = require('bcrypt');
const knex = require('../../database/knex');
const CustomError = require('../../utils/CustomError');

exports.signinService = async (trx, { email, password }) => {
  const user = await trx('user').where({ email }).first();
  if (!user) throw new CustomError('USER_NOT_FOUND', 404);
  const isPasswordMatched = await bcrypt.compare(password, user.password);
  if (!isPasswordMatched) throw new CustomError('SIGNIN_FAILED');
  return user;
};

exports.signupService = async (trx, { email, password, name, phone, nickname, place }) => {
  const userId = uuid();
  const hashedPassword = await bcrypt.hash(password, parseInt(process.env.HASH_ROUND));
  const now = Date.now();
  console.log(now);
  await trx('user').insert({
    id: userId,
    name,
    phone,
    email,
    password: hashedPassword,
    nickname,
    place,
  });
  return userId;
};

exports.getUserService = async (trx, user) => {
  const info = await trx('user').where({ id: user.uid }).first();
  return info;
};

exports.verifyEmailService = async (trx, { email }) => {
  const user = await trx('user').where({ email }).first();
  return user;
};
