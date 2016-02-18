'use strict';

angular.module('citygardenWebApp').controller('AnnouncementDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Announcement',
        function($scope, $stateParams, $uibModalInstance, entity, Announcement) {

        $scope.announcement = entity;
        $scope.load = function(id) {
            Announcement.get({id : id}, function(result) {
                $scope.announcement = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('citygardenWebApp:announcementUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.announcement.id != null) {
                Announcement.update($scope.announcement, onSaveSuccess, onSaveError);
            } else {
                Announcement.save($scope.announcement, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
