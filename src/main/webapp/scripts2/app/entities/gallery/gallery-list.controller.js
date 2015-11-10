'use strict';

angular.module('talarion2App')
    .controller('GalleryListController', function($scope, $state, Image, ParseLinks, growl) {

        $scope.imageList = {};

        $scope.callServer = function callServer(tableState) {

            $scope.isLoading = true;

            // var pagination = tableState.pagination;

            // var start = pagination.start || 0; // This is NOT the page number, but the index of item in the list that you want to use to display the table.
            // var number = pagination.number || 10; // Number of entries showed per page.
            // var numberOfPages = pagination.numberOfPages || 1;
            var page =0;

            var nameSearchCrit = '';

            // if (tableState.search.predicateObject != undefined) {
            //     nameSearchCrit = tableState.search.predicateObject.name || '';
            // }

            Image.query({
                page: page,
                size: 12,
                name: nameSearchCrit
            }, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.imageList = result;
                tableState.pagination.numberOfPages = 1 * headers('X-Total-Pages');
                tableState.pagination.number = 1 * headers('X-Size');
                tableState.pagination.start = tableState.pagination.number * (page - 1);
                $scope.isLoading = false;

                // console.log('tableState.pagination.start: ' + tableState.pagination.start);
                // console.log('tableState.pagination.numberOfPages: ' + tableState.pagination.numberOfPages);
                // console.log('tableState.pagination.number: ' + tableState.pagination.number);

                $state.go('gallery');
            });

        };

        $scope.callServer(null);

    });
