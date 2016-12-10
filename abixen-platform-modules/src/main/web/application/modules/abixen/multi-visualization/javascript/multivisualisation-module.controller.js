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
        '$http',
        '$log',
        'ChartModuleConfiguration',
        'CharData',
        'dataChartAdapter'
    ];

    function MultivisualisationModuleController($scope, $http, $log, ChartModuleConfiguration, CharData, dataChartAdapter) {
        $log.log('MultivisualisationModuleController');

        $log.log('$scope.moduleId: ' + $scope.moduleId);

        var multivisualisationModule = this;
        multivisualisationModule.options = undefined;
        multivisualisationModule.data = undefined;

        var moduleConfiguration = {};

        var chartParams = null;

        $scope.$emit(platformParameters.events.START_REQUEST);
        if ($scope.moduleId) {
            ChartModuleConfiguration.get({id: $scope.moduleId}, function (data) {
                moduleConfiguration = data;
                $log.log('ChartModuleConfiguration has got: ', moduleConfiguration);

                CharData.query({}, moduleConfiguration, function (data) {
                    $log.log('CharData.query: ', data);
                    chartParams = dataChartAdapter.convertTo(moduleConfiguration, data);

                    if (chartParams != null) {
                        multivisualisationModule.options = chartParams.options;
                        multivisualisationModule.data = chartParams.data;
                    }
                    $scope.$emit(platformParameters.events.STOP_REQUEST);
                }, function (error) {
                    $scope.$emit(platformParameters.events.STOP_REQUEST);
                    if (error.status == 401) {
                        $scope.$emit(platformParameters.events.MODULE_UNAUTHENTICATED);
                    } else if (error.status == 403) {
                        $scope.$emit(platformParameters.events.MODULE_FORBIDDEN);
                    }
                });
            }, function (error) {
                $scope.$emit(platformParameters.events.STOP_REQUEST);
                if (error.status == 401) {
                    $scope.$emit(platformParameters.events.MODULE_UNAUTHENTICATED);
                } else if (error.status == 403) {
                    $scope.$emit(platformParameters.events.MODULE_FORBIDDEN);
                }
            });
        }

    }
})();