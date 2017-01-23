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
        .config(platformFileDataSourceModuleConfig);

    platformFileDataSourceModuleConfig.$inject = [
        '$stateProvider'
    ];

    function platformFileDataSourceModuleConfig($stateProvider) {
        $stateProvider
            .state('application.multiVisualization.modules.fileDataSource', {
                url: '/file-data-source',
                templateUrl: '/admin/businessintelligence/abixen/multi-visualization/modules/file-data-source/html/index.html'
            })
            .state('application.multiVisualization.modules.fileDataSource.list', {
                url: '/list',
                templateUrl: '/admin/businessintelligence/abixen/multi-visualization/modules/file-data-source/html/list.html',
                controller: 'FileDataSourceListController',
                controllerAs: 'fileDataSourceList'
            })
            .state('application.multiVisualization.modules.fileDataSource.add', {
                url: '/add',
                templateUrl: '/admin/businessintelligence/abixen/multi-visualization/modules/file-data-source/html/edit.html',
                controller: 'FileDataSourceDetailController',
                controllerAs: 'fileDataSourceDetails'
            })
            .state('application.multiVisualization.modules.fileDataSource.edit', {
                url: '/edit/:id',
                templateUrl: '/admin/businessintelligence/abixen/multi-visualization/modules/file-data-source/html/edit.html',
                controller: 'FileDataSourceDetailController',
                controllerAs: 'fileDataSourceDetails'
            });
    }
})();