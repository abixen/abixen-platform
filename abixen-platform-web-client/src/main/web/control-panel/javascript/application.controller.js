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
        .module('platformAdminApplication')
        .controller('ApplicationController', ApplicationController);

    ApplicationController.$inject = [
        '$scope',
        '$http',
        '$log',
        'applicationNavigationItems',
        'toaster'
    ];

    function ApplicationController($scope, $http, $log, applicationNavigationItems, toaster) {

        $log.log('ApplicationController');

        var applicationLoginUrl = '/login';
        var applicationDashboardUrl = '/';

        $scope.$on('$stateNotFound', function (event, transition) {
            toaster.pop(platformParameters.statusAlertTypes.ERROR, 'Module unavailable', 'Module is unavailable. Please refresh the page and try again.');
        });

        $scope.logout = function () {
            $http.get('/user', {
                headers: {
                    authorization: 'Basic ' + btoa(':')
                }
            }).success(function () {
                window.location = applicationLoginUrl;
            }).error(function (error) {
                $log.error(error);
                window.location = applicationLoginUrl;
            });
        };
        var redirectAction = {
            title: 'Close control panel',
            onClick: function () {
                window.location = applicationDashboardUrl;
            }
        };
        applicationNavigationItems.setRedirectAction(redirectAction);

    }
})();