const CustomError = require('../../utils/CustomError');
const { createToken } = require('../../utils/jwt');
const { getSignedUrl, createPresignedPost } = require('../../utils/s3');
const {
  signupService,
  getUserService,
  dbTestService,
  checkEmailUsedService,
  signinService,
} = require('./common.service');

exports.getPresigned = async (req, res) => {
  const url = await getSignedUrl(req.params[0]);
  return res.status(200).json({ url });
};

exports.postPresigned = async (req, res) => {
  const data = await createPresignedPost(req.params[0]);
  return res.status(200).json(data);
};

exports.signinController = async (req, res) => {
  const user = await signinService(req.trx, req.body);
  const token = await createToken('access', user.id, 'app');
  return res.status(200).json(token);
};

exports.signupController = async (req, res) => {
  // const user = await checkEmailUsedService(req.trx, req.body);
  // if (user) throw new CustomError('USER_ALREADY_EXISTS');
  await signupService(req.trx, req.body);
  return res.status(200).end();
};

exports.getUserController = async (req, res) => {
  const user = await getUserService(req.trx, req.user);
  return res.status(200).json(user);
};
