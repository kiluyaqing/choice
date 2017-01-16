(function() {
    'use strict';

    angular
        .module('choiceApp')
        .controller('InvestigationMySuffixDetailController', InvestigationMySuffixDetailController);

    InvestigationMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Investigation', 'ChoiceUser', 'Question'];

    function InvestigationMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Investigation, ChoiceUser, Question) {
        var vm = this;

        vm.investigation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('choiceApp:investigationUpdate', function(event, result) {
            vm.investigation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
