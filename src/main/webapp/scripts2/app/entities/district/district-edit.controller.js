'use strict';

/**
 * @ngdoc function
 * @name yoAngularApp.controller:AboutCtrl
 * @description # AboutCtrl Controller of the yoAngularApp
 */
angular.module('talarion2App').controller('DistrictEditController',
    function($scope, $state, $stateParams, District, growl, Province, ParseLinks) {

        $scope.submitted = false;

        $scope.district = {};
        $scope.district.province = {};

        $scope.load = function(id) {
            District.get({
                id: id
            }, function(result) {
                $scope.district = result;
            });
        };
        $scope.load($stateParams.id);

        $scope.save = function() {

            $scope.submitted = true;

            if ($scope.editForm.$valid) {

                District.update($scope.district, function() {

                    growl.info("District successfully edited ", {});

                    $state.go('district')
                });

            }

        };

        $scope.showProvinceLookup = function() {

            $('#provinceLookupDialog').modal('show');
        };

        $scope.selectProvinceLookup = function(province) {

            $scope.district.province = province;

            $('#provinceLookupDialog').modal('hide');
        };

        $scope.callServerProvince = function callServerProvince(tableState) {

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

            Province.query({
                page: page,
                per_page: 10,
                name: nameSearchCrit
            }, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.provinceList = result;
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
