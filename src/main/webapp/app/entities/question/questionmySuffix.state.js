(function() {
    'use strict';

    angular
        .module('choiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('questionmySuffix', {
            parent: 'entity',
            url: '/questionmySuffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'choiceApp.question.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/question/questionsmySuffix.html',
                    controller: 'QuestionMySuffixController',
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
                    $translatePartialLoader.addPart('question');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('questionmySuffix-detail', {
            parent: 'entity',
            url: '/questionmySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'choiceApp.question.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/question/questionmySuffix-detail.html',
                    controller: 'QuestionMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('question');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Question', function($stateParams, Question) {
                    return Question.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'questionmySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('questionmySuffix-detail.edit', {
            parent: 'questionmySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question/questionmySuffix-dialog.html',
                    controller: 'QuestionMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Question', function(Question) {
                            return Question.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('questionmySuffix.new', {
            parent: 'questionmySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question/questionmySuffix-dialog.html',
                    controller: 'QuestionMySuffixDialogController',
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
                    $state.go('questionmySuffix', null, { reload: 'questionmySuffix' });
                }, function() {
                    $state.go('questionmySuffix');
                });
            }]
        })
        .state('questionmySuffix.edit', {
            parent: 'questionmySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question/questionmySuffix-dialog.html',
                    controller: 'QuestionMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Question', function(Question) {
                            return Question.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('questionmySuffix', null, { reload: 'questionmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('questionmySuffix.delete', {
            parent: 'questionmySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question/questionmySuffix-delete-dialog.html',
                    controller: 'QuestionMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Question', function(Question) {
                            return Question.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('questionmySuffix', null, { reload: 'questionmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
