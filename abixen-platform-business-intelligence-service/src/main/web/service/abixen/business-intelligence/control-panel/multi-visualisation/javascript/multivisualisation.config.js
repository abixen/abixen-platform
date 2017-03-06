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
        .module('multiVisualisationModule')
        .config(multiVisualisationModuleConfig);

    multiVisualisationModuleConfig.$inject = [
        '$stateProvider'
    ];

    function multiVisualisationModuleConfig($stateProvider) {
        $stateProvider
            .state('application.multiVisualisation', {
                url: '/business-intelligence',
                templateUrl: 'service/abixen/business-intelligence/control-panel/multi-visualisation/html/index.html',
                controller: 'MultiVisualisationController'
            })
            .state('application.multiVisualisation.modules', {
                url: '/multi-visualisation',
                templateUrl: 'service/abixen/business-intelligence/control-panel/multi-visualisation/modules/html/index.html'
            });
    }
})();