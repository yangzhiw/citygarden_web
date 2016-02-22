'use strict';

angular.module('citygardenWebApp')
    .factory('CartDetails', function ($resource, DateUtils) {
        return $resource('api/cartDetailss/:id', {}, {
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
