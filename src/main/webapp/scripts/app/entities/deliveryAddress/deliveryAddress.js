'use strict';

angular.module('citygardenWebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('deliveryAddress', {
                parent: 'entity',
                url: '/deliveryAddresss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'citygardenWebApp.deliveryAddress.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/deliveryAddress/deliveryAddresss.html',
                        controller: 'DeliveryAddressController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('deliveryAddress');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('deliveryAddress.detail', {
                parent: 'entity',
                url: '/deliveryAddress/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'citygardenWebApp.deliveryAddress.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/deliveryAddress/deliveryAddress-detail.html',
                        controller: 'DeliveryAddressDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('deliveryAddress');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DeliveryAddress', function($stateParams, DeliveryAddress) {
                        return DeliveryAddress.get({id : $stateParams.id});
                    }]
                }
            })
            .state('deliveryAddress.new', {
                parent: 'deliveryAddress',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/deliveryAddress/deliveryAddress-dialog.html',
                        controller: 'DeliveryAddressDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('deliveryAddress', null, { reload: true });
                    }, function() {
                        $state.go('deliveryAddress');
                    })
                }]
            })
            .state('deliveryAddress.edit', {
                parent: 'deliveryAddress',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/deliveryAddress/deliveryAddress-dialog.html',
                        controller: 'DeliveryAddressDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DeliveryAddress', function(DeliveryAddress) {
                                return DeliveryAddress.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('deliveryAddress', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('deliveryAddress.delete', {
                parent: 'deliveryAddress',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/deliveryAddress/deliveryAddress-delete-dialog.html',
                        controller: 'DeliveryAddressDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DeliveryAddress', function(DeliveryAddress) {
                                return DeliveryAddress.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('deliveryAddress', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
