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
        .module('platformDatabaseDataSourceModule')
        .config(platformDatabaseDataSourceModuleTranslateConfig);


    platformDatabaseDataSourceModuleTranslateConfig.$inject = ['$translateProvider'];
    function platformDatabaseDataSourceModuleTranslateConfig($translateProvider) {

        $translateProvider.useSanitizeValueStrategy('escape');

        $translateProvider.translations('POLISH', {
            'service.businessIntelligence.databaseDataSourceDetails.name.label': 'Nazwa',
            'service.businessIntelligence.databaseDataSourceDetails.name.placeholder': 'Np. Sprzedaż w Q3 BD',
            'service.businessIntelligence.databaseDataSourceDetails.description.label': 'Opis',
            'service.businessIntelligence.databaseDataSourceDetails.description.placeholder': 'Np. Baza danych zawiera dane z trzeciego kwartału'
        });

        $translateProvider.translations('ENGLISH', {
            'service.businessIntelligence.databaseDataSourceDetails.name.label': 'Name',
            'service.businessIntelligence.databaseDataSourceDetails.name.placeholder': 'E.g. Selling Q3 Data DB',
            'service.businessIntelligence.databaseDataSourceDetails.description.label': 'Description',
            'service.businessIntelligence.databaseDataSourceDetails.description.placeholder': 'E.g. The database contains data from the third quarter'
        });
    }
})();