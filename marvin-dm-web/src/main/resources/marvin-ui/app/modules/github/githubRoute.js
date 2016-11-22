'use strict';

/**
 * @ngdoc function
 * @name app.route:githubRoute
 * @description
 * # githubRoute
 * Route of the app
 */

angular.module('github')
	.config(['$stateProvider', function ($stateProvider) {
		
		$stateProvider
			.state('home.github', {
				url:'/github',
				templateUrl: 'app/modules/github/github.html',
				controller: 'GithubCtrl',
				controllerAs: 'vm'
			});

		
	}]);
