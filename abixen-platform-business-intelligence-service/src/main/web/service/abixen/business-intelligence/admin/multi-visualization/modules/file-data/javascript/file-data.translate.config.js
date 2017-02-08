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
        .module('platformFileDataModule')
        .config(platformFileDataModuleTranslateConfig);


    platformFileDataModuleTranslateConfig.$inject = ['$translateProvider'];
    function platformFileDataModuleTranslateConfig($translateProvider) {

        $translateProvider.useSanitizeValueStrategy('escape');

        $translateProvider.translations('POLISH', {
            'service.businessintelligence.fileData.name.label': 'Nazwa',
            'service.businessintelligence.fileData.name.placeholder': 'Np. Sprzedaż w Q3',
            'service.businessintelligence.fileData.description.label': 'Opis',
            'service.businessintelligence.fileData.description.placeholder': 'Np. Plik zawiera dane z trzeciego kwartału'
        });

        $translateProvider.translations('ENGLISH', {
            'service.businessintelligence.fileData.name.label': 'Name',
            'service.businessintelligence.fileData.name.placeholder': 'E.g. Selling Q3 Data',
            'service.businessintelligence.fileData.description.label': 'Description',
            'service.businessintelligence.fileData.description.placeholder': 'E.g. The file contains data from the third quarter'
        });
    }
})();