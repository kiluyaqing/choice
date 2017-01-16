(function() {
    'use strict';

    angular
        .module('choiceApp')
        .controller('ChoiceUserMySuffixDetailController', ChoiceUserMySuffixDetailController);

    ChoiceUserMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ChoiceUser', 'Investigation'];

    function ChoiceUserMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, ChoiceUser, Investigation) {
        var vm = this;

        vm.choiceUser = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('choiceApp:choiceUserUpdate', function(event, result) {
            vm.choiceUser = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
