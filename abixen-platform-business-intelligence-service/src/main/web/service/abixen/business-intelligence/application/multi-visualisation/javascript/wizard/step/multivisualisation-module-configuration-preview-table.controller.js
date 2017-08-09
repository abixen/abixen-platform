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
        .module('platformChartWizardModule')
        .controller('WizardPreviewTableController', WizardPreviewTableController);

    WizardPreviewTableController.$inject = [
        '$scope',
        '$log',
        'CharData',
        'moduleResponseErrorHandler',
        'MultiVisualisationConfigObject'
    ];

    function WizardPreviewTableController($scope, $log, CharData, moduleResponseErrorHandler, MultiVisualisationConfigObject) {


        var wizardPreviewTable = this;

        wizardPreviewTable.options = undefined;
        wizardPreviewTable.data = undefined;
        wizardPreviewTable.chartConfiguration = MultiVisualisationConfigObject.getChangedConfig($scope.moduleId);
/*        if ($scope.tableConfiguration) {
            if ($scope.initWizardStep) {
                wizardPreviewTable.chartConfiguration = $scope.initWizardStep.isChart() ? $scope.chartConfiguration : $scope.tableConfiguration;
            } else {
                wizardPreviewTable.chartConfiguration = $scope.tableConfiguration.chartType === 'TABLE' ? $scope.tableConfiguration : $scope.chartConfiguration;
            }
        }*/

        if (wizardPreviewTable.chartConfiguration) {
            angular.extend(wizardPreviewTable, new MultivisualisationModuleAbstractTableController(wizardPreviewTable, $log, CharData, moduleResponseErrorHandler, $scope,
                {
                    chartConfiguration: wizardPreviewTable.chartConfiguration
                }
            ));
        }
    }
})();