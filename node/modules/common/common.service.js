/* eslint-disable no-console */
const { v4: uuid } = require('uuid');
const bcrypt = require('bcrypt');
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
  const result = await trx('user').where({ email }).first();
  return result ? false : true;
};

exports.verifyNickNameService = async (trx, { nickname }) => {
  const result = await trx('user').where({ nickname }).first();
  return result ? false : true;
};

exports.getJobService = async (trx, { place }) => {
  const { id } = await trx('location_code').where({ code: place }).first();
  const jobs = await trx('job')
    .select('id', 'name', 'photo', 'location', 'start_date', 'end_date', 'payment')
    .where({ field: id })
    .orderBy('end_date', 'DESC');
  return jobs;
};

exports.getJobDetailService = async (trx, { place }, { jobId }) => {
  const { id } = await trx('location_code').where({ code: place }).first();
  const job = await trx('job')
    .select('id', 'name', 'photo', 'location', 'start_date', 'end_date', 'payment', 'host_phone')
    .where({ field: id, id: jobId })
    .first();
  const reviews = await trx
    .from({ jb: 'job_bill' })
    .leftJoin({ j: 'job' }, 'j.id', 'jb.job_id')
    .where({ 'j.field': id, 'j.id': jobId })
    .orderBy('created', 'DESC');
  return { ...job, reviews };
};
