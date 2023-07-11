const CustomError = require('../../utils/CustomError');
const { createToken } = require('../../utils/jwt');
const { getSignedUrl, createPresignedPost } = require('../../utils/s3');
const {
  signupService,
  getUserService,
  signinService,
  verifyEmailService,
  verifyNickNameService,
  getJobService,
  getJobDetailService,
  getReviewService,
  getReviewDetailService,
  getBillDetailService,
  getBillService,
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
  return res.status(200).json({ token });
};

exports.signupController = async (req, res) => {
  const user = await verifyEmailService(req.trx, req.body);
  if (!user) throw new CustomError('USER_ALREADY_EXISTS');
  const userId = await signupService(req.trx, req.body);
  const token = await createToken('access', userId, 'app');
  return res.status(200).json({ token });
};

exports.getUserController = async (req, res) => {
  const user = await getUserService(req.trx, req.user);
  return res.status(200).json(user);
};

exports.verifyNickNameController = async (req, res) => {
  const result = await verifyNickNameService(req.trx, req.query);
  return res.status(200).json({ result });
};
exports.verifyEmailController = async (req, res) => {
  const result = await verifyEmailService(req.trx, req.query);
  return res.status(200).json({ result });
};

exports.getJobController = async (req, res) => {
  const jobs = await getJobService(req.trx, req.query);
  return res.status(200).json({ jobs });
};

exports.getJobDetailController = async (req, res) => {
  const job = await getJobDetailService(req.trx, req.query, req.params);
  return res.status(200).json({ job });
};

exports.getReviewController = async (req, res) => {
  const reviews = await getReviewService(req.trx);
  return res.status(200).json({ reviews });
};

exports.getReviewDetailController = async (req, res) => {
  const review = await getReviewDetailService(req.trx, req.params);
  return res.status(200).json({ review });
};

exports.getBillController = async (req, res) => {
  const reviews = await getBillService(req.trx);
  return res.status(200).json({ reviews });
};

exports.getBillDetailController = async (req, res) => {
  const review = await getBillDetailService(req.trx, req.params);
  return res.status(200).json({ review });
};
