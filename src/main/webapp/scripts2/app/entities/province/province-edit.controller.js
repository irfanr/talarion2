'use strict';

/**
 * @ngdoc function
 * @name yoAngularApp.controller:AboutCtrl
 * @description # AboutCtrl Controller of the yoAngularApp
 */
angular.module('talarion2App').controller('ProvinceEditController',
    function($scope, $state, $stateParams, Province, growl) {

        $scope.submitted = false;

        $scope.province = {};

        $scope.load = function(id) {
            Province.get({
                id: id
            }, function(result) {
                $scope.province = result;
            });
        };
        $scope.load($stateParams.id);

        $scope.save = function() {

            $scope.submitted = true;

            if ($scope.editForm.$valid) {

                Province.update($scope.province, function() {

                    growl.info("Province successfully edited ", {});

                    $state.go('province')
                });

            }

        };

    });
