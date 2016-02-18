'use strict';

angular.module('citygardenWebApp')
	.controller('GroupPurchaseDeleteController', function($scope, $uibModalInstance, entity, GroupPurchase) {

        $scope.groupPurchase = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            GroupPurchase.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
