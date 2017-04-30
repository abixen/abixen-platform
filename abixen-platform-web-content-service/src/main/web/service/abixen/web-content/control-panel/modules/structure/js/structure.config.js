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
        .module('webContentServiceStructureModule')
        .config(webContentServiceStructureConfig);

    webContentServiceStructureConfig.$inject = [
        '$stateProvider'
    ];

    function webContentServiceStructureConfig($stateProvider) {

        $stateProvider
            .state('application.webContentService.structure', {
                url: '/structure',
                templateUrl: 'service/abixen/web-content/control-panel/modules/structure/html/index.html'
            })
            .state('application.webContentService.structure.list', {
                url: '/list',
                templateUrl: 'service/abixen/web-content/control-panel/modules/structure/html/list.html',
                controller: 'WebContentServiceStructureListController',
                controllerAs: 'structureList'
            })
            .state('application.webContentService.structure.add', {
                url: '/add',
                templateUrl: 'service/abixen/web-content/control-panel/modules/structure/html/edit.html',
                controller: 'WebContentServiceStructureDetailsController',
                controllerAs: 'structureDetails'
            })
            .state('application.webContentService.structure.edit', {
                url: '/edit/:id',
                templateUrl: 'service/abixen/web-content/control-panel/modules/structure/html/edit.html',
                controller: 'WebContentServiceStructureDetailsController',
                controllerAs: 'structureDetails'
            });
    }
})();