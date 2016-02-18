'use strict';

angular.module('citygardenWebApp')
    .controller('DeliveryAddressController', function ($scope, $state, DeliveryAddress) {

        $scope.deliveryAddresss = [];
        $scope.loadAll = function() {
            DeliveryAddress.query(function(result) {
               $scope.deliveryAddresss = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.deliveryAddress = {
                id: null
            };
        };
    });
