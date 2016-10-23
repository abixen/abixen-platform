var platformLoginApp = angular.module('platformLoginApp', ['platformLoginControllers', 'ngRoute', 'ui.router', 'toaster']);

platformLoginApp.config(
    ['$httpProvider', '$stateProvider', '$urlRouterProvider', function ($httpProvider, $stateProvider, $urlRouterProvider) {
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

        $urlRouterProvider.otherwise('/');

        $stateProvider
            .state('application', {
                abstract: true,
                templateUrl: '/login/html/application.html',
                url: '/'
            })
            .state('application.login', {
                url: '?activation-key',
                controller: 'PlatformAuthenticateController',
                templateUrl: '/login/html/login.html'
            });
    }]
);
;var platformLoginControllers = angular.module('platformLoginControllers', []);

platformLoginControllers.controller('PlatformAuthenticateController', ['$rootScope', '$scope', '$http', '$location', '$log', '$stateParams', 'toaster', function ($rootScope, $scope, $http, $location, $log, $stateParams, toaster) {
    $log.log('PlatformAuthenticateController');

    $scope.credentials = {};

    var applicationUrl = 'http://localhost:8080';
    var applicationAdminUrl = 'http://localhost:8080/admin';

    var authenticate = function (credentials, callback) {

        var headers = {
            authorization: 'Basic ' + btoa($scope.credentials.username + ':' + $scope.credentials.password)
        };

        $http.get('/user', {
            headers: headers
        }).success(function (data) {
            if (data.username) {
                $rootScope.authenticated = true;
                $rootScope.admin = data.admin;
            } else {
                $rootScope.authenticated = false;
            }
            callback && callback();
        }).error(function () {
            $rootScope.authenticated = false;
            callback && callback();
        });

    }

    $scope.login = function () {
        authenticate($scope.credentials, function () {
            if ($rootScope.authenticated) {
                if ($rootScope.admin) {
                    window.location = applicationAdminUrl;
                } else {
                    window.location = applicationUrl;
                }
            } else {
                toaster.pop('error', 'Wrong credentials', 'Wrong username and / or password. Please pass correct credentials and try again.');
            }
        });
    };

    if ($stateParams['activation-key']) {
        $http.get('/api/user-activation/activate/' + $stateParams['activation-key'] + '/', {})
            .success(function () {
                toaster.pop('success', 'Activated', 'Your account has been activated successfully.');
            }).error(function (error) {
                toaster.pop('error', 'Not activated', error.message);
            });
    }

}]);



