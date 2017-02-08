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
        .module('multiVisualizationModule')
        .config(multiVisualizationModuleConfig);

    multiVisualizationModuleConfig.$inject = [
        '$stateProvider'
    ];

    function multiVisualizationModuleConfig($stateProvider) {
        $stateProvider
            .state('application.multiVisualization', {
                url: '/multi-visualization',
                templateUrl: '/service/abixen/business-intelligence/admin/multi-visualization/html/index.html',
                controller: 'MultiVisualizationController'
            })
            .state('application.multiVisualization.modules', {
                url: '/businessintelligence',
                templateUrl: '/service/abixen/business-intelligence/admin/multi-visualization/modules/html/index.html'
            });
    }
})();