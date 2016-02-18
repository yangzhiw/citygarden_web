'use strict';

angular.module('citygardenWebApp')
    .controller('VegetableProvideController', function ($scope, $state, VegetableProvide) {

        $scope.vegetableProvides = [];
        $scope.loadAll = function() {
            VegetableProvide.query(function(result) {
               $scope.vegetableProvides = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.vegetableProvide = {
                id: null
            };
        };
    });
