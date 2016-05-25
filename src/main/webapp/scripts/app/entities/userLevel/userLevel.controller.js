'use strict';

angular.module('citygardenWebApp')
    .controller('UserLevelController', function ($scope, $state, UserLevel) {

        $scope.userLevels = [];
        $scope.loadAll = function() {
            UserLevel.query(function(result) {
               $scope.userLevels = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.userLevel = {
                id: null
            };
        };
    });
