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
        .controller('WizardPreviewController', WizardPreviewController);

    WizardPreviewController.$inject = [
        '$scope',
        '$log',
        'dataChartAdapter',
        'CharData',
        'moduleResponseErrorHandler',
        'multivisualisationDisplayingDataRules',
        'MultiVisualisationConfigObject'
    ];

    function WizardPreviewController($scope, $log, dataChartAdapter, CharData, moduleResponseErrorHandler, multivisualisationDisplayingDataRules, MultiVisualisationConfigObject) {

        var wizardPreview = this;
        wizardPreview.options = undefined;
        wizardPreview.data = undefined;
        wizardPreview.setPreviewType = setPreviewType;
        wizardPreview.isTableViewAvailable = multivisualisationDisplayingDataRules.isTableViewAvailable;
        wizardPreview.isChartViewAvailable = multivisualisationDisplayingDataRules.isChartViewAvailable;
        wizardPreview.chartConfiguration = MultiVisualisationConfigObject.getChangedConfig($scope.moduleId);
        wizardPreview.previewType = isChart() ? 'CHART' : 'TABLE';

        $scope.$emit(platformParameters.events.START_REQUEST);
        CharData.query({}, wizardPreview.chartConfiguration)
            .$promise
            .then(onQueryResult, onQueryError);

        function onQueryResult(data) {
            $log.log('CharData.query: ', data);
            var chartParams = dataChartAdapter.convertTo(wizardPreview.chartConfiguration, data);

            if (chartParams != null) {
                wizardPreview.options = chartParams.options;
                wizardPreview.data = chartParams.data;
            }
            $scope.$emit(platformParameters.events.STOP_REQUEST);
        }

        function onQueryError(error) {
            moduleResponseErrorHandler.handle(error, $scope);
        }

        function setPreviewType(type) {
            $log.log('setPreviewType', type);
            wizardPreview.previewType = type;
        }

        function isChart() {
            return !(wizardPreview.chartConfiguration.chartType === 'TABLE')
        }
    }
})();