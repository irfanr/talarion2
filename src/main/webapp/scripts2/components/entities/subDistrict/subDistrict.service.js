'use strict';

angular.module('talarion2App')
    .factory('SubDistrict', function($resource) {
        return $resource('api/subDistrict/:id', {}, {
            'update': {
                method: 'PUT'
            }
        });
    });
