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
        .module('platformFileDataSourceModule')
        .config(platformFileDataSourceModuleTranslateConfig);


    platformFileDataSourceModuleTranslateConfig.$inject = ['$translateProvider'];
    function platformFileDataSourceModuleTranslateConfig($translateProvider) {

        $translateProvider.useSanitizeValueStrategy('escape');

        $translateProvider.translations('pl', {
            'service.businessintelligence.fileDataSoruce.name.label': 'Nazwa',
            'service.businessintelligence.fileDataSoruce.name.placeholder': 'Np. Sprzedaż w Q3',
            'service.businessintelligence.fileDataSoruce.description.label': 'Opis',
            'service.businessintelligence.fileDataSoruce.description.placeholder': 'Np. Plik zawiera dane z trzeciego kwartału'
        });

        $translateProvider.translations('en', {
            'service.businessintelligence.fileDataSoruce.name.label': 'Name',
            'service.businessintelligence.fileDataSoruce.name.placeholder': 'E.g. Selling Q3 Data',
            'service.businessintelligence.fileDataSoruce.description.label': 'Description',
            'service.businessintelligence.fileDataSoruce.description.placeholder': 'E.g. The file contains data from the third quarter'
        });
    }
})();