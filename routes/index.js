const express = require("express");
const router = express.Router();
const packageJson = require("../package.json");

/* GET home page. */
router.get("/", function(req, res, next) {
  res
    .status(200)
    .json({
      msg: "Node.js on OCP",
      name: packageJson.name,
      version: packageJson.version
    });
});

module.exports = router;
