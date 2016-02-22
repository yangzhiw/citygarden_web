'use strict';

angular.module('citygardenWebApp')
	.controller('CartDetailsDeleteController', function($scope, $uibModalInstance, entity, CartDetails) {

        $scope.cartDetails = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CartDetails.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
