'use strict';

angular.module('talarion2App')
    .config(function($stateProvider) {
        $stateProvider
            .state('province', {
                parent: 'entity',
                url: '/province',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/province/province-list.view.html',
                        controller: 'ProvinceController'
                    }
                }
            }).state('province-create', {
                parent: 'entity',
                url: '/province/create',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/province/province-create.view.html',
                        controller: 'ProvinceCreateController'
                    }
                }
            }).state('province-edit', {
                parent: 'entity',
                url: '/province/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/province/province-edit.view.html',
                        controller: 'ProvinceEditController'
                    }
                }
            });
    });
