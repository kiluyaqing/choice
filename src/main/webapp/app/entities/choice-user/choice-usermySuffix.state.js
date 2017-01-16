(function() {
    'use strict';

    angular
        .module('choiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('choice-usermySuffix', {
            parent: 'entity',
            url: '/choice-usermySuffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'choiceApp.choiceUser.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/choice-user/choice-usersmySuffix.html',
                    controller: 'ChoiceUserMySuffixController',
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
                    $translatePartialLoader.addPart('choiceUser');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('choice-usermySuffix-detail', {
            parent: 'entity',
            url: '/choice-usermySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'choiceApp.choiceUser.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/choice-user/choice-usermySuffix-detail.html',
                    controller: 'ChoiceUserMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('choiceUser');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ChoiceUser', function($stateParams, ChoiceUser) {
                    return ChoiceUser.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'choice-usermySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('choice-usermySuffix-detail.edit', {
            parent: 'choice-usermySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/choice-user/choice-usermySuffix-dialog.html',
                    controller: 'ChoiceUserMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ChoiceUser', function(ChoiceUser) {
                            return ChoiceUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('choice-usermySuffix.new', {
            parent: 'choice-usermySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/choice-user/choice-usermySuffix-dialog.html',
                    controller: 'ChoiceUserMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                userName: null,
                                passwordHash: null,
                                email: null,
                                createdDate: null,
                                lastModifiedDate: null,
                                createdBy: null,
                                lastModifiedBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('choice-usermySuffix', null, { reload: 'choice-usermySuffix' });
                }, function() {
                    $state.go('choice-usermySuffix');
                });
            }]
        })
        .state('choice-usermySuffix.edit', {
            parent: 'choice-usermySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/choice-user/choice-usermySuffix-dialog.html',
                    controller: 'ChoiceUserMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ChoiceUser', function(ChoiceUser) {
                            return ChoiceUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('choice-usermySuffix', null, { reload: 'choice-usermySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('choice-usermySuffix.delete', {
            parent: 'choice-usermySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/choice-user/choice-usermySuffix-delete-dialog.html',
                    controller: 'ChoiceUserMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ChoiceUser', function(ChoiceUser) {
                            return ChoiceUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('choice-usermySuffix', null, { reload: 'choice-usermySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
