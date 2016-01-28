'use strict';

angular.module('citygardenWebApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


