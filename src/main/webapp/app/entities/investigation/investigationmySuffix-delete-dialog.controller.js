(function() {
    'use strict';

    angular
        .module('choiceApp')
        .controller('InvestigationMySuffixDeleteController',InvestigationMySuffixDeleteController);

    InvestigationMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Investigation'];

    function InvestigationMySuffixDeleteController($uibModalInstance, entity, Investigation) {
        var vm = this;

        vm.investigation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Investigation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
