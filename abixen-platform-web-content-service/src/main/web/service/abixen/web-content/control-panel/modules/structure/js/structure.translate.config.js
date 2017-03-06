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
        .module('webContentServiceStructureModule')
        .config(webContentServiceStructureModuleTranslateConfig);


    webContentServiceStructureModuleTranslateConfig.$inject = ['$translateProvider'];
    function webContentServiceStructureModuleTranslateConfig($translateProvider) {

        $translateProvider.useSanitizeValueStrategy('escape');

        $translateProvider.translations('POLISH', {
            'structureDetails.title.label': 'Title',
            'structureDetails.name.placeholder': 'Title',
            'structureDetails.content.label': 'Content',
            'structureDetails.content.placeholder': 'Content',
            'structureDetails.template.label': 'Template',
            'structureDetails.template.placeholder': 'Template',
            'structureDetails.template.select': 'Select Template'
        });

        $translateProvider.translations('ENGLISH', {
            'structureDetails.title.label': 'Title',
            'structureDetails.title.placeholder': 'Title',
            'structureDetails.content.label': 'Content',
            'structureDetails.content.placeholder': 'Content',
            'structureDetails.template.label': 'Template',
            'structureDetails.template.placeholder': 'Template',
            'structureDetails.template.select': 'Select Template'
        });
    }
})();