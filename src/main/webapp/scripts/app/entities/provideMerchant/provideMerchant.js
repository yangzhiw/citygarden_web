'use strict';

angular.module('citygardenWebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('provideMerchant', {
                parent: 'entity',
                url: '/provideMerchants',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/provideMerchant/provideMerchants.html',
                        controller: 'ProvideMerchantController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('provideMerchant');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('provideMerchant.detail', {
                parent: 'entity',
                url: '/provideMerchant/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'citygardenWebApp.provideMerchant.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/provideMerchant/provideMerchant-detail.html',
                        controller: 'ProvideMerchantDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('provideMerchant');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ProvideMerchant', function($stateParams, ProvideMerchant) {
                        return ProvideMerchant.get({id : $stateParams.id});
                    }]
                }
            })
            .state('provideMerchant.new', {
                parent: 'provideMerchant',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/provideMerchant/provideMerchant-dialog.html',
                        controller: 'ProvideMerchantDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('provideMerchant', null, { reload: true });
                    }, function() {
                        $state.go('provideMerchant');
                    })
                }]
            })
            .state('provideMerchant.edit', {
                parent: 'provideMerchant',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/provideMerchant/provideMerchant-dialog.html',
                        controller: 'ProvideMerchantDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ProvideMerchant', function(ProvideMerchant) {
                                return ProvideMerchant.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('provideMerchant', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('provideMerchant.delete', {
                parent: 'provideMerchant',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/provideMerchant/provideMerchant-delete-dialog.html',
                        controller: 'ProvideMerchantDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ProvideMerchant', function(ProvideMerchant) {
                                return ProvideMerchant.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('provideMerchant', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
