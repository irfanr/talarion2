'use strict';

angular.module('talarion2App').controller('AuthorDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Author', 'Book',
        function($scope, $stateParams, $modalInstance, entity, Author, Book) {

        $scope.author = entity;
        $scope.books = Book.query();
        $scope.load = function(id) {
            Author.get({id : id}, function(result) {
                $scope.author = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('talarion2App:authorUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.author.id != null) {
                Author.update($scope.author, onSaveSuccess, onSaveError);
            } else {
                Author.save($scope.author, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
