'use strict';

angular.module('citygardenWebApp')
    .controller('OrderItemDetailController', function ($scope, $rootScope, $stateParams, entity, OrderItem) {
        $scope.orderItem = entity;
        $scope.load = function (id) {
            OrderItem.get({id: id}, function(result) {
                $scope.orderItem = result;
            });
        };
        var unsubscribe = $rootScope.$on('citygardenWebApp:orderItemUpdate', function(event, result) {
            $scope.orderItem = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
