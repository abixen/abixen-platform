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
        .module('platformRoleModule')
        .config(platformRoleModuleConfig);


    platformRoleModuleConfig.$inject = ['$translateProvider'];
    function platformRoleModuleConfig($translateProvider) {

        $translateProvider.useSanitizeValueStrategy('escape');

        $translateProvider.translations('POLISH', {
            'module.role.name.label': 'Nazwa',
            'module.role.name.placeholder': 'Np. Administrator',
            'module.role.type.label': 'Rodzaj roli',
            'module.role.type.select': 'Wybierz rodzaj roli'
        });

        $translateProvider.translations('ENGLISH', {
            'module.role.name.label': 'Name',
            'module.role.name.placeholder': 'E.g. Administrator',
            'module.role.type.label': 'Role type',
            'module.role.type.select': 'Select role type'
        });
    }
})();