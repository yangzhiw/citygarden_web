'use strict';

angular.module('citygardenWebApp')
    .controller('AccountsController', function ($scope, $state, Order,DeliveryAddress) {

        $scope.order = {
            id : "",
            deliveryWay : "",
            orderItemList : [],
            orderStatus :  "",
            totalPrice : Number
        };
        $scope.deliveryAddresses = [];
        $scope.cost = 5;
        $scope.deliveryWay = [
            '亲取', '配送'
        ]

        $scope.loadAll = function() {
            DeliveryAddress.query(function(result) {
                $scope.deliveryAddresses = result;
                console.log(result);
            });

            Order.get({id:1},function(result) {
               console.log(result);
               $scope.order = result
               $scope.order.totalPrice = Number($scope.order.totalPrice) +  Number($scope.cost);
            });
        };
        $scope.loadAll();

    })
