'use strict';

angular.module('talarion2App')
    .factory('District', function($resource) {
        return $resource('api/district/:id', {}, {
            'update': {
                method: 'PUT'
            }
        });
    });
