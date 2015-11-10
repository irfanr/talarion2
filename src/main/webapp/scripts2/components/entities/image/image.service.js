'use strict';

angular.module('talarion2App')
    .factory('Image', function($resource) {
        return $resource('api/image/:id', {}, {
            'update': {
                method: 'PUT'
            }
        });
    });
