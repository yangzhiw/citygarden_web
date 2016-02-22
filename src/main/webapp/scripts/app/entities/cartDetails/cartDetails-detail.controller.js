'use strict';

angular.module('citygardenWebApp')
    .controller('CartDetailsDetailController', function ($scope, $rootScope, $stateParams, entity, CartDetails) {
        $scope.cartDetails = entity;
        $scope.load = function (id) {
            CartDetails.get({id: id}, function(result) {
                $scope.cartDetails = result;
            });
        };
        var unsubscribe = $rootScope.$on('citygardenWebApp:cartDetailsUpdate', function(event, result) {
            $scope.cartDetails = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
