'use strict';

angular.module('citygardenWebApp').controller('CartDetailsDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'CartDetails',
        function($scope, $stateParams, $uibModalInstance, entity, CartDetails) {

        $scope.cartDetails = entity;
        $scope.load = function(id) {
            CartDetails.get({id : id}, function(result) {
                $scope.cartDetails = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('citygardenWebApp:cartDetailsUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cartDetails.id != null) {
                CartDetails.update($scope.cartDetails, onSaveSuccess, onSaveError);
            } else {
                CartDetails.save($scope.cartDetails, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
