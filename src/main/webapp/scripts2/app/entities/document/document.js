'use strict';

angular.module('talarion2App')
    .config(function($stateProvider) {
        $stateProvider
            .state('document', {
                parent: 'entity',
                url: '/document',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/document/document-list.view.html',
                        controller: 'DocumentListController'
                    }
                }
            }).state('document-create', {
                parent: 'entity',
                url: '/document/create',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/document/document-create.view.html',
                        controller: 'DocumentCreateController'
                    }
                }
            });
    });
