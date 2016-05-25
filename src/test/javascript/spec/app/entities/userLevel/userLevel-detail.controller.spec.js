'use strict';

describe('Controller Tests', function() {

    describe('UserLevel Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockUserLevel;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockUserLevel = jasmine.createSpy('MockUserLevel');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'UserLevel': MockUserLevel
            };
            createController = function() {
                $injector.get('$controller')("UserLevelDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'citygardenWebApp:userLevelUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
