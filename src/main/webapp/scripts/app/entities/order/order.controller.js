'use strict';

angular.module('citygardenWebApp')
    .controller('OrderController', function ($scope, $state, Order) {

        $scope.orders = [];
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

        $scope.clear = function () {
            $scope.order = {
                priceCount: null,
                id: null
            };
        };
    });
