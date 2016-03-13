'use strict';

angular.module('citygardenWebApp')
    .controller('OrderBackController', function ($scope, $state,PayBack) {
        $scope.load = function() {
            PayBack.get(function(result) {
                $scope.orders = result;
                console.log(result)
            });
        };
        $scope.load();
    })
