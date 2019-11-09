var express = require("express");
var path = require("path");
var logger = require("morgan");

var indexRouter = require("./routes/index");

var app = express();

app.use(
  logger(":method :url :status :res[content-length] - :response-time ms")
);
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(express.static(path.join(__dirname, "public")));

app.use("/", indexRouter);

module.exports = app;
