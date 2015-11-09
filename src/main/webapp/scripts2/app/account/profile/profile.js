'use strict';

angular.module('talarion2App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('profile', {
                parent: 'account',
                url: '/profile',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/account/profile/profile.view.html',
                        controller: 'ProfileController'
                    }
                }
            });
    });
