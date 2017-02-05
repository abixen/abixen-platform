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
        .config(platformAdminApplicationTranslateConfig);

    platformAdminApplicationTranslateConfig.$inject = ['$translateProvider'];
    function platformAdminApplicationTranslateConfig($translateProvider) {

        $translateProvider.useSanitizeValueStrategy('escape');

        $translateProvider.translations('POLISH', {
            'search': 'Szukaj',
            'users': 'Użytkownicy',
            'roles': 'Role',
            'permissions': 'Uprawnienia',
            'pages': 'Strony',
            'modules': 'Moduły',
            'templates': 'Szablony',
            'data-sources': 'Źródła Danych',
            'my-account': 'Moje Konto',
            'logout': 'Wyloguj'
        });

        $translateProvider.translations('ENGLISH', {
            'search': 'Search',
            'users': 'Users',
            'roles': 'Roles',
            'permissions': 'Permissions',
            'pages': 'Pages',
            'modules': 'Modules',
            'templates': 'Templates',
            'data-sources': 'Data Sources',
            'my-account': 'My Account',
            'logout': 'Logout'
        });
    }
})();