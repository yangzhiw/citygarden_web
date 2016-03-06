'use strict';

angular.module('citygardenWebApp')
    .controller('OrderController', function ($scope, $state, Order,Payment,orderData) {

        $scope.orders = [];
        $scope.orderpay = {
            id:"",
            total : "",
            bankCode : ""
        }
        $scope.loadAll = function() {
            Order.query(function(result) {
               $scope.orders = result;
                console.log(result)
            });
        };
        $scope.loadAll();


       $scope.payment = function (code) {
           console.log(code);
           $scope.orderpay.bankCode =  code;
           $scope.orderpay.id =  $scope.orders[0].id;
           $scope.orderpay.total =  $scope.orders[0].totalPrice;
           console.log( $scope.orderpay)
           Payment.pay($scope.orderpay,function(result){
               console.log(result);
           })
       }

 })
