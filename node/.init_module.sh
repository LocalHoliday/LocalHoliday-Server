#!/bin/bash
function init_module() {
  module_name=$1
  mkdir ${module_name}
  echo -e "const router = require('express').Router();\n\nconst { auth, asyncWrapper, validator } = require('../../middlewares');\n\nconst {} = require('./${module_name}.controller');\nconst {} = require('../../utils/validationSchema');\n\nmodule.exports = router;" > "${module_name}/${module_name}.router.js";
  echo -e "const {} = require('./${module_name}.service');" > "${module_name}/${module_name}.controller.js";
  echo -e "" > "${module_name}/${module_name}.service.js";
}