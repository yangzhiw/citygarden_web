'use strict';

angular.module('citygardenWebApp')
    .controller('AnnouncementDetailController', function ($scope, $rootScope, $stateParams, entity, Announcement) {
        $scope.announcement = entity;
        $scope.load = function (id) {
            Announcement.get({id: id}, function(result) {
                $scope.announcement = result;
            });
        };
        var unsubscribe = $rootScope.$on('citygardenWebApp:announcementUpdate', function(event, result) {
            $scope.announcement = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
