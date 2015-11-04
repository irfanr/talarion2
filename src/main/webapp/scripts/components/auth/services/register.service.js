'use strict';

angular.module('talarion2App')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


