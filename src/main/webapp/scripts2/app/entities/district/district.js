'use strict';

angular.module('talarion2App')
    .config(function($stateProvider) {
        $stateProvider
            .state('district', {
                parent: 'entity',
                url: '/district',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/district/district-list.view.html',
                        controller: 'DistrictController'
                    }
                }
            }).state('district-create', {
                parent: 'entity',
                url: '/district/create',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/district/district-create.view.html',
                        controller: 'DistrictCreateController'
                    }
                }
            }).state('district-edit', {
                parent: 'entity',
                url: '/district/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/district/district-edit.view.html',
                        controller: 'DistrictEditController'
                    }
                }
            });
    });
