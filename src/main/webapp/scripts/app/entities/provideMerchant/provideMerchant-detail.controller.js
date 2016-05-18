'use strict';

angular.module('citygardenWebApp')
    .controller('ProvideMerchantDetailController', function ($scope, $rootScope, $stateParams, entity, ProvideMerchant) {
        $scope.provideMerchant = entity;
        $scope.load = function (id) {
            ProvideMerchant.get({id: id}, function(result) {
                $scope.provideMerchant = result;
            });
        };
        var unsubscribe = $rootScope.$on('citygardenWebApp:provideMerchantUpdate', function(event, result) {
            $scope.provideMerchant = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
