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
        .config(platformDatabaseDataSourceModuleConfig);

    platformDatabaseDataSourceModuleConfig.$inject = [
        '$stateProvider'
    ];

    function platformDatabaseDataSourceModuleConfig($stateProvider) {
        $stateProvider
            .state('application.multiVisualization.modules.databaseDataSource', {
                url: '/data-source',
                templateUrl: '/admin/businessintelligence/abixen/multi-visualization/modules/database-data-source/html/index.html'
            })
            .state('application.multiVisualization.modules.databaseDataSource.list', {
                url: '/list',
                templateUrl: '/admin/businessintelligence/abixen/multi-visualization/modules/database-data-source/html/list.html',
                controller: 'DatabaseDataSourceListController',
                controllerAs: 'databaseDataSourceList'
            })
            .state('application.multiVisualization.modules.databaseDataSource.add', {
                url: '/add',
                templateUrl: '/admin/businessintelligence/abixen/multi-visualization/modules/database-data-source/html/edit.html',
                controller: 'DatabaseDataSourceDetailsController',
                controllerAs: 'databaseDataSourceDetails'
            })
            .state('application.multiVisualization.modules.databaseDataSource.edit', {
                url: '/edit/:id',
                templateUrl: '/admin/businessintelligence/abixen/multi-visualization/modules/database-data-source/html/edit.html',
                controller: 'DatabaseDataSourceDetailsController',
                controllerAs: 'databaseDataSourceDetails'
            });
    }
})();