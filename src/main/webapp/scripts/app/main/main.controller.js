
'use strict';

angular.module('citygardenWebApp')
    .controller('MainController', function ($scope, $state, ProvideMerchant,Principal,CartDetails) {

        $scope.provideMerchants = [];

        $scope.cartDetails = {
            count : Number,
            dish   : {},
            subtotal : ""
        }

        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        $scope.loadAll = function() {
            ProvideMerchant.query(function(result) {
                $scope.provideMerchants = result;
                console.log(result);
            });
        };
        $scope.loadAll();

        $scope.changeCount = function (count) {
            var dishPrice = (Number)($scope.dish.discountPrice);
            var r = /^[0-9]*[1-9][0-9]*$/
            if(r.test(count)){
                count = (Number)(count);
                $scope.cartDetails.subtotal = dishPrice * (count);
            }else{
                alert("输入正确的整数！")
            }

        }

        $scope.decrement = function(dish){
            console.log($scope.dish)
            var count = $scope.cartDetails.count;
            count = (Number)(count);
            var dishPrice = (Number)(dish.discountPrice);
            console.log(dishPrice);
            if(count>=2){
                $scope.cartDetails.count = count -1;
                $scope.cartDetails.subtotal = dishPrice * (count-1);
            }
        }

        $scope.increment = function(dish){
            var count = $scope.cartDetails.count;
            console.log(dish);
            count = (Number)(count);
            var dishPrice = (Number)(dish.discountPrice);
            console.log(dish.discountPrice);
            if(count>=0){
                $scope.cartDetails.count = count +1;
                $scope.cartDetails.subtotal = dishPrice * (count+1);
            }
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
    });
