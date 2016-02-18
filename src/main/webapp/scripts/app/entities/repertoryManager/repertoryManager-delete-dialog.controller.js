'use strict';

angular.module('citygardenWebApp')
	.controller('RepertoryManagerDeleteController', function($scope, $uibModalInstance, entity, RepertoryManager) {

        $scope.repertoryManager = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            RepertoryManager.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
