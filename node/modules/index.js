const router = require('express').Router();

const commonRouter = require('./common/common.router');
const authRouter = require('./auth/auth.router');

router.use('/', commonRouter);
router.use('/auth', authRouter);

module.exports = router;
