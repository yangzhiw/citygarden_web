'use strict';

angular.module('citygardenWebApp').controller('VegetableProvideDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'VegetableProvide',
        function($scope, $stateParams, $uibModalInstance, entity, VegetableProvide) {

        $scope.vegetableProvide = entity;
        $scope.load = function(id) {
            VegetableProvide.get({id : id}, function(result) {
                $scope.vegetableProvide = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('citygardenWebApp:vegetableProvideUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.vegetableProvide.id != null) {
                VegetableProvide.update($scope.vegetableProvide, onSaveSuccess, onSaveError);
            } else {
                VegetableProvide.save($scope.vegetableProvide, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
