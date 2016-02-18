'use strict';

angular.module('citygardenWebApp')
	.controller('OrderDeleteController', function($scope, $uibModalInstance, entity, Order) {

        $scope.order = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Order.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
