'use strict';

angular.module('talarion2App')
    .config(function($stateProvider) {
        $stateProvider
            .state('subDistrict', {
                parent: 'entity',
                url: '/subDistrict',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/subDistrict/subDistrict-list.view.html',
                        controller: 'SubDistrictController'
                    }
                }
            }).state('subDistrict-create', {
                parent: 'entity',
                url: '/subDistrict/create',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/subDistrict/subDistrict-create.view.html',
                        controller: 'SubDistrictCreateController'
                    }
                }
            }).state('subDistrict-edit', {
                parent: 'entity',
                url: '/subDistrict/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/subDistrict/subDistrict-edit.view.html',
                        controller: 'SubDistrictEditController'
                    }
                }
            });
    });
