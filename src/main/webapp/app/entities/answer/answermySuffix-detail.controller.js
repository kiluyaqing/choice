(function() {
    'use strict';

    angular
        .module('choiceApp')
        .controller('AnswerMySuffixDetailController', AnswerMySuffixDetailController);

    AnswerMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Answer', 'Question'];

    function AnswerMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Answer, Question) {
        var vm = this;

        vm.answer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('choiceApp:answerUpdate', function(event, result) {
            vm.answer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
