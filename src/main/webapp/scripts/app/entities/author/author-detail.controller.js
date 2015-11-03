'use strict';

angular.module('punicApp')
    .controller('AuthorDetailController', function ($scope, $rootScope, $stateParams, entity, Author, Book) {
        $scope.author = entity;
        $scope.load = function (id) {
            Author.get({id: id}, function(result) {
                $scope.author = result;
            });
        };
        var unsubscribe = $rootScope.$on('punicApp:authorUpdate', function(event, result) {
            $scope.author = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
