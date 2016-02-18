'use strict';

angular.module('citygardenWebApp')
    .controller('OrderItemController', function ($scope, $state, OrderItem) {

        $scope.orderItems = [];
        $scope.loadAll = function() {
            OrderItem.query(function(result) {
               $scope.orderItems = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.orderItem = {
                id: null
            };
        };
    });
