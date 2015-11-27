'use strict';

angular.module('talarion2App')
	.controller('BookDeleteController', function($scope, $modalInstance, entity, Book) {

        $scope.book = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Book.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });