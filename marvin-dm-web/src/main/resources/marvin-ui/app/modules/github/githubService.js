(function() {
	'use strict';

	/**
	 * @ngdoc function
	 * @name app.service:githubService
	 * @description
	 * # githubService
	 * Service of the app
	 */

  	angular
		.module('github')
		.factory('GithubService', Github);
		// Inject your dependencies as .$inject = ['$http', 'someSevide'];
		// function Name ($http, someSevide) {...}

		Github.$inject = ['$http'];

		function Github ($http) {

		}

})();
