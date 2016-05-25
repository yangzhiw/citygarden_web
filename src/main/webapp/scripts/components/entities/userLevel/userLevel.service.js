'use strict';

angular.module('citygardenWebApp')
    .factory('UserLevel', function ($resource, DateUtils) {
        return $resource('api/userLevels/:id', {}, {
            'query': { method: 'GET', isArray: false},
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
