'use strict';

angular.module('talarion2App')
	.controller('AuthorDeleteController', function($scope, $modalInstance, entity, Author) {

        $scope.author = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Author.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });