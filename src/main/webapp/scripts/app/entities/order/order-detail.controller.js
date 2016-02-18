'use strict';

angular.module('citygardenWebApp')
    .controller('OrderDetailController', function ($scope, $rootScope, $stateParams, entity, Order) {
        $scope.order = entity;
        $scope.load = function (id) {
            Order.get({id: id}, function(result) {
                $scope.order = result;
            });
        };
        var unsubscribe = $rootScope.$on('citygardenWebApp:orderUpdate', function(event, result) {
            $scope.order = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
