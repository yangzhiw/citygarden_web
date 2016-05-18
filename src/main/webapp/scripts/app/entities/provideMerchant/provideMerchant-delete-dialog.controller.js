'use strict';

angular.module('citygardenWebApp')
	.controller('ProvideMerchantDeleteController', function($scope, $uibModalInstance, entity, ProvideMerchant) {

        $scope.provideMerchant = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ProvideMerchant.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
