'use strict';

angular.module('talarion2App')
    .config(function($stateProvider) {
        $stateProvider
            .state('gallery', {
                parent: 'entity',
                url: '/gallery',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/gallery/gallery-list.view.html',
                        controller: 'GalleryListController'
                    }
                }
            }).state('gallery-create', {
                parent: 'entity',
                url: '/gallery/create',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/gallery/gallery-create.view.html',
                        controller: 'GalleryCreateController'
                    }
                }
            });
    });
