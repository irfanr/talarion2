'use strict';

/**
 * @ngdoc function
 * @name yoAngularApp.controller:AboutCtrl
 * @description # AboutCtrl Controller of the yoAngularApp
 */
angular.module('talarion2App').controller('SubDistrictEditController',
    function($scope, $state, $stateParams, SubDistrict, growl, District, ParseLinks) {

        $scope.submitted = false;

        $scope.subDistrict = {};
        $scope.subDistrict.district = {};

        $scope.load = function(id) {
            SubDistrict.get({
                id: id
            }, function(result) {
                $scope.subDistrict = result;
            });
        };
        $scope.load($stateParams.id);

        $scope.save = function() {

            $scope.submitted = true;

            if ($scope.editForm.$valid) {

                SubDistrict.update($scope.subDistrict, function() {

                    growl.info("SubDistrict successfully edited ", {});

                    $state.go('subDistrict')
                });

            }

        };

        $scope.showDistrictLookup = function() {

            $('#districtLookupDialog').modal('show');
        };

        $scope.selectDistrictLookup = function(district) {

            $scope.subDistrict.district = district;

            $('#districtLookupDialog').modal('hide');
        };

        $scope.callServerDistrict = function callServerDistrict(tableState) {

            $scope.isLoading = true;

            var pagination = tableState.pagination;

            var start = pagination.start || 0; // This is NOT the page number, but the index of item in the list that you want to use to display the table.
            var number = pagination.number || 10; // Number of entries showed per page.
            var numberOfPages = pagination.numberOfPages || 1;
            var page = start / number + 1;

            var nameSearchCrit = '';

            if (tableState.search.predicateObject != undefined) {
                nameSearchCrit = tableState.search.predicateObject.name || '';
            }

            District.query({
                page: page,
                per_page: 10,
                name: nameSearchCrit
            }, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.districtList = result;
                tableState.pagination.numberOfPages = 1 * headers('X-Total-Pages');
                tableState.pagination.number = 1 * headers('X-Size');
                tableState.pagination.start = tableState.pagination.number * (page - 1);
                $scope.isLoading = false;

                // console.log('tableState.pagination.start: ' + tableState.pagination.start);
                // console.log('tableState.pagination.numberOfPages: ' + tableState.pagination.numberOfPages);
                // console.log('tableState.pagination.number: ' + tableState.pagination.number);

                // $state.go('setting-produk');
            });

        };


    });
