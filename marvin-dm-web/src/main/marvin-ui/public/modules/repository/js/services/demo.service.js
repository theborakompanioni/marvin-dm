'use strict';

angular.module('repository').factory('RepositoryService', [function () {

    return {
        getDummyText: function(){
            return 'dummyText';
        }
    };

}]);
