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
        .module('platformChartModule')
        .controller('MultivisualisationModuleInitController', MultivisualisationModuleInitController);

    MultivisualisationModuleInitController.$inject = [
        '$scope',
        '$log',
        'ChartModuleInit',
        'moduleResponseErrorHandler'
    ];

    function MultivisualisationModuleInitController($scope, $log, ChartModuleInit, moduleResponseErrorHandler) {
        $log.log('MultivisualisationModuleInitController');

        var multivisualisationModuleInit = this;
        multivisualisationModuleInit.subview = null;

        var SUBVIEW_CONFIGURATION = 'configuration';
        var SUBVIEW_CHART = 'chart';

        $scope.moduleId = null;

        $scope.$on(platformParameters.events.RELOAD_MODULE, function (event, id, viewMode) {
            $log.log('RELOAD MODULE EVENT', event, id, viewMode);

            $scope.moduleId = id;

            $scope.$emit(platformParameters.events.START_REQUEST);
            ChartModuleInit.get({id: id})
                .$promise
                .then(onGetResult, onGetError);

            function onGetResult(data) {
                $log.log('ChartModuleInit has been got: ', data);
                if (viewMode === 'view') {
                    multivisualisationModuleInit.subview = SUBVIEW_CHART;
                } else if (viewMode === 'edit') {
                    multivisualisationModuleInit.subview = SUBVIEW_CONFIGURATION;
                } else {
                    multivisualisationModuleInit.subview = SUBVIEW_CHART;
                }

                $scope.$emit(platformParameters.events.STOP_REQUEST);
            }

            function onGetError(error) {
                moduleResponseErrorHandler.handle(error, $scope);
            }
        });

        $scope.$on('CONFIGURATION_MODE', function (event, id) {
            $log.log('CONFIGURATION_MODE EVENT', event, id);
            multivisualisationModuleInit.subview = SUBVIEW_CONFIGURATION;
            $scope.$emit(platformParameters.events.CONFIGURATION_MODE_READY);
        });

        $scope.$on('VIEW_MODE', function (event, id) {
            $log.log('VIEW_MODE EVENT', event, id);
            multivisualisationModuleInit.subview = SUBVIEW_CHART;
            $scope.$emit(platformParameters.events.VIEW_MODE_READY);
        });

        $scope.$emit(platformParameters.events.MODULE_READY);
    }
})();