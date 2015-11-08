'use strict';

angular.module('talarion2App')
    .config(function($stateProvider) {
        $stateProvider
            .state('product', {
                parent: 'entity',
                url: '/product',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/product/product-list.view.html',
                        controller: 'ProductController'
                    }
                }
            }).state('product-create', {
                parent: 'entity',
                url: '/product/create',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/product/product-create2.view.html',
                        controller: 'ProductCreate2Controller'
                    }
                }
            }).state('product-edit', {
                parent: 'entity',
                url: '/product/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/product/product-edit2.view.html',
                        controller: 'ProductEdit2Controller'
                    }
                }
            });
    });
