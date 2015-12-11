'use strict';

angular.module('talarion2App')
    .factory('File', function($resource) {
        return $resource('api/file/:id', {}, {
            'update': {
                method: 'PUT'
            }
        });
    });
