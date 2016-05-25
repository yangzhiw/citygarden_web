'use strict';

angular.module('citygardenWebApp').controller('UserLevelDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserLevel',
        function($scope, $stateParams, $uibModalInstance, entity, UserLevel) {

        $scope.userLevel = entity;
        $scope.load = function(id) {
            UserLevel.get({id : id}, function(result) {
                $scope.userLevel = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('citygardenWebApp:userLevelUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.userLevel.id != null) {
                UserLevel.update($scope.userLevel, onSaveSuccess, onSaveError);
            } else {
                UserLevel.save($scope.userLevel, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
