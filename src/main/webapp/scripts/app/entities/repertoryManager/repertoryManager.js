'use strict';

angular.module('citygardenWebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('repertoryManager', {
                parent: 'entity',
                url: '/repertoryManagers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'citygardenWebApp.repertoryManager.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/repertoryManager/repertoryManagers.html',
                        controller: 'RepertoryManagerController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('repertoryManager');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('repertoryManager.detail', {
                parent: 'entity',
                url: '/repertoryManager/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'citygardenWebApp.repertoryManager.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/repertoryManager/repertoryManager-detail.html',
                        controller: 'RepertoryManagerDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('repertoryManager');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'RepertoryManager', function($stateParams, RepertoryManager) {
                        return RepertoryManager.get({id : $stateParams.id});
                    }]
                }
            })
            .state('repertoryManager.new', {
                parent: 'repertoryManager',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/repertoryManager/repertoryManager-dialog.html',
                        controller: 'RepertoryManagerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    nowCount: null,
                                    totalCount: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('repertoryManager', null, { reload: true });
                    }, function() {
                        $state.go('repertoryManager');
                    })
                }]
            })
            .state('repertoryManager.edit', {
                parent: 'repertoryManager',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/repertoryManager/repertoryManager-dialog.html',
                        controller: 'RepertoryManagerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['RepertoryManager', function(RepertoryManager) {
                                return RepertoryManager.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('repertoryManager', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('repertoryManager.delete', {
                parent: 'repertoryManager',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/repertoryManager/repertoryManager-delete-dialog.html',
                        controller: 'RepertoryManagerDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['RepertoryManager', function(RepertoryManager) {
                                return RepertoryManager.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('repertoryManager', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
