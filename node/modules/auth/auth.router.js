const router = require('express').Router();

const { auth, asyncWrapper, validator } = require('../../middlewares');
const { getLikeController } = require('./auth.controller');

router.route('/like').get(auth(), asyncWrapper(getLikeController));

module.exports = router;
