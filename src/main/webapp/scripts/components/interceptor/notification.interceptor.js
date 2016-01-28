 'use strict';

angular.module('citygardenWebApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-citygardenWebApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-citygardenWebApp-params')});
                }
                return response;
            }
        };
    });
