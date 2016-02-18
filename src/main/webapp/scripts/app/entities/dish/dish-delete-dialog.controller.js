'use strict';

angular.module('citygardenWebApp')
	.controller('DishDeleteController', function($scope, $uibModalInstance, entity, Dish) {

        $scope.dish = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Dish.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
