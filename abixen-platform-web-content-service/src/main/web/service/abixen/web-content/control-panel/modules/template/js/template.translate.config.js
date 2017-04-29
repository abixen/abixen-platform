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

        $translateProvider.translations('POLISH', {
            'webContentService.template.name.label': 'Nazwa',
            'webContentService.template.name.placeholder': 'Np. aktualności sprzedażowe',
            'webContentService.template.content.label': 'Treść szablonu'
        });

        $translateProvider.translations('ENGLISH', {
            'webContentService.template.name.label': 'Name',
            'webContentService.template.name.placeholder': 'E.g. sales news',
            'webContentService.template.content.label': 'Template content'
        });

        $translateProvider.translations('RUSSIAN', {
            'webContentService.template.name.label': 'Имя',
            'webContentService.template.name.placeholder': 'Напр. новости продаж',
            'webContentService.template.content.label': 'Содежание шаблона'
        });

        $translateProvider.translations('UKRAINIAN', {
            'webContentService.template.name.label': 'Ім\'я',
            'webContentService.template.name.placeholder': 'Напр. новини продажів',
            'webContentService.template.content.label': 'Зміст шаблона'
        });
    }
})();
