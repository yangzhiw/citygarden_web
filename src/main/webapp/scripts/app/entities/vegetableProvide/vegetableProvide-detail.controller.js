'use strict';

angular.module('citygardenWebApp')
    .controller('VegetableProvideDetailController', function ($scope, $rootScope, $stateParams, entity, VegetableProvide) {
        $scope.vegetableProvide = entity;
        $scope.load = function (id) {
            VegetableProvide.get({id: id}, function(result) {
                $scope.vegetableProvide = result;
            });
        };
        var unsubscribe = $rootScope.$on('citygardenWebApp:vegetableProvideUpdate', function(event, result) {
            $scope.vegetableProvide = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
