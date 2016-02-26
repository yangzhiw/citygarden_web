'use strict';

angular.module('citygardenWebApp')
    .controller('DishDetailController', function ($scope, $rootScope,$state, $stateParams,CartDetails,entity, Dish) {
        $scope.dish = entity;
        console.log(entity)
        $scope.cartDetails = {
            count : Number,
            dish   : entity,
            subtotal : ""
        }

        $scope.init = function(){
            $scope.cartDetails.subtotal = $scope.dish.discountPrice;
            console.log($scope.cartDetails.subtotal)
        }

        $scope.init();

        $scope.load = function (id) {
            Dish.get({id: id}, function(result) {
                $scope.dish = result;
            });
        };
        var unsubscribe = $rootScope.$on('citygardenWebApp:dishUpdate', function(event, result) {
            $scope.dish = result;
        });
        $scope.$on('$destroy', unsubscribe);

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

        $scope.decrement = function(){
            console.log($scope.cartDetails.dish)
            var count = $scope.cartDetails.count;
            count = (Number)(count);
            var dishPrice = (Number)($scope.dish.discountPrice);
            console.log(dishPrice);
            if(count>=2){
                $scope.cartDetails.count = count -1;
                $scope.cartDetails.subtotal = dishPrice * (count-1);
            }
        }

        $scope.increment = function(){
            var count = $scope.cartDetails.count;
            count = (Number)(count);
            var dishPrice = (Number)($scope.dish.discountPrice);
            console.log(entity.discountPrice);
            if(count>=0){
                $scope.cartDetails.count = count +1;
                $scope.cartDetails.subtotal = dishPrice * (count+1);
            }
        }

        $scope.addCart = function(cartDetails){
            console.log(cartDetails.dish);
            CartDetails.addCart(cartDetails,function(result){
                $state.go('cart')
            })
        }

    });
