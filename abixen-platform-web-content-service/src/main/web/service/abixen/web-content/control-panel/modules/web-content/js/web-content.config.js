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
        .module('webContentServiceWebContentModule')
        .config(webContentServiceWebContentConfig);

    webContentServiceWebContentConfig.$inject = [
        '$stateProvider'
    ];

    function webContentServiceWebContentConfig($stateProvider) {

        $stateProvider
            .state('application.webContentService.webContent', {
                url: '/web-content',
                templateUrl: 'service/abixen/web-content/control-panel/modules/web-content/html/index.html'
            })
            .state('application.webContentService.webContent.list', {
                url: '/list',
                templateUrl: 'service/abixen/web-content/control-panel/modules/web-content/html/list.html',
                controller: 'WebContentServiceWebContentController',
                controllerAs: 'webContentList'
            })
            .state('application.webContentService.webContent.addSimple', {
                url: '/simple/add',
                templateUrl: 'service/abixen/web-content/control-panel/modules/web-content/html/simple-details.html',
                controller: 'WebContentServiceSimpleWebContentDetailsController',
                controllerAs: 'simpleWebContentDetails'
            })
            .state('application.webContentService.webContent.editSimple', {
                url: '/simple/edit/:id',
                templateUrl: 'service/abixen/web-content/control-panel/modules/web-content/html/simple-details.html',
                controller: 'WebContentServiceSimpleWebContentDetailsController',
                controllerAs: 'simpleWebContentDetails'
            })
            .state('application.webContentService.webContent.addAdvanced', {
                url: '/advanced/add',
                templateUrl: 'service/abixen/web-content/control-panel/modules/web-content/html/advanced-details.html',
                controller: 'WebContentServiceAdvancedWebContentDetailsController',
                controllerAs: 'advancedWebContentDetails'
            })
            .state('application.webContentService.webContent.editAdvanced', {
            url: '/advanced/edit/:id',
            templateUrl: 'service/abixen/web-content/control-panel/modules/web-content/html/advanced-details.html',
            controller: 'WebContentServiceAdvancedWebContentDetailsController',
            controllerAs: 'advancedWebContentDetails'
        });
    }
})();