'use strict';

angular.module('citygardenWebApp')
    .factory('Dish', function ($resource, DateUtils) {
        return $resource('api/dishs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
