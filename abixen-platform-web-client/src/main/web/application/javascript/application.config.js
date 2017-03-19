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
        .module('platformApplication')
        .config(platformApplicationConfig)
        .run(run);

    platformApplicationConfig.$inject = [
        '$httpProvider',
        '$stateProvider',
        '$urlRouterProvider',
        'modalWindowProvider',
        '$translateProvider'
    ];

    function platformApplicationConfig($httpProvider,
                                       $stateProvider,
                                       $urlRouterProvider,
                                       modalWindowProvider,
                                       $translateProvider) {

        $httpProvider.interceptors.push('platformHttpInterceptor');

        $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

        $urlRouterProvider.otherwise('/');

        $translateProvider.preferredLanguage('ENGLISH');

        $stateProvider
            .state('application', {
                abstract: true,
                controller: 'PlatformInitController',
                templateUrl: 'application/html/index.html',
                resolve: {
                    platformSecurityResolver: platformSecurityResolver
                }
            });

        modalWindowProvider.setOkButtonClass('btn save-button add-module-btton');
        modalWindowProvider.setCancelButtonClass('btn cancel-button');
        modalWindowProvider.setWarningWindowClass('warning-modal');

        platformSecurityResolver.$inject = ['platformSecurity'];

        function platformSecurityResolver(platformSecurity) {
            platformSecurity.reloadPlatformUser();
        }
    }

    function run(editableOptions, editableThemes) {
        // set `default` theme
        editableOptions.theme = 'bs3';

        // overwrite submit button template
        editableThemes['bs3'].submitTpl = '<button type="submit" class="btn save-button"><i class="fa fa-floppy-o"></i></button>';
        editableThemes['bs3'].cancelTpl = '<button type="button" class="btn cancel-button" ng-click="$form.$cancel()"><i class="fa fa-times"></i></button>';
    }
})();