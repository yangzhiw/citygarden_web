'use strict';

angular.module('citygardenWebApp').controller('GroupPurchaseDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'GroupPurchase',
        function($scope, $stateParams, $uibModalInstance, entity, GroupPurchase) {

        $scope.groupPurchase = entity;
        $scope.load = function(id) {
            GroupPurchase.get({id : id}, function(result) {
                $scope.groupPurchase = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('citygardenWebApp:groupPurchaseUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.groupPurchase.id != null) {
                GroupPurchase.update($scope.groupPurchase, onSaveSuccess, onSaveError);
            } else {
                GroupPurchase.save($scope.groupPurchase, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
