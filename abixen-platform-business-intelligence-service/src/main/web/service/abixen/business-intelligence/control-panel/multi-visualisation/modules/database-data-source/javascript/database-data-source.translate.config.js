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
            'service.businessIntelligence.databaseDataSourceDetails.description.placeholder': 'Np. Baza danych zawiera dane z trzeciego kwartału',
            'service.businessIntelligence.databaseDataSourceDetails.databaseConnection.label': 'Połączenie do bazy danych',
            'service.businessIntelligence.databaseDataSourceDetails.databaseConnection.select': 'Wybierz połączenie',
            'service.businessIntelligence.databaseDataSourceDetails.table.label': 'Tabela/widok z wybranego połączenia',
            'service.businessIntelligence.databaseDataSourceDetails.table.select': 'Wybierz tabelę/widok'
        });

        $translateProvider.translations('ENGLISH', {
            'service.businessIntelligence.databaseDataSourceDetails.name.label': 'Name',
            'service.businessIntelligence.databaseDataSourceDetails.name.placeholder': 'E.g. Selling Q3 Data DB',
            'service.businessIntelligence.databaseDataSourceDetails.description.label': 'Description',
            'service.businessIntelligence.databaseDataSourceDetails.description.placeholder': 'E.g. The database contains data from the third quarter',
            'service.businessIntelligence.databaseDataSourceDetails.databaseConnection.label': 'Database connection',
            'service.businessIntelligence.databaseDataSourceDetails.databaseConnection.select': 'Select database connection',
            'service.businessIntelligence.databaseDataSourceDetails.table.label': 'Table/view from selected connection',
            'service.businessIntelligence.databaseDataSourceDetails.table.select': 'Select table/view'
        });
    }
})();