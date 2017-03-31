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
        .config(platformLoginApplicationConfig);

    platformLoginApplicationConfig.$inject = [
        '$httpProvider',
        '$stateProvider',
        '$urlRouterProvider',
        'cfpLoadingBarProvider'
    ];

    function platformLoginApplicationConfig($httpProvider, $stateProvider, $urlRouterProvider, cfpLoadingBarProvider) {

        $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

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

        cfpLoadingBarProvider.includeSpinner = true;
    }
})();