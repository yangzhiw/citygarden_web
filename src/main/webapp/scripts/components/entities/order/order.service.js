'use strict';

angular.module('citygardenWebApp')
    .factory('Order', function ($resource, DateUtils) {
        return $resource('api/orders/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' },
            'save': {method:'POST'}

        });
    })

    .factory('CartToAccount', function ($resource, DateUtils) {
        return $resource('api/cart2accounts/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' },
            'save': {method:'POST'}

        });
    })

    .factory('orderData', function () {
        var tmp = {};
        return {
            get: function () {
                return tmp;
            },
            set: function (data) {
                tmp = data;
            }
        }
    });
