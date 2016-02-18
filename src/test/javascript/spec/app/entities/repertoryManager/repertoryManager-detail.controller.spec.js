'use strict';

describe('Controller Tests', function() {

    describe('RepertoryManager Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRepertoryManager;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRepertoryManager = jasmine.createSpy('MockRepertoryManager');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'RepertoryManager': MockRepertoryManager
            };
            createController = function() {
                $injector.get('$controller')("RepertoryManagerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'citygardenWebApp:repertoryManagerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
