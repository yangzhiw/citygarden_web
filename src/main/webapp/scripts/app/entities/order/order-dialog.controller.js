'use strict';

angular.module('citygardenWebApp').controller('OrderDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Order',
        function($scope, $stateParams, $uibModalInstance, entity, Order) {

        $scope.order = entity;
        $scope.load = function(id) {
            Order.get({id : id}, function(result) {
                $scope.order = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('citygardenWebApp:orderUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.order.id != null) {
                Order.update($scope.order, onSaveSuccess, onSaveError);
            } else {
                Order.save($scope.order, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
