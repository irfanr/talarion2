'use strict';

angular.module('talarion2App')
    .controller('ProfileController', function($scope, Upload, $timeout, $state, Principal, $rootScope) {

        $scope.uploadPic = function(file) {
            file.upload = Upload.upload({
                url: 'http://localhost:8080/api/upload',
                // fields: {
                //     'username': $scope.username
                // }, // additional data to send
                file: file
            });

            file.upload.then(function(response) {
                $timeout(function() {
                    file.result = response.data;
                    console.log('1: ' + response.data.profileImagePath);
                    $rootScope.account.profileImagePath = response.data.profileImagePath + '?decache=' + Math.random();

                    Principal.identity().then(function(account) {
                        $rootScope.account = account;
                    });

                });

                console.log('2');


            }, function(response) {
                if (response.status > 0) {
                    console.log('3');
                    $scope.errorMsg = response.status + ': ' + response.data;
                }
                console.log('4');
            }, function(evt) {
                // Math.min is to fix IE which reports 200% sometimes
                file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
                console.log('5');
            });

        }


        $state.go('profile');
    });
