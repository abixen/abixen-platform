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
        .config(platformFileDataModuleSourceTranslateConfig);


    platformFileDataModuleSourceTranslateConfig.$inject = ['$translateProvider'];
    function platformFileDataModuleSourceTranslateConfig($translateProvider) {

        $translateProvider.useSanitizeValueStrategy('escape');

        $translateProvider.translations('POLISH', {
            'service.businessIntelligence.fileDataSource.name.label': 'Nazwa',
            'service.businessIntelligence.fileDataSource.name.placeholder': 'Np. Sprzedaż w Q3 ZD',
            'service.businessIntelligence.fileDataSource.description.label': 'Opis',
            'service.businessIntelligence.fileDataSource.description.placeholder': 'Np. Źródło danych zawiera dane z trzeciego kwartału',
            'service.businessIntelligence.fileDataSource.fileData.label': 'Plik z danymi',
            'service.businessIntelligence.fileDataSource.fileData.select': 'Wybierz plik',
            'service.businessIntelligence.fileDataSource.save': 'Zapisz',
            'service.businessIntelligence.fileDataSource.cancel': 'Anuluj',
            'service.businessIntelligence.fileDataSource.column.header.name': 'Nazwa kolumny',
            'service.businessIntelligence.fileDataSource.column.header.available': 'Dostępna',
            'service.businessIntelligence.fileDataSource.column.header.type': 'Typ kolumny'
        });

        $translateProvider.translations('ENGLISH', {
            'service.businessIntelligence.fileDataSource.name.label': 'Name',
            'service.businessIntelligence.fileDataSource.name.placeholder': 'E.g. Selling Q3 Data DS',
            'service.businessIntelligence.fileDataSource.description.label': 'Description',
            'service.businessIntelligence.fileDataSource.description.placeholder': 'E.g. The data source contains data from the third quarter',
            'service.businessIntelligence.fileDataSource.fileData.label': 'File data',
            'service.businessIntelligence.fileDataSource.fileData.select': 'Select file',
            'service.businessIntelligence.fileDataSource.save': 'Save',
            'service.businessIntelligence.fileDataSource.cancel': 'Cancel',
            'service.businessIntelligence.fileDataSource.column.header.name': 'Column name',
            'service.businessIntelligence.fileDataSource.column.header.available': 'Available',
            'service.businessIntelligence.fileDataSource.column.header.type': 'Column type'
        });
    }
})();