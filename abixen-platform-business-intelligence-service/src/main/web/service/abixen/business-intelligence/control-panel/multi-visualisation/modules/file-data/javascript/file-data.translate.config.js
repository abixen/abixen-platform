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
            'service.businessIntelligence.fileData.name.label': 'Nazwa',
            'service.businessIntelligence.fileData.name.placeholder': 'Np. Sprzedaż w Q3',
            'service.businessIntelligence.fileData.description.label': 'Opis',
            'service.businessIntelligence.fileData.description.placeholder': 'Np. Plik zawiera dane z trzeciego kwartału',
            'service.businessIntelligence.fileData.readFirstColumnAsHeader.label': 'Przeczytaj nazwy kolumn z pierwszego wiersza (Każda zmiana wymaga przeładowania pliku)'
        });

        $translateProvider.translations('ENGLISH', {
            'service.businessIntelligence.fileData.name.label': 'Name',
            'service.businessIntelligence.fileData.name.placeholder': 'E.g. Selling Q3 Data',
            'service.businessIntelligence.fileData.description.label': 'Description',
            'service.businessIntelligence.fileData.description.placeholder': 'E.g. The file contains data from the third quarter',
            'service.businessIntelligence.fileData.readFirstColumnAsHeader.label': 'Read column names from the first row of the file (After each change, reload the file to see the result).'
        });
    }
})();