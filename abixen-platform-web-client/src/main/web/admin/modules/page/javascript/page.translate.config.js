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
        .module('platformPageModule')
        .config(platformPageModuleConfig);


    platformPageModuleConfig.$inject = ['$translateProvider'];
    function platformPageModuleConfig($translateProvider) {

        $translateProvider.useSanitizeValueStrategy('escape');

        $translateProvider.translations('POLISH', {
            'module.page.name.label': 'Nazwa wyświetlana',
            'module.page.title.label': 'Np. Janek',
            'module.page.description.label': 'Desc',
            'module.page.title.placeholder':'Title',
            'module.page.description.placeholder':'Description'
        });

        $translateProvider.translations('ENGLISH', {
            'module.page.name.label': 'Name',
            'module.page.title.label': 'Title',
            'module.page.description.label': 'Description',
            'module.page.title.placeholder':'Title',
            'module.page.description.placeholder':'Description'
        });
    }
})();