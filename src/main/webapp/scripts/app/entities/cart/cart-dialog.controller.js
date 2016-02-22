'use strict';

angular.module('citygardenWebApp').controller('CartDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cart',
        function($scope, $stateParams, $uibModalInstance, entity, Cart) {

        $scope.cart = entity;
        $scope.load = function(id) {
            Cart.get({id : id}, function(result) {
                $scope.cart = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('citygardenWebApp:cartUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cart.id != null) {
                Cart.update($scope.cart, onSaveSuccess, onSaveError);
            } else {
                Cart.save($scope.cart, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
