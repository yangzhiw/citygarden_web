'use strict';

angular.module('citygardenWebApp')
    .controller('DishController', function ($scope, $state, Dish,DishPhoto) {

        $scope.dishs = [];
        $scope.loadAll = function() {
            //Dish.query(function(result) {
            //   $scope.dishs = result;
            //    console.log(result);
            //});

            DishPhoto.query(function(result) {
                $scope.dishPhoto = result.photo;
                console.log(result);
            })
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.dish = {
                id: null
            };
        };
    });
