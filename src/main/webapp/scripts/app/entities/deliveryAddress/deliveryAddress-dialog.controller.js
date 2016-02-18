'use strict';

angular.module('citygardenWebApp').controller('DeliveryAddressDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'DeliveryAddress',
        function($scope, $stateParams, $uibModalInstance, entity, DeliveryAddress) {

        $scope.deliveryAddress = entity;
        $scope.load = function(id) {
            DeliveryAddress.get({id : id}, function(result) {
                $scope.deliveryAddress = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('citygardenWebApp:deliveryAddressUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.deliveryAddress.id != null) {
                DeliveryAddress.update($scope.deliveryAddress, onSaveSuccess, onSaveError);
            } else {
                DeliveryAddress.save($scope.deliveryAddress, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
