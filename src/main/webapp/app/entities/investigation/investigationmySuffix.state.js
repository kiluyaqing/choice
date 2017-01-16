(function() {
    'use strict';

    angular
        .module('choiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('investigationmySuffix', {
            parent: 'entity',
            url: '/investigationmySuffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'choiceApp.investigation.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/investigation/investigationsmySuffix.html',
                    controller: 'InvestigationMySuffixController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('investigation');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('investigationmySuffix-detail', {
            parent: 'entity',
            url: '/investigationmySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'choiceApp.investigation.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/investigation/investigationmySuffix-detail.html',
                    controller: 'InvestigationMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('investigation');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Investigation', function($stateParams, Investigation) {
                    return Investigation.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'investigationmySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('investigationmySuffix-detail.edit', {
            parent: 'investigationmySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/investigation/investigationmySuffix-dialog.html',
                    controller: 'InvestigationMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Investigation', function(Investigation) {
                            return Investigation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('investigationmySuffix.new', {
            parent: 'investigationmySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/investigation/investigationmySuffix-dialog.html',
                    controller: 'InvestigationMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                content: null,
                                remark: null,
                                createdDate: null,
                                lastModifiedDate: null,
                                createdBy: null,
                                lastModifiedBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('investigationmySuffix', null, { reload: 'investigationmySuffix' });
                }, function() {
                    $state.go('investigationmySuffix');
                });
            }]
        })
        .state('investigationmySuffix.edit', {
            parent: 'investigationmySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/investigation/investigationmySuffix-dialog.html',
                    controller: 'InvestigationMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Investigation', function(Investigation) {
                            return Investigation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('investigationmySuffix', null, { reload: 'investigationmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('investigationmySuffix.delete', {
            parent: 'investigationmySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/investigation/investigationmySuffix-delete-dialog.html',
                    controller: 'InvestigationMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Investigation', function(Investigation) {
                            return Investigation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('investigationmySuffix', null, { reload: 'investigationmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
