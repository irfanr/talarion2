'use strict';

angular.module('talarion2App')
    .directive('navbarMinimize', function() {
        return {
            restrict: 'A',
            link: function(scope, element, attrs) {

                element.bind('click', function() {

                    if ($('body').is('.page-sidebar-minimize', '.page-sidebar-minimize-auto')) {
                        // console.log('minimize');
                        $("li[prevent-bloat='']").width(75);
                    } else {
                        // console.log('maximize');
                        $("li[prevent-bloat='']").width(220);
                    }
                });


            }
        };
    });
