'use strict';

angular.module('repository').config(['$stateProvider', function ($stateProvider) {
    $stateProvider.state("repository",
        {
            parent: "root",
            url: "/repository",
            views: {
                "": {
                    controller: "RepositoryCtrl",
                    templateUrl: "modules/repository/views/index.html"
                },
                "repository.partial@repository": {
                    controller: "RepositoryCtrl",
                    templateUrl: "modules/repository/views/partial.html"
                },
                "repository.projectheader@repository": {
                    controller: "RepositoryCtrl",
                    templateUrl: "modules/repository/views/project-header.html"
                },
                "repository.dependencysummary@repository": {
                    controller: "RepositoryCtrl",
                    templateUrl: "modules/repository/views/dependency-summary.html"
                },
                "repository.dependencies@repository": {
                    controller: "RepositoryCtrl",
                    templateUrl: "modules/repository/views/dependencies.html"
                }
            }
        });
}]);
