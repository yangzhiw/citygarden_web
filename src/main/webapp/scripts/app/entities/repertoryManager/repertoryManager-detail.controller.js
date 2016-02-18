'use strict';

angular.module('citygardenWebApp')
    .controller('RepertoryManagerDetailController', function ($scope, $rootScope, $stateParams, entity, RepertoryManager) {
        $scope.repertoryManager = entity;
        $scope.load = function (id) {
            RepertoryManager.get({id: id}, function(result) {
                $scope.repertoryManager = result;
            });
        };
        var unsubscribe = $rootScope.$on('citygardenWebApp:repertoryManagerUpdate', function(event, result) {
            $scope.repertoryManager = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
