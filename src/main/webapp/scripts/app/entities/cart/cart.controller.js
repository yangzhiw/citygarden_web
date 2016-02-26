'use strict';

angular.module('citygardenWebApp')
    .controller('CartController', function ($scope, $state, Cart) {

        $scope.carts = {};
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
    });
