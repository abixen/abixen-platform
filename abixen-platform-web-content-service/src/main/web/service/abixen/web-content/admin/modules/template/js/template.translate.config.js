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
        .module('webContentServiceTemplateModule')
        .config(WebContentServiceTemplateTranslateConfig);

    WebContentServiceTemplateTranslateConfig.$inject = [
        '$translateProvider'
    ];

    function WebContentServiceTemplateTranslateConfig($translateProvider) {
        $translateProvider.useSanitizeValueStrategy('escape');

        $translateProvider.translations('pl', {
            'webContentService.template.name.label': 'Nazwa',
            'webContentService.template.name.placeholder': 'Nazwa',
            'webContentService.template.content.label': 'Treść szablonu',
            'webContentService.template.content.placeholder': 'Treść szablonu'
        });

        $translateProvider.translations('en', {
            'webContentService.template.name.label': 'Name',
            'webContentService.template.name.placeholder': 'Name',
            'webContentService.template.content.label': 'Template content',
            'webContentService.template.content.placeholder': 'Template content'
        });

    }
})();
