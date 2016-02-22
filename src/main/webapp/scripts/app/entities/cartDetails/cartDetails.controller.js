'use strict';

angular.module('citygardenWebApp')
    .controller('CartDetailsController', function ($scope, $state, CartDetails) {

        $scope.cartDetailss = [];
        $scope.loadAll = function() {
            CartDetails.query(function(result) {
               $scope.cartDetailss = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.cartDetails = {
                id: null
            };
        };
    });
