'use strict';

angular.module('citygardenWebApp')
    .controller('CartDetailController', function ($scope, $rootScope, $stateParams, entity, Cart) {
        $scope.cart = entity;
        $scope.load = function (id) {
            Cart.get({id: id}, function(result) {
                $scope.cart = result;
            });
        };
        var unsubscribe = $rootScope.$on('citygardenWebApp:cartUpdate', function(event, result) {
            $scope.cart = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
