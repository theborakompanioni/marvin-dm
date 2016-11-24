'use strict';

angular.module('repository').directive('repositoryDirective', [function () {

  return {
    restrict: 'AE', // A: Attribute, E: Element
    template: '<div><span>Directive in: repository, with name: repositoryDirective</span></div>'
  };

}]);
