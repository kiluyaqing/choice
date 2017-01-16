'use strict';

describe('Controller Tests', function() {

    describe('Investigation Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockInvestigation, MockChoiceUser, MockQuestion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockInvestigation = jasmine.createSpy('MockInvestigation');
            MockChoiceUser = jasmine.createSpy('MockChoiceUser');
            MockQuestion = jasmine.createSpy('MockQuestion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Investigation': MockInvestigation,
                'ChoiceUser': MockChoiceUser,
                'Question': MockQuestion
            };
            createController = function() {
                $injector.get('$controller')("InvestigationMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'choiceApp:investigationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
