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
        .module('platformLayoutModule')
        .config(platformLayoutModuleTranslateConfig);


    platformLayoutModuleTranslateConfig.$inject = ['$translateProvider'];
    function platformLayoutModuleTranslateConfig($translateProvider) {

        $translateProvider.useSanitizeValueStrategy('escape');

        $translateProvider.translations('POLISH', {
            'module.layout.title.label': 'Tytuł',
            'module.layout.title.placeholder': 'Np. 2 (50/50)',
            'module.layout.content.label': 'Xml',
            'module.layout.content.placeholder': 'Definicja układu strony'
        });

        $translateProvider.translations('ENGLISH', {
            'module.layout.title.label': 'Title',
            'module.layout.title.placeholder': 'E.g. 2 (50/50)',
            'module.layout.content.label': 'Xml',
            'module.layout.content.placeholder': 'Layout\'s definition'
        });
    }
})();