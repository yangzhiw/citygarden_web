'use strict';

angular.module('citygardenWebApp')
    .controller('DishController', function ($rootScope,$scope, $state, Dish,CartDetails,DishData) {

        $scope.dishs = [];
        $scope.loadAll = function() {
            Dish.query(function(result) {
               $scope.dishs = result;
                console.log(result);
            });
        };

        $scope.loadAll();

        $scope.cartDetails = {
            count : Number,
            dish   : {},
            subtotal : ""
        }

        $scope.addCart = function(dish){
            console.log(dish);
            $scope.cartDetails.count = 1;
            $scope.cartDetails.dish = dish;
            $scope.cartDetails.subtotal = dish.discountPrice;
            CartDetails.addCart( $scope.cartDetails,function(result){
                $state.go('cart')
            })
        }
    })

    .controller('DishSearchController', function ($rootScope,$scope, $state,DishData, Dish,CartDetails) {
        $scope.dishs = DishData.get();
        $scope.cartDetails = {
            count : Number,
            dish   : {},
            subtotal : ""
        }

        $scope.addCart = function(dish){
            console.log(dish);
            $scope.cartDetails.count = 1;
            $scope.cartDetails.dish = dish;
            $scope.cartDetails.subtotal = dish.discountPrice;
            CartDetails.addCart( $scope.cartDetails,function(result){
                $state.go('cart')
            })
        }
    })


    .controller('DishSearchingController', function ($rootScope,$scope, $state,DishData, Dish,CartDetails) {
    });