'use strict';

angular.module('talarion2App')
    .factory('Category', function($resource) {
        return $resource('api/category/:id', {}, {
            'update': {
                method: 'PUT'
            }
        });
    });
