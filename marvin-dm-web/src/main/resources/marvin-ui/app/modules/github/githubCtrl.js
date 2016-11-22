(function() {
	'use strict';

	/**
	* @ngdoc function
	* @name app.controller:githubCtrl
	* @description
	* # githubCtrl
	* Controller of the app
	*/

  	angular
		.module('github')
		.controller('GithubCtrl', Github);

		Github.$inject = [];

		/*
		* recommend
		* Using function declarations
		* and bindable members up top.
		*/

		function Github() {
			/*jshint validthis: true */
			var vm = this;

		}

})();
