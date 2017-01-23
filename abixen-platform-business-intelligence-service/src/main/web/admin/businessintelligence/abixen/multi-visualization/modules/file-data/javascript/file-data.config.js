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
        .config(platformFileDataModuleConfig);

    platformFileDataModuleConfig.$inject = [
        '$stateProvider'
    ];

    function platformFileDataModuleConfig($stateProvider) {
        $stateProvider
            .state('application.multiVisualization.modules.fileData', {
                url: '/file-data',
                templateUrl: '/admin/businessintelligence/abixen/multi-visualization/modules/file-data/html/index.html'
            })
            .state('application.multiVisualization.modules.fileData.list', {
                url: '/list',
                templateUrl: '/admin/businessintelligence/abixen/multi-visualization/modules/file-data/html/list.html',
                controller: 'FileDataListController',
                controllerAs: 'fileDataList'
            })
            .state('application.multiVisualization.modules.fileData.add', {
                url: '/add',
                templateUrl: '/admin/businessintelligence/abixen/multi-visualization/modules/file-data/html/edit.html',
                controller: 'FileDataDetailController',
                controllerAs: 'fileDataDetails'
            })
            .state('application.multiVisualization.modules.fileData.edit', {
                url: '/edit/:id',
                templateUrl: '/admin/businessintelligence/abixen/multi-visualization/modules/file-data/html/edit.html',
                controller: 'FileDataDetailController',
                controllerAs: 'fileDataDetails'
            });
    }
})();