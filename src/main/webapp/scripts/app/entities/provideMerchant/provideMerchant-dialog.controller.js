'use strict';

angular.module('citygardenWebApp').controller('ProvideMerchantDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProvideMerchant',
        function($scope, $stateParams, $uibModalInstance, entity, ProvideMerchant) {

        $scope.provideMerchant = entity;
        $scope.load = function(id) {
            ProvideMerchant.get({id : id}, function(result) {
                $scope.provideMerchant = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('citygardenWebApp:provideMerchantUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.provideMerchant.id != null) {
                ProvideMerchant.update($scope.provideMerchant, onSaveSuccess, onSaveError);
            } else {
                ProvideMerchant.save($scope.provideMerchant, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
