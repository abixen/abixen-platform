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
        .controller('MultivisualisationModuleController', MultivisualisationModuleController);

    MultivisualisationModuleController.$inject = [
        '$scope',
        '$log',
        'ChartModuleConfiguration',
        'CharData',
        'dataChartAdapter',
        'moduleResponseErrorHandler'
    ];

    function MultivisualisationModuleController($scope, $log, ChartModuleConfiguration, CharData, dataChartAdapter, moduleResponseErrorHandler) {
        $log.log('MultivisualisationModuleController');

        $log.log('$scope.moduleId: ' + $scope.moduleId);

        var multivisualisationModule = this;
        multivisualisationModule.options = undefined;
        multivisualisationModule.data = undefined;

        $scope.$emit(platformParameters.events.START_REQUEST);

        if ($scope.moduleId) {
            ChartModuleConfiguration.get({id: $scope.moduleId})
                .$promise
                .then(onGetResult, onGetError);
        }

        function onGetResult(moduleConfiguration) {
            $log.log('ChartModuleConfiguration has got: ', moduleConfiguration);

            CharData.query({}, moduleConfiguration)
                .$promise
                .then(onQueryResult, onQueryError);

            function onQueryResult(data) {
                $log.log('CharData.query: ', data);
                var chartParams = dataChartAdapter.convertTo(moduleConfiguration, data);

                if (chartParams != null) {
                    multivisualisationModule.options = chartParams.options;
                    multivisualisationModule.data = chartParams.data;
                }
                $scope.$emit(platformParameters.events.STOP_REQUEST);
            }

            function onQueryError(error) {
                moduleResponseErrorHandler.handle(error, $scope);
            }
        }

        function onGetError(error) {
            moduleResponseErrorHandler.handle(error, $scope);
        }
    }
})();