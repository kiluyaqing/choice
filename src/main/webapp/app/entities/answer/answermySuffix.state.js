(function() {
    'use strict';

    angular
        .module('choiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('answermySuffix', {
            parent: 'entity',
            url: '/answermySuffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'choiceApp.answer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/answer/answersmySuffix.html',
                    controller: 'AnswerMySuffixController',
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
                    $translatePartialLoader.addPart('answer');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('answermySuffix-detail', {
            parent: 'entity',
            url: '/answermySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'choiceApp.answer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/answer/answermySuffix-detail.html',
                    controller: 'AnswerMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('answer');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Answer', function($stateParams, Answer) {
                    return Answer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'answermySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('answermySuffix-detail.edit', {
            parent: 'answermySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/answer/answermySuffix-dialog.html',
                    controller: 'AnswerMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Answer', function(Answer) {
                            return Answer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('answermySuffix.new', {
            parent: 'answermySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/answer/answermySuffix-dialog.html',
                    controller: 'AnswerMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                content: null,
                                result: null,
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
                    $state.go('answermySuffix', null, { reload: 'answermySuffix' });
                }, function() {
                    $state.go('answermySuffix');
                });
            }]
        })
        .state('answermySuffix.edit', {
            parent: 'answermySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/answer/answermySuffix-dialog.html',
                    controller: 'AnswerMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Answer', function(Answer) {
                            return Answer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('answermySuffix', null, { reload: 'answermySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('answermySuffix.delete', {
            parent: 'answermySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/answer/answermySuffix-delete-dialog.html',
                    controller: 'AnswerMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Answer', function(Answer) {
                            return Answer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('answermySuffix', null, { reload: 'answermySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
