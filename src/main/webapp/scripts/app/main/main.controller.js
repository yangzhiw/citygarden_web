//'use strict';
//
//angular.module('citygardenWebApp')
//    .controller('MainController', function ($scope, Principal) {
//        Principal.identity().then(function(account) {
//            $scope.account = account;
//            $scope.isAuthenticated = Principal.isAuthenticated;
//        });
//    });

'use strict';

angular.module('citygardenWebApp')
    .controller('MainController', function ($scope, $state, ProvideMerchant,Principal) {

        $scope.provideMerchants = [];

        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        $scope.loadAll = function() {
            ProvideMerchant.query(function(result) {
                $scope.provideMerchants = result;
                console.log(result);
            });
        };
        $scope.loadAll();

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.provideMerchant = {
                id: null
            };
        };
    });
