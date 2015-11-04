 'use strict';

angular.module('talarion2App')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-talarion2App-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-talarion2App-params')});
                }
                return response;
            }
        };
    });
