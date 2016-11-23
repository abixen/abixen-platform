var platformLoginControllers = angular.module('platformLoginControllers', []);

platformLoginControllers.controller('PlatformAuthenticateController', [
        '$rootScope', '$scope', '$http', '$location', '$log', '$stateParams', 'toaster',
        function ($rootScope, $scope, $http, $location, $log, $stateParams, toaster) {
            $log.log('PlatformAuthenticateController');

            $scope.credentials = {};

            var applicationUrl = '/';
            var applicationAdminUrl = '/admin';

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

            };

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
        }
    ]
);