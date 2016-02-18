'use strict';

angular.module('citygardenWebApp')
    .factory('RepertoryManager', function ($resource, DateUtils) {
        return $resource('api/repertoryManagers/:id', {}, {
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
