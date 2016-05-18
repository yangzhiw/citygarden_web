'use strict';

describe('Controller Tests', function() {

    describe('ProvideMerchant Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockProvideMerchant;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockProvideMerchant = jasmine.createSpy('MockProvideMerchant');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ProvideMerchant': MockProvideMerchant
            };
            createController = function() {
                $injector.get('$controller')("ProvideMerchantDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'citygardenWebApp:provideMerchantUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
