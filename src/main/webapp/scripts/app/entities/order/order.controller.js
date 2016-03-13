'use strict';

angular.module('citygardenWebApp')
    .controller('OrderController', function ($scope, $state, Order,Payment,orderData) {

        $scope.orders = [];

        $scope.orderStatus = [
            '',
            '付款',
            '未发货',
            '确认收货',
            '订单完成'
        ]
        $scope.loadAll = function() {
            Order.query(function(result) {
               $scope.orders = result;
                console.log(result)
            });
        };
        $scope.loadAll();



 })
    .controller('OrderPayController', function ($scope,$stateParams, $state, Order,Payment) {

        $scope.order = {};
        $scope.orderpay = {
            id:"",
            total : "",
            bankCode : ""
        }

        $scope.load = function(){
            Order.get({id : $stateParams.id},function(result){
                $scope.order = result;
            });
        }

        $scope.load();

        $scope.payment = function (code) {
            console.log(code);
            $scope.orderpay.bankCode =  code;
            $scope.orderpay.id =  $scope.order.id;
            $scope.orderpay.total =  $scope.order.totalPrice;
            console.log( $scope.orderpay)
            Payment.pay($scope.orderpay,function(result){
                console.log(result);
                //   window.location.href = result.payUrl;
                window.open(result.payUrl,'_blank');
            })
        }

    })
