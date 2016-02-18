'use strict';

angular.module('citygardenWebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('vegetableProvide', {
                parent: 'entity',
                url: '/vegetableProvides',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'citygardenWebApp.vegetableProvide.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/vegetableProvide/vegetableProvides.html',
                        controller: 'VegetableProvideController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vegetableProvide');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('vegetableProvide.detail', {
                parent: 'entity',
                url: '/vegetableProvide/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'citygardenWebApp.vegetableProvide.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/vegetableProvide/vegetableProvide-detail.html',
                        controller: 'VegetableProvideDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vegetableProvide');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'VegetableProvide', function($stateParams, VegetableProvide) {
                        return VegetableProvide.get({id : $stateParams.id});
                    }]
                }
            })
            .state('vegetableProvide.new', {
                parent: 'vegetableProvide',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/vegetableProvide/vegetableProvide-dialog.html',
                        controller: 'VegetableProvideDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('vegetableProvide', null, { reload: true });
                    }, function() {
                        $state.go('vegetableProvide');
                    })
                }]
            })
            .state('vegetableProvide.edit', {
                parent: 'vegetableProvide',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/vegetableProvide/vegetableProvide-dialog.html',
                        controller: 'VegetableProvideDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['VegetableProvide', function(VegetableProvide) {
                                return VegetableProvide.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('vegetableProvide', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('vegetableProvide.delete', {
                parent: 'vegetableProvide',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/vegetableProvide/vegetableProvide-delete-dialog.html',
                        controller: 'VegetableProvideDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['VegetableProvide', function(VegetableProvide) {
                                return VegetableProvide.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('vegetableProvide', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
