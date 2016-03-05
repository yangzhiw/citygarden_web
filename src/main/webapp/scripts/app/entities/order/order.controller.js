'use strict';

angular.module('citygardenWebApp')
    .controller('OrderController', function ($scope, $state, Order,orderData) {

        $scope.orders = [];
        $scope.loadAll = function() {
            Order.query(function(result) {
               $scope.orders = result;
                console.log(result)
            });
        };
        $scope.loadAll();




 })
