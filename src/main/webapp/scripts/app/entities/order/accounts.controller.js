'use strict';

angular.module('citygardenWebApp')
    .controller('AccountsController', function ($scope, $state, CartToAccount,DeliveryAddress) {

        $scope.order = [];
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

            CartToAccount.get({id:1},function(result) {
               console.log(result);
               $scope.order = result
              $scope.order.totalPrice = Number($scope.order.totalPrice) +  Number($scope.cost);
            });
        };
        $scope.loadAll();

    })
