(function() {
    'use strict';

    angular
        .module('choiceApp')
        .controller('QuestionMySuffixDetailController', QuestionMySuffixDetailController);

    QuestionMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Question', 'Investigation', 'Answer'];

    function QuestionMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Question, Investigation, Answer) {
        var vm = this;

        vm.question = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('choiceApp:questionUpdate', function(event, result) {
            vm.question = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
