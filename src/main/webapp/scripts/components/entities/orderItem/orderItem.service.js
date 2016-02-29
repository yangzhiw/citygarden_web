'use strict';

angular.module('citygardenWebApp')
    .factory('OrderItem', function ($resource, DateUtils) {
        return $resource('api/orderItems/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET',isArray: false
             },
            'update': { method:'PUT' },
            'save': {method:'POST'}
        });
    });
