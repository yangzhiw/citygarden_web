'use strict';

angular.module('citygardenWebApp')
	.controller('CartDeleteController', function($scope, $uibModalInstance, entity, Cart) {

        $scope.cart = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Cart.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
