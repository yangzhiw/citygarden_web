'use strict';

angular.module('citygardenWebApp')
    .controller('OrderController', function ($scope, $state, Order,orderData) {

        $scope.orders = [];
        $scope.order1 = {};
        $scope.loadAll = function() {
            Order.query(function(result) {
               $scope.orders = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };
        $scope.order1 = orderData.get();
        console.log($scope.order1);
        $scope.clear = function () {
            $scope.order = {
                priceCount: null,
                id: null
            };
        };
    });
