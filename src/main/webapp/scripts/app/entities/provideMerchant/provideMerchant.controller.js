'use strict';

angular.module('citygardenWebApp')
    .controller('ProvideMerchantController', function ($scope, $state, ProvideMerchant) {

        $scope.provideMerchants = [];
        $scope.loadAll = function() {
            ProvideMerchant.query(function(result) {
               $scope.provideMerchants = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.provideMerchant = {
                id: null
            };
        };
    });
