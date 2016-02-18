'use strict';

angular.module('citygardenWebApp')
	.controller('AnnouncementDeleteController', function($scope, $uibModalInstance, entity, Announcement) {

        $scope.announcement = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Announcement.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
