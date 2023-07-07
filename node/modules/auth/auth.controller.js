const { postClipService, getClipService, getBillService } = require('./auth.service');

exports.getClipController = async (req, res) => {
  const clips = await getClipService(req.trx, req.user);
  return res.status(200).json(clips);
};

exports.postClipController = async (req, res) => {
  await postClipService(req.trx, req.user, req.body);
  return res.status(200).end();
};

exports.getBillController = async (req, res) => {
  const bills = await getBillService(req.trx, req.user);
  return res.status(200).json(bills);
};
