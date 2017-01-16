(function() {
    'use strict';

    angular
        .module('choiceApp')
        .controller('InvestigationMySuffixDialogController', InvestigationMySuffixDialogController);

    InvestigationMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Investigation', 'ChoiceUser', 'Question'];

    function InvestigationMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Investigation, ChoiceUser, Question) {
        var vm = this;

        vm.investigation = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.choiceusers = ChoiceUser.query();
        vm.questions = Question.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.investigation.id !== null) {
                Investigation.update(vm.investigation, onSaveSuccess, onSaveError);
            } else {
                Investigation.save(vm.investigation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('choiceApp:investigationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.lastModifiedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
