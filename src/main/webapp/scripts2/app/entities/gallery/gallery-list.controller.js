'use strict';

angular.module('talarion2App')
    .controller('GalleryListController', function($scope, $state, Image, ParseLinks, growl) {

        $scope.page = 0;

        $scope.imageList = [];

        $scope.callServer = function callServer(tableState) {

            $scope.page = $scope.page + 1;

            $scope.isLoading = true;

            // var pagination = tableState.pagination;

            // var start = pagination.start || 0; // This is NOT the page number, but the index of item in the list that you want to use to display the table.
            // var number = pagination.number || 10; // Number of entries showed per page.
            // var numberOfPages = pagination.numberOfPages || 1;
            // $scope.page = 0;

            var nameSearchCrit = '';

            // if (tableState.search.predicateObject != undefined) {
            //     nameSearchCrit = tableState.search.predicateObject.name || '';
            // }

            Image.query({
                page: $scope.page,
                size: 12,
                name: nameSearchCrit
            }, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));

                // $scope.imageList.push.apply($scope.imageList, result);\

                angular.forEach(result, function(newImage) {
                    $scope.imageList.push(newImage);
                });

                // $scope.imageList = result;
                // tableState.pagination.numberOfPages = 1 * headers('X-Total-Pages');
                // tableState.pagination.number = 1 * headers('X-Size');
                // tableState.pagination.start = tableState.pagination.number * (page - 1);
                $scope.isLoading = false;

                $scope.totalPages = headers('X-Total-Pages');

                console.log('X-Total-Count: ' + headers('X-Total-Count'));
                console.log('X-Number-Of-Elements:' + headers('X-Number-Of-Elements'));
                console.log('X-Total-Pages:' + headers('X-Total-Pages'));
                console.log('X-Number:' + headers('X-Number'));
                console.log('X-Size:' + headers('X-Size'));

                // console.log('tableState.pagination.start: ' + tableState.pagination.start);
                // console.log('tableState.pagination.numberOfPages: ' + tableState.pagination.numberOfPages);
                // console.log('tableState.pagination.number: ' + tableState.pagination.number);

                $state.go('gallery');
            });

        };

        $scope.callServer(null);

    });
