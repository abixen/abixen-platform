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
            .state('application.multiVisualisation.modules.fileData', {
                url: '/file-data',
                templateUrl: 'service/abixen/business-intelligence/control-panel/multi-visualisation/modules/file-data/html/index.html'
            })
            .state('application.multiVisualisation.modules.fileData.list', {
                url: '/list',
                templateUrl: 'service/abixen/business-intelligence/control-panel/multi-visualisation/modules/file-data/html/list.html',
                controller: 'FileDataListController',
                controllerAs: 'fileDataList'
            })
            .state('application.multiVisualisation.modules.fileData.add', {
                url: '/add',
                templateUrl: 'service/abixen/business-intelligence/control-panel/multi-visualisation/modules/file-data/html/edit.html',
                controller: 'FileDataDetailController',
                controllerAs: 'fileDataDetails'
            })
            .state('application.multiVisualisation.modules.fileData.edit', {
                url: '/edit/:id',
                templateUrl: 'service/abixen/business-intelligence/control-panel/multi-visualisation/modules/file-data/html/edit.html',
                controller: 'FileDataDetailController',
                controllerAs: 'fileDataDetails'
            });
    }
})();