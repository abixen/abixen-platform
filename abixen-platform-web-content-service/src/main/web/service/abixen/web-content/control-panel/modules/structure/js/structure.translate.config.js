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
            'structureDetails.name.label': 'Nazwa',
            'structureDetails.name.placeholder': 'Np. Nowości sprzedażowe',
            'structureDetails.content.label': 'Zawartość',
            'structureDetails.template.label': 'Szablon',
            'structureDetails.template.select': 'Wybierz szablon'
        });

        $translateProvider.translations('ENGLISH', {
            'structureDetails.name.label': 'Name',
            'structureDetails.name.placeholder': 'E.g. Sales news',
            'structureDetails.content.label': 'Content',
            'structureDetails.template.label': 'Template',
            'structureDetails.template.select': 'Select Template'
        });

        $translateProvider.translations('RUSSIAN', {
            'structureDetails.name.label': 'Имя',
            'structureDetails.name.placeholder': 'Напр. Новости продаж',
            'structureDetails.content.label': 'Содержание',
            'structureDetails.template.label': 'Шаблон',
            'structureDetails.template.select': 'Выберите шаблон'
        });

        $translateProvider.translations('UKRAINIAN', {
            'structureDetails.name.label': 'Ім\'я',
            'structureDetails.name.placeholder': 'Напр. Новини продажів',
            'structureDetails.content.label': 'Зміст',
            'structureDetails.template.label': 'Шаблон',
            'structureDetails.template.select': 'Виберіть шаблон'
        });
    }
})();