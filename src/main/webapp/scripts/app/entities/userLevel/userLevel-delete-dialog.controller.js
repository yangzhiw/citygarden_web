'use strict';

angular.module('citygardenWebApp')
	.controller('UserLevelDeleteController', function($scope, $uibModalInstance, entity, UserLevel) {

        $scope.userLevel = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            UserLevel.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
