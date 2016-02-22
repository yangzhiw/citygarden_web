'use strict';

angular.module('citygardenWebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cartDetails', {
                parent: 'entity',
                url: '/cartDetailss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'citygardenWebApp.cartDetails.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cartDetails/cartDetailss.html',
                        controller: 'CartDetailsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cartDetails');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('cartDetails.detail', {
                parent: 'entity',
                url: '/cartDetails/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'citygardenWebApp.cartDetails.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cartDetails/cartDetails-detail.html',
                        controller: 'CartDetailsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cartDetails');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CartDetails', function($stateParams, CartDetails) {
                        return CartDetails.get({id : $stateParams.id});
                    }]
                }
            })
            .state('cartDetails.new', {
                parent: 'cartDetails',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cartDetails/cartDetails-dialog.html',
                        controller: 'CartDetailsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('cartDetails', null, { reload: true });
                    }, function() {
                        $state.go('cartDetails');
                    })
                }]
            })
            .state('cartDetails.edit', {
                parent: 'cartDetails',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cartDetails/cartDetails-dialog.html',
                        controller: 'CartDetailsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CartDetails', function(CartDetails) {
                                return CartDetails.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cartDetails', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('cartDetails.delete', {
                parent: 'cartDetails',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cartDetails/cartDetails-delete-dialog.html',
                        controller: 'CartDetailsDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CartDetails', function(CartDetails) {
                                return CartDetails.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cartDetails', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
