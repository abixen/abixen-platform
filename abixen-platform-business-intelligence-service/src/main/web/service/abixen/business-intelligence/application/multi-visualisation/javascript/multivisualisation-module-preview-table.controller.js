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
        .controller('MultivisualisationModulePreviewTableController', MultivisualisationModulePreviewTableController);

    MultivisualisationModulePreviewTableController.$inject = [
        '$scope',
        '$log',
        'CharData',
        'moduleResponseErrorHandler'
    ];

    function MultivisualisationModulePreviewTableController($scope, $log, CharData, moduleResponseErrorHandler) {
        $log.log('MultivisualisationModuleController');

        $log.log('$scope.moduleId: ' + $scope.moduleId);

        var multivisualisationModuleTable = this;
        multivisualisationModuleTable.options = undefined;
        multivisualisationModuleTable.data = undefined;
        if ($scope.tableConfiguration) {
            if ($scope.initWizardStep) {
                $scope.chartConfiguration = $scope.initWizardStep.isChart() ? $scope.chartConfiguration : $scope.tableConfiguration;
            } else {
                $scope.chartConfiguration = $scope.tableConfiguration.chartType === 'TABLE' ? $scope.tableConfiguration : $scope.chartConfiguration;
            }
        }

        if ($scope.chartConfiguration) {
            angular.extend(multivisualisationModuleTable, new MultivisualisationModuleAbstractTableController(multivisualisationModuleTable, $log, CharData, moduleResponseErrorHandler, $scope,
                {
                    chartConfiguration: $scope.chartConfiguration
                }
            ));
        }
    }
})();