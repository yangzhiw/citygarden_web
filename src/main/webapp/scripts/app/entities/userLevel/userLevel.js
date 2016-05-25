'use strict';

angular.module('citygardenWebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('userLevel', {
                parent: 'entity',
                url: '/userLevels',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'citygardenWebApp.userLevel.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userLevel/userLevels.html',
                        controller: 'UserLevelController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('userLevel');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('userLevel.detail', {
                parent: 'entity',
                url: '/userLevel/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'citygardenWebApp.userLevel.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userLevel/userLevel-detail.html',
                        controller: 'UserLevelDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('userLevel');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'UserLevel', function($stateParams, UserLevel) {
                        return UserLevel.get({id : $stateParams.id});
                    }]
                }
            })
            .state('userLevel.new', {
                parent: 'userLevel',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/userLevel/userLevel-dialog.html',
                        controller: 'UserLevelDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('userLevel', null, { reload: true });
                    }, function() {
                        $state.go('userLevel');
                    })
                }]
            })
            .state('userLevel.edit', {
                parent: 'userLevel',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/userLevel/userLevel-dialog.html',
                        controller: 'UserLevelDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['UserLevel', function(UserLevel) {
                                return UserLevel.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('userLevel', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('userLevel.delete', {
                parent: 'userLevel',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/userLevel/userLevel-delete-dialog.html',
                        controller: 'UserLevelDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['UserLevel', function(UserLevel) {
                                return UserLevel.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('userLevel', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
