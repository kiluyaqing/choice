(function() {
    'use strict';

    angular
        .module('choiceApp')
        .controller('ChoiceUserMySuffixDeleteController',ChoiceUserMySuffixDeleteController);

    ChoiceUserMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'ChoiceUser'];

    function ChoiceUserMySuffixDeleteController($uibModalInstance, entity, ChoiceUser) {
        var vm = this;

        vm.choiceUser = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ChoiceUser.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
