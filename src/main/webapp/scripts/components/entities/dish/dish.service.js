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
    })

    .factory('DishPhoto', function ($resource, DateUtils) {
        return $resource('api/dishphoto/:id', {}, {
            'query': { method: 'GET', isArray: false}
        })

    })

    .factory('DishSearch', function ($resource, DateUtils) {
        return $resource('api/dishs/seacher', {}, {
            'get': { method: 'GET', isArray: true}
        })

    })

    .factory('DishData', function () {
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
