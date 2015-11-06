'use strict';

angular.module('talarion2App')
    .factory('Province', function($resource) {
        return $resource('api/province/:id', {}, {
            'update': {
                method: 'PUT'
            }
        });
    });
