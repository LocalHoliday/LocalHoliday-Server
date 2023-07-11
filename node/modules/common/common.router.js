const router = require('express').Router();

const { auth, asyncWrapper, validator } = require('../../middlewares');

const {
  getPresigned,
  postPresigned,
  signinController,
  signupController,
  getUserController,
  verifyEmailController,
  verifyNickNameController,
  getJobController,
  getJobDetailController,
  getReviewController,
  getReviewDetailController,
  getBillController,
  getBillDetailController,
} = require('./common.controller');
const {
  signupSchema,
  signinSchema,
  verifyEmailSchema,
  verifyNickNameSchema,
  getJobSchema,
} = require('../../utils/validationSchema');
const { REGEX } = require('../../utils');

/** AWS S3 */
router
  .route('/presigned/*')
  // AWS S3 조회용 presigned URL 요청
  .get(asyncWrapper(getPresigned, false))
  // AWS S3 업로드용 presigned URL 요청
  .post(auth(), asyncWrapper(postPresigned, false));

router
  .route('/signIn')
  // 로그인
  .post(validator(signinSchema), asyncWrapper(signinController));

router
  .route('/')
  // 회원가입
  .post(validator(signupSchema), asyncWrapper(signupController))
  // 내 정보 가져오기
  .get(auth(), asyncWrapper(getUserController));

router
  // email 중복 확인
  .route('/verify/email')
  .get(validator(verifyEmailSchema), asyncWrapper(verifyEmailController));

router
  // NICK_NAME 중복 확인
  .route('/verify/nickname')
  .get(validator(verifyNickNameSchema), asyncWrapper(verifyNickNameController));

router.route('/job').get(validator(getJobSchema), asyncWrapper(getJobController));
router
  .route(`/job/:jobId(${REGEX.UUID})`)
  .get(validator(getJobSchema), asyncWrapper(getJobDetailController));

router.route('/review').get(asyncWrapper(getReviewController));
router.route(`/review/:reviewId(${REGEX.UUID})`).get(asyncWrapper(getReviewDetailController));

router.route('/bill').get(asyncWrapper(getBillController));
router.route(`/bill/:billId(${REGEX.UUID})`).get(asyncWrapper(getBillDetailController));

module.exports = router;
