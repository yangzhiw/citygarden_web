'use strict';

angular.module('citygardenWebApp')
    .controller('OrderController', function ($scope, $state, Order,Payment,orderData) {

        $scope.orders = [];
        $scope.isInit = true;
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

        $scope.selectOrderStatus = function(){
            $scope.isInit = false;
        };

 })
    .controller('OrderPayController', function ($scope,$stateParams, $state, Order,Payment,md5) {

        $scope.order = {};
        $scope.orderpay = {
            id:"",
            total : "",
            bankCode : ""
        }

        $scope.load = function(){
            Order.get({id : $stateParams.id},function(result){
                $scope.order = result;
                console.log(result);
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

        $scope.bcpay = function(){
            console.log("ll");

            $scope.app_id = "c5d1cba1-5e3f-4ba0-941d-9b0a371fe719";
            $scope.app_secret = "39a7a518-9ac8-4a9e-87bc-7885f33cf18c";
            $scope.title = "test1";
            $scope.amount = "1";
            $scope.out_trade_no = $scope.order.id;
           //  $scope.out_trade_no ="test3333337";

            $scope.sign = md5.createHash($scope.app_id + $scope.title + $scope.amount + $scope.out_trade_no + $scope.app_secret );

            BC.click({
                "title":$scope.title, //商品名
                "amount":$scope.amount,  //总价（分）
                "out_trade_no":$scope.out_trade_no, //自定义订单号
                "trace_id":"testcustomer", //自定义购买者id
                "sign":$scope.sign, //商品信息hash值，含义和生成方式见下文
                "return_url" : "http://localhost:8082/order-success.html", //支付成功后跳转的商户页面,可选，默认为http://payservice.beecloud.cn/spay/result.php
                "optional" : {"hello":"1"} //可选，自定义webhook
            },function(res){
                console.log(res);
            });

            Payment.update($scope.order,function(result){
                console.log(result);
            } ,function() {
                $state.go('orderpay', {id:$scope.order.id}, { reload: true });
            })

            /**
             * click调用错误返回：默认行为console.log(err)
             */
            BC.err = function(err) {
                //err 为object, 例 ｛”ERROR“ : "xxxx"｝;
                alert(err.ERROR);
            }
        }

    })
