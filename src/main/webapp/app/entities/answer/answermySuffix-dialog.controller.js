(function() {
    'use strict';

    angular
        .module('choiceApp')
        .controller('AnswerMySuffixDialogController', AnswerMySuffixDialogController);

    AnswerMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Answer', 'Question'];

    function AnswerMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Answer, Question) {
        var vm = this;

        vm.answer = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.questions = Question.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.answer.id !== null) {
                Answer.update(vm.answer, onSaveSuccess, onSaveError);
            } else {
                Answer.save(vm.answer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('choiceApp:answerUpdate', result);
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
