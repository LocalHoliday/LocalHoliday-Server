const router = require('express').Router();

const { auth, asyncWrapper, validator } = require('../../middlewares');

const {
  getPresigned,
  postPresigned,
  signinController,
  signupController,
  getUserController,
} = require('./common.controller');
const { signupSchema, signinSchema } = require('../../utils/validationSchema');

/** AWS S3 */
router
  .route('/presigned/*')
  // AWS S3 조회용 presigned URL 요청
  .get(asyncWrapper(getPresigned, false))
  // AWS S3 업로드용 presigned URL 요청
  .post(auth(), asyncWrapper(postPresigned, false));

/**
 * @swagger
 * tags:
 *   name: Users
 *   description: 유저 추가 수정 삭제 조회
 */
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

module.exports = router;
