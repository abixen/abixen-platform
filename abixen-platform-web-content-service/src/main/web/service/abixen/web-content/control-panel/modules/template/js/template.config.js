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
        .module('webContentServiceTemplateModule')
        .config(webContentServiceTemplateConfig);

    webContentServiceTemplateConfig.$inject = [
        '$stateProvider'
    ];

    function webContentServiceTemplateConfig($stateProvider) {

        $stateProvider
            .state('application.webContentService.template', {
                url: '/template',
                templateUrl: 'service/abixen/web-content/control-panel/modules/template/html/index.html'
            })
            .state('application.webContentService.template.list', {
                url: '/list',
                templateUrl: 'service/abixen/web-content/control-panel/modules/template/html/list.html',
                controller: 'WebContentServiceTemplateListController',
                controllerAs: 'templateList'
            })
            .state('application.webContentService.template.add', {
                url: '/add',
                templateUrl: 'service/abixen/web-content/control-panel/modules/template/html/details.html',
                controller: 'WebContentServiceTemplateDetailsController',
                controllerAs: 'templateDetails'
            })
            .state('application.webContentService.template.edit', {
                url: '/edit/:id',
                templateUrl: 'service/abixen/web-content/control-panel/modules/template/html/details.html',
                controller: 'WebContentServiceTemplateDetailsController',
                controllerAs: 'templateDetails'
            });
    }
})();