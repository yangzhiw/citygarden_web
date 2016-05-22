'use strict';

angular.module('citygardenWebApp')
    .controller('CartController', function ($scope, $state, Cart,orderData,CartToAccount) {

        $scope.carts = {};
        $scope.order = {
            totalPrice:Number,
            deliveryWay:"",
            orderStatus:"",
            orderItemList : []
        }
        $scope.loadAll = function() {
            Cart.query(function(result) {
               $scope.carts = result;
                console.log(result);
            });
        };
        $scope.loadAll();

        $scope.changeCount = function (cartDetail) {
            var count = cartDetail.count;
            var dishPrice = (Number)(cartDetail.dish.discountPrice);
            var r = /^[0-9]*[1-9][0-9]*$/
            if(r.test(count)){
                count = (Number)(count);
                cartDetail.subtotal = dishPrice * (count);
            }else{
                alert("输入正确的整数！")
            }
            Cart.update($scope.carts,function(result){
                console.log(result);
                $state.reload();
            })

        }

        $scope.decrement = function(cartDetail){
            var count = cartDetail.count;
            count = (Number)(count);
            var dishPrice = (Number)(cartDetail.dish.discountPrice);
            console.log(dishPrice);
            if(count>=2){
                cartDetail.count = count -1;
                cartDetail.subtotal = dishPrice * (count-1);
            }
            Cart.update($scope.carts,function(result){
                console.log(result);
                $state.reload();
            })
        }

        $scope.increment = function(cartDetail){
            var count = cartDetail.count;
            count = (Number)(count);
            var dishPrice = (Number)(cartDetail.dish.discountPrice);
            console.log(cartDetail.dish.discountPrice);
            if(count>=0){
                cartDetail.count = count +1;
                cartDetail.subtotal = dishPrice * (count+1);
            }
            Cart.update($scope.carts,function(result){
                console.log(result);
                $state.reload();
            })
        }


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
            $scope.order.orderItemList = [];
            var totalPrice = 0;
            for(var i=0; i<$scope.carts.cartDetailsList.length; i++) {
                if($scope.carts.cartDetailsList[i].check) {
                    delete $scope.carts.cartDetailsList[i].check;
                    $scope.order.orderItemList.push($scope.carts.cartDetailsList[i]);

                    totalPrice = totalPrice + $scope.carts.cartDetailsList[i].subtotal;
                }
            }

            $scope.order.totalPrice = totalPrice;
            $scope.order.deliveryWay = "1";
            $scope.order.orderStatus = "1";

            console.log($scope.order);
            if($scope.order.orderItemList.length != 0){
                CartToAccount.save($scope.order,function(result){
                    $state.go('accounts');
                })
            }

        }
    });
