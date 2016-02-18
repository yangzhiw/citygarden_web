'use strict';

describe('Controller Tests', function() {

    describe('DeliveryAddress Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDeliveryAddress;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDeliveryAddress = jasmine.createSpy('MockDeliveryAddress');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'DeliveryAddress': MockDeliveryAddress
            };
            createController = function() {
                $injector.get('$controller')("DeliveryAddressDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'citygardenWebApp:deliveryAddressUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
