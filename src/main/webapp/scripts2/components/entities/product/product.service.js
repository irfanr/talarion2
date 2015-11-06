'use strict';

angular.module('talarion2App')
    .factory('Product', function($resource) {
        return $resource('api/product/:id', {}, {
            'update': {
                method: 'PUT'
            }
        });
    });
