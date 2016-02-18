'use strict';

angular.module('citygardenWebApp')
    .controller('RepertoryManagerController', function ($scope, $state, RepertoryManager) {

        $scope.repertoryManagers = [];
        $scope.loadAll = function() {
            RepertoryManager.query(function(result) {
               $scope.repertoryManagers = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.repertoryManager = {
                name: null,
                nowCount: null,
                totalCount: null,
                id: null
            };
        };
    });
