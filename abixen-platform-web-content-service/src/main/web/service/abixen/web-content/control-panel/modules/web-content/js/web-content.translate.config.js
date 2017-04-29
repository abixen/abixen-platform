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
        .module('webContentServiceWebContentModule')
        .config(webContentServiceWebContentModuleTranslateConfig);


    webContentServiceWebContentModuleTranslateConfig.$inject = ['$translateProvider'];
    function webContentServiceWebContentModuleTranslateConfig($translateProvider) {

        $translateProvider.useSanitizeValueStrategy('escape');

        $translateProvider.translations('POLISH', {
            'webContentService.webContent.title.label': 'Tytuł',
            'webContentService.webContent.title.placeholder': 'Np. ogłoszenie wyników sprzedaży',
            'webContentService.webContent.content.label': 'Treść'
        });

        $translateProvider.translations('ENGLISH', {
            'webContentService.webContent.title.label': 'Title',
            'webContentService.webContent.title.placeholder': 'E.g. announcement of sales results',
            'webContentService.webContent.content.label': 'Content'
        });
    }
})();