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
        .module('platformModuleModule')
        .config(platformModuleModuleTranslateConfig);


    platformModuleModuleTranslateConfig.$inject = ['$translateProvider'];
    function platformModuleModuleTranslateConfig($translateProvider) {

        $translateProvider.useSanitizeValueStrategy('escape');

        $translateProvider.translations('POLISH', {
            'module.module.title.label': 'Tytuł',
            'module.module.title.placeholder': 'Np. Sprzedaż w 2016',
            'module.module.description.label': 'Opis',
            'module.module.description.placeholder': 'Np. Strona prezentuje sprzedaż detaliczną naszego kluczowego produktu w 2016 roku.'
        });

        $translateProvider.translations('ENGLISH', {
            'module.module.title.label': 'Title',
            'module.module.title.placeholder': 'E.g. Sales in 2016',
            'module.module.description.label': 'Description',
            'module.module.description.placeholder': 'E.g. The page presents retail sales of our key products in 2016.'
        });
    }
})();