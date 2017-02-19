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
        .config(platformPageModuleTranslateConfig);


    platformPageModuleTranslateConfig.$inject = ['$translateProvider'];
    function platformPageModuleTranslateConfig($translateProvider) {

        $translateProvider.useSanitizeValueStrategy('escape');

        $translateProvider.translations('POLISH', {
            'module.page.modalWindow.title.add': 'Tworzenie nowej strony',
            'module.page.modalWindow.title.edit': 'Edycja strony',
            'module.page.title.label': 'Tytuł',
            'module.page.title.placeholder': 'Np. Sprzedaż w sklepach',
            'module.page.description.label': 'Opis',
            'module.page.description.placeholder': 'Np. Strona przedstawia wykresy prezentujące sprzedaż w sklepach.'
        });

        $translateProvider.translations('ENGLISH', {
            'module.page.modalWindow.title.add': 'Create a new page',
            'module.page.modalWindow.title.edit': 'Edit page',
            'module.page.title.label': 'Title',
            'module.page.title.placeholder': 'E.g. Sales in stores',
            'module.page.description.label': 'Description',
            'module.page.description.placeholder': 'E.g. The page contains charts presenting a sales in the stores.'
        });
    }
})();