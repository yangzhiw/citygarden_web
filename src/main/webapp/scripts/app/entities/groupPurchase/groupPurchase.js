'use strict';

angular.module('citygardenWebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('groupPurchase', {
                parent: 'entity',
                url: '/groupPurchases',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'citygardenWebApp.groupPurchase.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/groupPurchase/groupPurchases.html',
                        controller: 'GroupPurchaseController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('groupPurchase');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('groupPurchase.detail', {
                parent: 'entity',
                url: '/groupPurchase/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'citygardenWebApp.groupPurchase.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/groupPurchase/groupPurchase-detail.html',
                        controller: 'GroupPurchaseDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('groupPurchase');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'GroupPurchase', function($stateParams, GroupPurchase) {
                        return GroupPurchase.get({id : $stateParams.id});
                    }]
                }
            })
            .state('groupPurchase.new', {
                parent: 'groupPurchase',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/groupPurchase/groupPurchase-dialog.html',
                        controller: 'GroupPurchaseDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('groupPurchase', null, { reload: true });
                    }, function() {
                        $state.go('groupPurchase');
                    })
                }]
            })
            .state('groupPurchase.edit', {
                parent: 'groupPurchase',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/groupPurchase/groupPurchase-dialog.html',
                        controller: 'GroupPurchaseDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['GroupPurchase', function(GroupPurchase) {
                                return GroupPurchase.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('groupPurchase', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('groupPurchase.delete', {
                parent: 'groupPurchase',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/groupPurchase/groupPurchase-delete-dialog.html',
                        controller: 'GroupPurchaseDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['GroupPurchase', function(GroupPurchase) {
                                return GroupPurchase.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('groupPurchase', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
