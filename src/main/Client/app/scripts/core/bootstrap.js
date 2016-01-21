'use strict';

require('./vendor.js');
var app = require('../app');

angular.element(document).ready(function(){
    angular.bootstrap(document, [app.name], {});
});