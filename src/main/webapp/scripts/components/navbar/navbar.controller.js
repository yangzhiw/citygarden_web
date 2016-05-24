'use strict';

angular.module('citygardenWebApp')
    .controller('NavbarController', function ($rootScope,$scope, $location, $state,Auth, Principal,DishSearch,DishData,ProvideMerchant, ENV) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;
        $scope.inProduction = ENV === 'prod';

        $rootScope.dishData = [];
        $scope.provides = [];
        $scope.isShow = false;

        $scope.loadAll = function(){
            ProvideMerchant.query(function(result){
                $scope.provides = result;
                console.log(result);
            })
        };

        $scope.loadAll();


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
        };
        $(".fenlei").hover(function(){
            $(this).css("background-color","#e7e7e7");
        },function(){
            $(this).css("background-color"," #c7ddef");
        });

        $scope.clickFenlei = function(){
            $scope.isShow = !$scope.isShow;
        };
        $scope.clickLi = function(){
            $scope.isShow = false;
        };
    });
