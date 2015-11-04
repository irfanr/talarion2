'use strict';

angular.module('talarion2App')
    .controller('AuthorDetailController', function ($scope, $rootScope, $stateParams, entity, Author, Book) {
        $scope.author = entity;
        $scope.load = function (id) {
            Author.get({id: id}, function(result) {
                $scope.author = result;
            });
        };
        var unsubscribe = $rootScope.$on('talarion2App:authorUpdate', function(event, result) {
            $scope.author = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
