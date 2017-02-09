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
        .module('platformDatabaseConnectionModule')
        .config(platformDatabaseConnectionModuleTranslateConfig);


    platformDatabaseConnectionModuleTranslateConfig.$inject = ['$translateProvider'];
    function platformDatabaseConnectionModuleTranslateConfig($translateProvider) {

        $translateProvider.useSanitizeValueStrategy('escape');

        $translateProvider.translations('POLISH', {
            'service.businessIntelligence.databaseConnection.name.label': 'Nazwa',
            'service.businessIntelligence.databaseConnection.name.placeholder': 'Np. Połączenie do bazy danych z danymi sprzedaży',
            'service.businessIntelligence.databaseConnection.description.label': 'Opis',
            'service.businessIntelligence.databaseConnection.description.placeholder': 'Np. Połączenie do bazy danych z danymi sprzedaży w trzecim kwartale',
            'service.businessIntelligence.databaseConnection.username.label': 'Nazwa użytkownika',
            'service.businessIntelligence.databaseConnection.username.placeholder': 'Np. joe.brown.db',
            'service.businessIntelligence.databaseConnection.password.label': 'Hasło',
            'service.businessIntelligence.databaseConnection.password.placeholder': 'Np. sekretneHasło1',
            'service.businessIntelligence.databaseConnection.databaseType.label': 'Rodzaj bazy danych',
            'service.businessIntelligence.databaseConnection.databaseType.select': 'Wybierz rodzaj bazy danych',
            'service.businessIntelligence.databaseConnection.databaseHost.label': 'Host bazy danych',
            'service.businessIntelligence.databaseConnection.databaseHost.placeholder': 'Np. localhost',
            'service.businessIntelligence.databaseConnection.databasePort.label': 'Port bazy danych',
            'service.businessIntelligence.databaseConnection.databasePort.placeholder': 'Np. 5432',
            'service.businessIntelligence.databaseConnection.databaseName.label': 'Nazwa bazy danych',
            'service.businessIntelligence.databaseConnection.databaseName.placeholder': 'Np. dane_sprz_1'
        });

        $translateProvider.translations('ENGLISH', {
            'service.businessIntelligence.databaseConnection.name.label': 'Name',
            'service.businessIntelligence.databaseConnection.name.placeholder': 'E.g. Connection to database with selling data',
            'service.businessIntelligence.databaseConnection.description.label': 'Description',
            'service.businessIntelligence.databaseConnection.description.placeholder': 'E.g. Connection to database with selling data from the third quarter',
            'service.businessIntelligence.databaseConnection.username.label': 'Username',
            'service.businessIntelligence.databaseConnection.username.placeholder': 'E.g. joe.brown.db',
            'service.businessIntelligence.databaseConnection.password.label': 'Password',
            'service.businessIntelligence.databaseConnection.password.placeholder': 'E.g. secretPassword1',
            'service.businessIntelligence.databaseConnection.databaseType.label': 'Database type',
            'service.businessIntelligence.databaseConnection.databaseType.select': 'Select database type',
            'service.businessIntelligence.databaseConnection.databaseHost.label': 'Database host',
            'service.businessIntelligence.databaseConnection.databaseHost.placeholder': 'E.g. localhost',
            'service.businessIntelligence.databaseConnection.databasePort.label': 'Database port',
            'service.businessIntelligence.databaseConnection.databasePort.placeholder': 'E.g. 5432',
            'service.businessIntelligence.databaseConnection.databaseName.label': 'Database name',
            'service.businessIntelligence.databaseConnection.databaseName.placeholder': 'E.g. sell_date_1'
        });
    }
})();