'use strict';

angular.module('citygardenWebApp')
    .controller('CartController', function ($scope, $state, Cart,orderData) {

        $scope.carts = {};
        $scope.order = {
            date:new Date(),
            totalPrice:Number,
            deliveryWay:"",
            orderStatus:"",
            orderItems : []
        }
        $scope.loadAll = function() {
            Cart.query(function(result) {
               $scope.carts = result;
                console.log(result);
            });
        };
        $scope.loadAll();

        $scope.updateCartDetail = function(cartDetail){
            console.log(cartDetail);
            var index = $scope.carts.cartDetailsList.indexOf(cartDetail);
            console.log(index);
            if( index > -1){
                $scope.carts.cartDetailsList.splice(index,1);
            }
            console.log($scope.carts);

            Cart.update($scope.carts,function(result){
                console.log(result);
                $state.reload();
            })
        }

        $scope.goAccount = function(){
            console.log($scope.carts);
            $scope.order.orderItems = [];
            var totalPrice = 0;
            for(var i=0; i<$scope.carts.cartDetailsList.length; i++) {
                if($scope.carts.cartDetailsList[i].check) {
                    delete $scope.carts.cartDetailsList[i].check;
                    $scope.order.orderItems.push($scope.carts.cartDetailsList[i]);

                    totalPrice = totalPrice + $scope.carts.cartDetailsList[i].subtotal;
                }
            }

            $scope.order.totalPrice = totalPrice;
            $scope.order.deliveryWay = "派送";
            $scope.order.orderStatus = "1";
            console.log($scope.order);

            orderData.set($scope.order);

        }
    });
