'use strict';

angular.module('citygardenWebApp')
    .controller('AccountsController', function ($scope, $state, CartToAccount,DeliveryAddress,Order) {

        $scope.order = [];
        $scope.deliveryAddresses = [];
        $scope.defaultDeliveryAddress = "";
        $scope.cost = 5;
        $scope.deliveryWay = [
            '亲取', '配送'
        ]

        $scope.loadAll = function() {
            DeliveryAddress.query(function(result) {
                $scope.deliveryAddresses = result;
                angular.forEach(result,function(res){
                    if(res.isDefault == 0){
                        $scope.defaultDeliveryAddress = res.address;
                    }
                })
                console.log(result);
            });

            CartToAccount.get({id:1},function(result) {
               console.log(result);
               $scope.order = result
                if(result.deliveryWay == 1){
                    $scope.order.totalPrice = Number($scope.order.totalPrice) +  Number($scope.cost);
                }
                $scope.order.deliveryAddress = $scope.defaultDeliveryAddress;
            });
        };
        $scope.loadAll();

        $scope.changDeliveryWay = function(deliveryWay) {
            if(deliveryWay == 1){
                $scope.order.totalPrice = Number($scope.order.totalPrice) +  Number($scope.cost);
            }else{
                $scope.order.totalPrice = Number($scope.order.totalPrice) -  Number($scope.cost);
            }
        };

        //$scope.addDelivery = function(delivery){
        //    console.log(delivery)
        //}

        $scope.delete = function(order){
            console.log(order);
            CartToAccount.delete({id:order.id},function(result){
                $state.go("cart");
            });
        }

        $scope.submit = function (order) {
            console.log(order);
             Order.save(order,function(result){
                $state.go('order');
             })
        }

    })
