'use strict';

/**
 * @ngdoc function
 * @name yoAngularApp.controller:AboutCtrl
 * @description # AboutCtrl Controller of the yoAngularApp
 */
angular.module('talarion2App').controller('ProvinceCreateController',
    function($scope, $state, $stateParams, Province, growl) {

      $scope.submitted = false;

        $scope.province = {};

        $scope.create = function() {

            $scope.submitted = true;

            if ($scope.createForm.$valid) {

                Province.save($scope.province, function() {

                    growl.info("Province successfully added ", {});

                    $state.go('province')
                });

            }

        };

    });
