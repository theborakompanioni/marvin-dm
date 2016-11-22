(function () {
	'use strict';

	/**
	 * @ngdoc function
	 * @name app.test:githubTest
	 * @description
	 * # githubTest
	 * Test of the app
	 */

	describe('github test', function () {
		var controller = null, $scope = null;

		beforeEach(function () {
			module('marvin-ui');
		});

		beforeEach(inject(function ($controller, $rootScope) {
			$scope = $rootScope.$new();
			controller = $controller('GithubCtrl', {
				$scope: $scope
			});
		}));

		it('Should controller must be defined', function () {
			expect(controller).toBeDefined();
		});

	});
})();
