(function() {
    'use strict';

    angular
        .module('choiceApp')
        .controller('ChoiceUserMySuffixDialogController', ChoiceUserMySuffixDialogController);

    ChoiceUserMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ChoiceUser', 'Investigation'];

    function ChoiceUserMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ChoiceUser, Investigation) {
        var vm = this;

        vm.choiceUser = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.investigations = Investigation.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.choiceUser.id !== null) {
                ChoiceUser.update(vm.choiceUser, onSaveSuccess, onSaveError);
            } else {
                ChoiceUser.save(vm.choiceUser, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('choiceApp:choiceUserUpdate', result);
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
