'use strict';

angular.module('talarion2App')
    .controller('SidebarLeftController', function($rootScope, $scope, $location, $state, Auth, Principal, ENV) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;
        $scope.inProduction = ENV === 'prod';

        // Define $rootScope.account if html is refreshed
        Principal.identity().then(function(account) {
            $rootScope.account = account;
        });

        $scope.logout = function() {
            Auth.logout();
            $state.go('home');
        };
    });
