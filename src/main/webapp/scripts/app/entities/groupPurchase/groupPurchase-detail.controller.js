'use strict';

angular.module('citygardenWebApp')
    .controller('GroupPurchaseDetailController', function ($scope, $rootScope, $stateParams, entity, GroupPurchase) {
        $scope.groupPurchase = entity;
        $scope.load = function (id) {
            GroupPurchase.get({id: id}, function(result) {
                $scope.groupPurchase = result;
            });
        };
        var unsubscribe = $rootScope.$on('citygardenWebApp:groupPurchaseUpdate', function(event, result) {
            $scope.groupPurchase = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
