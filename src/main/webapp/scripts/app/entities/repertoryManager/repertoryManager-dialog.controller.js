'use strict';

angular.module('citygardenWebApp').controller('RepertoryManagerDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'RepertoryManager',
        function($scope, $stateParams, $uibModalInstance, entity, RepertoryManager) {

        $scope.repertoryManager = entity;
        $scope.load = function(id) {
            RepertoryManager.get({id : id}, function(result) {
                $scope.repertoryManager = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('citygardenWebApp:repertoryManagerUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.repertoryManager.id != null) {
                RepertoryManager.update($scope.repertoryManager, onSaveSuccess, onSaveError);
            } else {
                RepertoryManager.save($scope.repertoryManager, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
