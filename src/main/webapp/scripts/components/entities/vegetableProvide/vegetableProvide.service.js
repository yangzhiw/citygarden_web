'use strict';

angular.module('citygardenWebApp')
    .factory('VegetableProvide', function ($resource, DateUtils) {
        return $resource('api/vegetableProvides/:id', {}, {
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
