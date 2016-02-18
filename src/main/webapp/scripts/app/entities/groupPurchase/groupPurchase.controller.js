'use strict';

angular.module('citygardenWebApp')
    .controller('GroupPurchaseController', function ($scope, $state, GroupPurchase) {

        $scope.groupPurchases = [];
        $scope.loadAll = function() {
            GroupPurchase.query(function(result) {
               $scope.groupPurchases = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.groupPurchase = {
                id: null
            };
        };
    });
