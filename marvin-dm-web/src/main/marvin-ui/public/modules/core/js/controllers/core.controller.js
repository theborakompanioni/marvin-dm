'use strict';

angular.module('core')
    .controller("CoreCtrl", ['$scope', 'ScrollSmooth', function ($scope, ScrollSmooth) {

        $scope.AppName = 'marvin';
        $scope.ModuleTest = 'marvin dependency management';

        $scope.scrollSmoothToElementId = function (elementId) {
            ScrollSmooth.toElementId(elementId);
        };

        $scope.scrollSmoothToTop = function () {
            ScrollSmooth.toTop();
        };
    }]);