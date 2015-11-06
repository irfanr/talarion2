'use strict';

angular.module('talarion2App')
    .config(function($stateProvider) {
        $stateProvider
            .state('village', {
                parent: 'entity',
                url: '/village',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/village/village-list.view.html',
                        controller: 'VillageController'
                    }
                }
            }).state('village-create', {
                parent: 'entity',
                url: '/village/create',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/village/village-create.view.html',
                        controller: 'VillageCreateController'
                    }
                }
            }).state('village-edit', {
                parent: 'entity',
                url: '/village/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/village/village-edit.view.html',
                        controller: 'VillageEditController'
                    }
                }
            });
    });
