'use strict';

angular.module('citygardenWebApp')
    .controller('AnnouncementController', function ($scope, $state, Announcement) {

        $scope.announcements = [];
        $scope.loadAll = function() {
            Announcement.query(function(result) {
               $scope.announcements = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.announcement = {
                name: null,
                id: null
            };
        };
    });
