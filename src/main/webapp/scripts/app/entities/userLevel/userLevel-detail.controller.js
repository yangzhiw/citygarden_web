'use strict';

angular.module('citygardenWebApp')
    .controller('UserLevelDetailController', function ($scope, $rootScope, $stateParams, entity, UserLevel) {
        $scope.userLevel = entity;
        $scope.load = function (id) {
            UserLevel.get({id: id}, function(result) {
                $scope.userLevel = result;
            });
        };
        var unsubscribe = $rootScope.$on('citygardenWebApp:userLevelUpdate', function(event, result) {
            $scope.userLevel = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
