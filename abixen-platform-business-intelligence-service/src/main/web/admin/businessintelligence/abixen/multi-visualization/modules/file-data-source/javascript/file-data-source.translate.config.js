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
            'service.businessintelligence.fileDataSource.name.label': 'Nazwa',
            'service.businessintelligence.fileDataSource.name.placeholder': 'Np. Sprzedaż w Q3 ZD',
            'service.businessintelligence.fileDataSource.description.label': 'Opis',
            'service.businessintelligence.fileDataSource.description.placeholder': 'Np. Żródlo danych zawiera dane z trzeciego kwartału',
            'service.businessintelligence.fileDataSource.save': 'Zapisz',
            'service.businessintelligence.fileDataSource.cancel': 'Anuluj',
            'service.businessintelligence.fileDataSource.column.header.name': 'Nazwa kolumny',
            'service.businessintelligence.fileDataSource.column.header.available': 'Dostępna'
        });

        $translateProvider.translations('ENGLISH', {
            'service.businessintelligence.fileDataSource.name.label': 'Name',
            'service.businessintelligence.fileDataSource.name.placeholder': 'E.g. Selling Q3 Data DS',
            'service.businessintelligence.fileDataSource.description.label': 'Description',
            'service.businessintelligence.fileDataSource.description.placeholder': 'E.g. The data source contains data from the third quarter',
            'service.businessintelligence.fileDataSource.save': 'Save',
            'service.businessintelligence.fileDataSource.cancel': 'Cancel',
            'service.businessintelligence.fileDataSource.column.header.name': 'Column name',
            'service.businessintelligence.fileDataSource.column.header.available': 'Available'
        });
    }
})();