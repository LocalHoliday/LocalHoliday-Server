const router = require('express').Router();

const { auth, asyncWrapper, validator } = require('../../middlewares');
const { postClipSchema } = require('../../utils/validationSchema');
const { getClipController, postClipController } = require('./auth.controller');

router
  // 스크랩
  .route('/clip')
  // 스크랩 조회 기능
  .get(auth(), asyncWrapper(getClipController))
  // 스크랩 추가 기능
  .post(auth(), validator(postClipSchema), asyncWrapper(postClipController));

module.exports = router;
