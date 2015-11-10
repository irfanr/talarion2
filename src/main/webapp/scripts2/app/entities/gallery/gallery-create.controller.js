'use strict';

angular.module('talarion2App')
    .controller('GalleryCreateController', function($scope, Upload, $timeout, $state, Principal, $rootScope) {

        $scope.uploadPic = function(file) {
            file.upload = Upload.upload({
                url: '/api/image/file/gallery/upload',
                // fields: {
                //     'username': $scope.username
                // }, // additional data to send
                file: file
            });

            file.upload.then(function(response) {
                $timeout(function() {
                    file.result = response.data;
                    // console.log('1: ' + response.data.profileImagePath);

                    $state.go('gallery');

                });

            }, function(response) {
                if (response.status > 0) {
                    $scope.errorMsg = response.status + ': ' + response.data;
                }
            }, function(evt) {
                // Math.min is to fix IE which reports 200% sometimes
                file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
            });

        }
        
    });
