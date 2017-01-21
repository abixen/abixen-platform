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
        .module('platformModuleTypeModule')
        .config(platformModuleTypeModuleConfig);


    platformModuleTypeModuleConfig.$inject = ['$translateProvider'];
    function platformModuleTypeModuleConfig($translateProvider) {

        $translateProvider.useSanitizeValueStrategy('escape');

        $translateProvider.translations('POLISH', {
            'module.module-type.name.label': 'Name',
            'module.module-type.title.label': 'Title',
            'module.module-type.serviceId.label': 'Service Id'
        });

        $translateProvider.translations('ENGLISH', {
            'module.module-type.name.label': 'Name',
            'module.module-type.title.label': 'Title',
            'module.module-type.serviceId.label': 'Service Id'
        });
    }
})();