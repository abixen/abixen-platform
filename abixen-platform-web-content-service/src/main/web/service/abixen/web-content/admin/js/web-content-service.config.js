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
        .module('webContentService', [
            'webContentServiceStructureModule',
            'webContentServiceTemplateModule',
            'webContentServiceWebContentModule',
            'ui.router',
            'webContentServiceTemplatecache'
        ]);
})();

(function () {

    'use strict';

    angular
        .module('webContentService')
        .config(webContentServiceConfig);

    webContentServiceConfig.$inject = [
        '$stateProvider'
    ];

    function webContentServiceConfig($stateProvider) {

        $stateProvider
            .state('application.webContentService', {
                url: '/web-content',
                templateUrl: 'service/abixen/web-content/admin/html/index.html',
                controller: 'WebContentServiceController',
                controllerAs: 'webContentService'
            });
    }
})();