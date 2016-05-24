'use strict';

angular.module('citygardenWebApp')
    .controller('NavbarController', function ($rootScope,$scope, $location, $state,Auth, Principal,DishSearch,DishData, ENV) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;
        $scope.inProduction = ENV === 'prod';

        $rootScope.dishData = [];

        $scope.logout = function () {
            Auth.logout();
            $state.go('home');
        };

        $scope.searchDish = function(name){
            $state.go('dishSearching');
            if(name != undefined){
                DishSearch.get({name:name},function(result){
                    DishData.set(result);
                    $state.go('dishSearch');
                });
            }
        }
    });
