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
        .controller('WizardInitController', WizardInitController);

    WizardInitController.$inject = [
        '$scope',
        '$log',
        'MultiVisualisationConfigObject',
        'ChartModuleConfiguration'
    ];

    function WizardInitController($scope, $log, MultiVisualisationConfigObject, ChartModuleConfiguration) {

        var wizardInit = this;

        wizardInit.stepCurrent = -1;
        wizardInit.stepMax = 3;

        wizardInit.chart = {
            name: null,
            hasTable: false,
            axisX: null,
            axisY: null,
            series: null,
            seriesSelected: null
        };

        wizardInit.table = {
            columns: [],
            columnSelected: null
        };

        wizardInit.canNext = canNext;
        wizardInit.next = next;
        wizardInit.prev = prev;

        getConfiguration($scope.moduleId);

        function canNext() {
            var validate = true;
            if (wizardInit.stepCurrent == 0) {
                return validate;
            }
            if (wizardInit.stepCurrent == 1) {
                //validate = dataSourceWizardStepValidate();
                return validate;
            }
            if (wizardInit.stepCurrent === 2) {
                //validate = dataConfigurationWizardStepValidate();
            }
            return validate
        }

        function prev() {
            if (wizardInit.stepCurrent > 0) {
                wizardInit.stepCurrent--;
            }
        }



        function next() {
            if (wizardInit.stepCurrent < wizardInit.stepMax) {
                wizardInit.stepCurrent++;
            } else {
                saveConfiguration();
            }
            $log.log('next step:', wizardInit.stepCurrent);
        }

        function getConfiguration(moduleId) {
            if (moduleId === null) {
                return;
            }

            ChartModuleConfiguration.get({id: moduleId})
                .$promise
                .then(onGetResult);

            function onGetResult(chartConfiguration) {
                if (chartConfiguration.id === null || chartConfiguration.id === undefined) {
                    wizardInit.chartConfiguration = MultiVisualisationConfigObject.getDefaultConfig()
                    wizardInit.chartConfiguration.moduleId = $scope.moduleId
                } else {
                    wizardInit.chartConfiguration = chartConfiguration;
                }
                MultiVisualisationConfigObject.setConfig(wizardInit.chartConfiguration);
                MultiVisualisationConfigObject.setChangedConfig(wizardInit.chartConfiguration);
                wizardInit.stepCurrent = 0;
            }
        }

        function saveConfiguration() {
            var configuration = MultiVisualisationConfigObject.getChangedConfig($scope.moduleId);
            configuration = prepareFilterForDomain(configuration);
            if (configuration.id) {
                ChartModuleConfiguration.update({id: configuration.id}, configuration)
                    .$promise
                    .then(onResult);
            } else {
                ChartModuleConfiguration.save(configuration)
                    .$promise
                    .then(onResult);
            }

            function onResult() {
                $log.log('ChartModuleConfiguration has been updated: ', configuration);
                $scope.$emit('VIEW_MODE');
            }
        }

        function parseObjToJsonCriteriaAsString(domainSeries) {
            return convertToString(buildJsonFromObj(domainSeries))
        }

        function buildJsonFromObj(domainSeries) {
            if (domainSeries.filterObj === null || domainSeries.filterObj === undefined) {
                domainSeries.filterObj = {};
            }
            var filter = {
                group: {
                    operator: 'AND',
                    rules: []
                }
            };
            if (domainSeries.filterObj.conditionOne && domainSeries.filterObj.conditionOne.value !== null){
                filter.group.rules.push({
                    condition: domainSeries.filterObj.conditionOne.operator,
                    field: domainSeries.dataSourceColumn.name,
                    data: getValueAsText(domainSeries.filterObj.conditionOne.value)
                })
            }
            if (domainSeries.filterObj.conditionTwo && domainSeries.filterObj.conditionTwo.value !== null){
                filter.group.rules.push({
                    condition: domainSeries.filterObj.conditionTwo.operator,
                    field: domainSeries.dataSourceColumn.name,
                    data: getValueAsText(domainSeries.filterObj.conditionTwo.value)
                })
            }
            return filter;
        }

        function getValueAsText(value) {
            if (isDate(value)){
                return new Date(new Date(value) - (new Date()).getTimezoneOffset() * 60000).toISOString().slice(0,10);
            }
            return value;
        }

        function prepareFilterForDomain(configuration) {
            $log.debug("configuration", configuration);
            configuration.filter = parseObjToJsonCriteriaAsString(configuration.dataSetChart.domainXSeriesColumn);
            return configuration
        }

        function convertToString(jsonObj) {
            return JSON.stringify(jsonObj);
        }
    }
})();