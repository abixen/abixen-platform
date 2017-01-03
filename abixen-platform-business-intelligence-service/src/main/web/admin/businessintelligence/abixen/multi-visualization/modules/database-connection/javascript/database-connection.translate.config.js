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

        $translateProvider.translations('pl', {
            'service.businessintelligence.databaseConnection.name.label': 'Nazwa',
            'service.businessintelligence.databaseConnection.name.placeholder': 'Np. Połączenie do bazy danych z danymi sprzedaży',
            'service.businessintelligence.databaseConnection.description.label': 'Opis',
            'service.businessintelligence.databaseConnection.description.placeholder': 'Np. Połączenie do bazy danych z danymi sprzedaży w trzecim kwartale',
            'service.businessintelligence.databaseConnection.username.label': 'Nazwa użytkownika',
            'service.businessintelligence.databaseConnection.username.placeholder': 'Np. joe.brown.db',
            'service.businessintelligence.databaseConnection.password.label': 'Hasło',
            'service.businessintelligence.databaseConnection.password.placeholder': 'Np. sekretneHasło1',
            'service.businessintelligence.databaseConnection.databaseHost.label': 'Host bazy danych',
            'service.businessintelligence.databaseConnection.databaseHost.placeholder': 'Np. localhost',
            'service.businessintelligence.databaseConnection.databasePort.label': 'Port bazy danych',
            'service.businessintelligence.databaseConnection.databasePort.placeholder': 'Np. 5432',
            'service.businessintelligence.databaseConnection.databaseName.label': 'Nazwa bazy danych',
            'service.businessintelligence.databaseConnection.databaseName.placeholder': 'Np. dane_sprz_1'
        });

        $translateProvider.translations('en', {
            'service.businessintelligence.databaseConnection.name.label': 'Name',
            'service.businessintelligence.databaseConnection.name.placeholder': 'E.g. Connection to database with selling data',
            'service.businessintelligence.databaseConnection.description.label': 'Description',
            'service.businessintelligence.databaseConnection.description.placeholder': 'E.g. Connection to database with selling data from the third quarter',
            'service.businessintelligence.databaseConnection.username.label': 'Username',
            'service.businessintelligence.databaseConnection.username.placeholder': 'E.g. joe.brown.db',
            'service.businessintelligence.databaseConnection.password.label': 'Password',
            'service.businessintelligence.databaseConnection.password.placeholder': 'E.g. secretPassword1',
            'service.businessintelligence.databaseConnection.databaseHost.label': 'Database host',
            'service.businessintelligence.databaseConnection.databaseHost.placeholder': 'E.g. localhost',
            'service.businessintelligence.databaseConnection.databasePort.label': 'Database port',
            'service.businessintelligence.databaseConnection.databasePort.placeholder': 'E.g. 5432',
            'service.businessintelligence.databaseConnection.databaseName.label': 'Database name',
            'service.businessintelligence.databaseConnection.databaseName.placeholder': 'E.g. sell_date_1'
        });
    }
})();