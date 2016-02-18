'use strict';

describe('Controller Tests', function() {

    describe('VegetableProvide Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockVegetableProvide;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockVegetableProvide = jasmine.createSpy('MockVegetableProvide');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'VegetableProvide': MockVegetableProvide
            };
            createController = function() {
                $injector.get('$controller')("VegetableProvideDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'citygardenWebApp:vegetableProvideUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
