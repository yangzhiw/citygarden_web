'use strict';

angular.module('citygardenWebApp')
	.controller('VegetableProvideDeleteController', function($scope, $uibModalInstance, entity, VegetableProvide) {

        $scope.vegetableProvide = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            VegetableProvide.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
