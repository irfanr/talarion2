'use strict';

angular.module('talarion2App')
    .factory('Village', function($resource) {
        return $resource('api/village/:id', {}, {
            'update': {
                method: 'PUT'
            }
        });
    });
