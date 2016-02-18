'use strict';

angular.module('citygardenWebApp')
    .controller('DeliveryAddressDetailController', function ($scope, $rootScope, $stateParams, entity, DeliveryAddress) {
        $scope.deliveryAddress = entity;
        $scope.load = function (id) {
            DeliveryAddress.get({id: id}, function(result) {
                $scope.deliveryAddress = result;
            });
        };
        var unsubscribe = $rootScope.$on('citygardenWebApp:deliveryAddressUpdate', function(event, result) {
            $scope.deliveryAddress = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
