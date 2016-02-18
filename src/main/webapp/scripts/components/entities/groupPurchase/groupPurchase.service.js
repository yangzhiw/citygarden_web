'use strict';

angular.module('citygardenWebApp')
    .factory('GroupPurchase', function ($resource, DateUtils) {
        return $resource('api/groupPurchases/:id', {}, {
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
