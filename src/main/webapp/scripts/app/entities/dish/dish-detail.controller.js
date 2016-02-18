'use strict';

angular.module('citygardenWebApp')
    .controller('DishDetailController', function ($scope, $rootScope, $stateParams, entity, Dish) {
        $scope.dish = entity;
        $scope.load = function (id) {
            Dish.get({id: id}, function(result) {
                $scope.dish = result;
            });
        };
        var unsubscribe = $rootScope.$on('citygardenWebApp:dishUpdate', function(event, result) {
            $scope.dish = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
