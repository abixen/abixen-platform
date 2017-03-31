/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

(function () {

    'use strict';

    angular
        .module('platformLoginApp')
        .controller('PlatformAuthenticateController', PlatformAuthenticateController);

    PlatformAuthenticateController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$log',
        '$stateParams',
        'toaster',
        'cfpLoadingBar',
        '$timeout'
    ];

    function PlatformAuthenticateController($rootScope,
                                            $scope,
                                            $http,
                                            $log,
                                            $stateParams,
                                            toaster,
                                            cfpLoadingBar,
                                            $timeout) {

        $log.log('PlatformAuthenticateController');

        $scope.credentials = {};

        var applicationUrl = '/';
        var applicationAdminUrl = '/control-panel#/users/list';

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

        $scope.start = function () {
            cfpLoadingBar.start();
        };
        $scope.start();
        $scope.fakeIntro = true;
        $timeout(function () {
            $scope.complete();
            $scope.fakeIntro = false;
        }, 1250);
        $scope.complete = function () {
            cfpLoadingBar.complete();
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
})();