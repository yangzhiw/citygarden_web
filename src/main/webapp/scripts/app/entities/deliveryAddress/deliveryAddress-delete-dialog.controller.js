'use strict';

angular.module('citygardenWebApp')
	.controller('DeliveryAddressDeleteController', function($scope, $uibModalInstance, entity, DeliveryAddress) {

        $scope.deliveryAddress = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            DeliveryAddress.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
