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
            .state('application.multiVisualisation.modules.fileDataSource', {
                url: '/file-data-source',
                templateUrl: 'service/abixen/business-intelligence/control-panel/multi-visualisation/modules/file-data-source/html/index.html'
            })
            .state('application.multiVisualisation.modules.fileDataSource.list', {
                url: '/list',
                templateUrl: 'service/abixen/business-intelligence/control-panel/multi-visualisation/modules/file-data-source/html/list.html',
                controller: 'FileDataSourceListController',
                controllerAs: 'fileDataSourceList'
            })
            .state('application.multiVisualisation.modules.fileDataSource.add', {
                url: '/add',
                templateUrl: 'service/abixen/business-intelligence/control-panel/multi-visualisation/modules/file-data-source/html/edit.html',
                controller: 'FileDataSourceDetailController',
                controllerAs: 'fileDataSourceDetails'
            })
            .state('application.multiVisualisation.modules.fileDataSource.edit', {
                url: '/edit/:id',
                templateUrl: 'service/abixen/business-intelligence/control-panel/multi-visualisation/modules/file-data-source/html/edit.html',
                controller: 'FileDataSourceDetailController',
                controllerAs: 'fileDataSourceDetails'
            });
    }
})();