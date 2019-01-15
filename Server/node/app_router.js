var express = require('express');
var path = require('path');
var bodyParser = require('body-parser');

var user_api = require('./user_api');
var trip_api = require('./trip_api');

var app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

app.use('/user', user_api);

app.use('/trip', trip_api);

app.get('/',function(req,res){
	res.send('Mi trovo in app_router.js');
});

module.exports = app;
