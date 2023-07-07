const router = require('express').Router();
const { swaggerUi, specs } = require('../config/swagger');

const commonRouter = require('./common/common.router');
const authRouter = require('./auth/auth.router');

/**
 * @swagger
 * tags:
 *   name: Common Router
 *   description: 로그인, 회원가입, 내 정보 조회 기능
 */
router.use('/', commonRouter);

router.use('/auth', authRouter);
router.use('/docs', swaggerUi.serve, swaggerUi.setup(specs));

module.exports = router;
