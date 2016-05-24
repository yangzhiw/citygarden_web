'use strict';

angular.module('citygardenWebApp')
    .factory('ProvideMerchant', function ($resource, DateUtils) {
        return $resource('api/provideMerchants/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET'
            },
            'update': { method:'PUT' }
        });
    });
