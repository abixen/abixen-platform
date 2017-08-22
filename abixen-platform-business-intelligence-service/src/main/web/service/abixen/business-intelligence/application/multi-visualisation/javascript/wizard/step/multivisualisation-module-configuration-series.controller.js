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
        .controller('WizardSeriesController', WizardSeriesController);

    WizardSeriesController.$inject = [
        '$scope',
        '$log',
        'MultiVisualisationConfigObject',
        'CharData'
    ];

    function WizardSeriesController($scope, $log, MultiVisualisationConfigObject, CharData) {

        var wizardSeries = this;
        var seriesNumber = 1; //todo remove in the future
        wizardSeries.chartConfiguration = MultiVisualisationConfigObject.getChangedConfig($scope.moduleId);
        wizardSeries.isChart = $scope.isChart = chartTypeWizardStepIsChart;

        wizardSeries.validators = getValidators();

        wizardSeries.conditionsForFilter = conditionsForFilter();

        wizardSeries.initDataSetSeries = initDataSetSeries;
        wizardSeries.addDataSetSeries = addDataSetSeries;
        wizardSeries.removeDataSetSeries = removeDataSetSeries;
        wizardSeries.setDataSetSeriesSelected = setDataSetSeriesSelected;
        wizardSeries.reloadPreviewData = reloadPreviewData;
        wizardSeries.resetFilters = resetFilters;
        wizardSeries.setColumnSelected = setColumnSelected;

        wizardSeries.chart = {
            name: null,
            hasTable: false,
            axisX: null,
            axisY: null,
            series: null,
            seriesSelected: null
        };

        wizardSeries.table = {
            columns: [],
            columnSelected: null
        };

        moduleConfigurationWizardStepSelected();

        function prepareFilterForDomain(configuration) {
            $log.debug("configuration", configuration);
            configuration.filter = parseObjToJsonCriteriaAsString(configuration.dataSetChart.domainXSeriesColumn);
            return configuration
        }
        function parseObjToJsonCriteriaAsString(domainSeries) {
            return convertToString(buildJsonFromObj(domainSeries))
        }

        function convertToString(jsonObj) {
            return JSON.stringify(jsonObj);
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

        function initDataSetSeries() {
            if (!wizardSeries.chartConfiguration.dataSetChart){
                wizardSeries.chartConfiguration.dataSetChart = {};
            }
            if (!wizardSeries.chartConfiguration.dataSetChart.dataSetSeries){
                wizardSeries.chartConfiguration.dataSetChart.dataSetSeries = [];
            }
            if (wizardSeries.chartConfiguration.dataSetChart.dataSetSeries.length === 0) {
                addDataSetSeries();
            } else {
                if (wizardSeries.dataSetSeriesSelected === undefined || wizardSeries.dataSetSeriesSelected === null) {
                    setDataSetSeriesSelected(wizardSeries.chartConfiguration.dataSetChart.dataSetSeries[0]);
                }
            }
        }

        function addDataSetSeries() {
            $log.log('moduleConfigurationWizardStep series', wizardSeries.chartConfiguration.dataSetChart.dataSetSeries);

            if (wizardSeries.chartConfiguration.dataSetChart.dataSetSeries === undefined) {
                wizardSeries.chartConfiguration.dataSetChart.dataSetSeries = [];
            }

            if (wizardSeries.chartConfiguration.dataSetChart.dataSetSeries.length == 0) {
                seriesNumber = 1;
            }

            wizardSeries.chartConfiguration.dataSetChart.dataSetSeries.push({
                id: null,
                name: ('Series ' + seriesNumber),
                isValid: true,
                valueSeriesColumn: {
                    id: null,
                    name: '',
                    type: 'Y',
                    dataSourceColumn: null
                }

            });
            seriesNumber++;

            if (wizardSeries.dataSetSeriesSelected == null) {
                setDataSetSeriesSelected(wizardSeries.chartConfiguration.dataSetChart.dataSetSeries[0]);
            }
        }

        function removeDataSetSeries(dataSetSeries) {
            $log.log('moduleConfigurationWizardStep removeSeries ', dataSetSeries);

            var index = wizardSeries.chartConfiguration.dataSetChart.dataSetSeries.indexOf(dataSetSeries);

            if (index + 1 < wizardSeries.chartConfiguration.dataSetChart.dataSetSeries.length) {
                var nextDataSetSeries = wizardSeries.chartConfiguration.dataSetChart.dataSetSeries[index + 1];
                setDataSetSeriesSelected(nextDataSetSeries);
            } else if (index > 0) {
                var prevDataSetSeries = wizardSeries.chartConfiguration.dataSetChart.dataSetSeries[index - 1];
                setDataSetSeriesSelected(prevDataSetSeries);
            } else {
                setDataSetSeriesSelected(null);
                wizardSeries.chartConfiguration.dataSetChart.dataSetSeries = [];
            }

            if (wizardSeries.chartConfiguration.dataSetChart.dataSetSeries !== []) {
                wizardSeries.chartConfiguration.dataSetChart.dataSetSeries.splice(index, 1);
            }
        }

        function setDataSetSeriesSelected(dataSetSeries) {
            $log.log('moduleConfigurationWizardStep setSelected ', dataSetSeries);

            wizardSeries.dataSetSeriesSelected = dataSetSeries; //wizardSeries.chart.series[idx - 1];


        }

        function reloadPreviewData() {
            getSeriesData();
        }

        function resetFilters() {
            var domainSeries = wizardSeries.chartConfiguration.dataSetChart.domainXSeriesColumn;
            if (!domainSeries.filterObj) {
                domainSeries.filterObj = {};
            }
            if (!domainSeries.filterObj.conditionOne) {
                domainSeries.filterObj.conditionOne = {};
            }
            if (!domainSeries.filterObj.conditionTwo) {
                domainSeries.filterObj.conditionTwo = {};
            }
            domainSeries.filterObj.conditionOne.operator = null;
            domainSeries.filterObj.conditionOne.value = null;
            domainSeries.filterObj.conditionTwo = {};
            domainSeries.filterObj.conditionTwo.operator = null;
            domainSeries.filterObj.conditionTwo.value = null;
        }

        function buildObjFromJson(domainSeries, json) {
            var jsonObj = JSON.parse(json);

            if (jsonObj && jsonObj.group !== undefined) {
                domainSeries.filterObj = {};
                domainSeries.filterObj.conditionOne = {};
                domainSeries.filterObj.conditionOne.operator = jsonObj.group.rules[0].condition;
                domainSeries.filterObj.conditionOne.value = jsonObj.group.rules[0].data;
                if (jsonObj.group.rules[1] && jsonObj.group.rules[1].data){
                    domainSeries.filterObj.conditionTwo = {};
                    domainSeries.filterObj.conditionTwo.operator = jsonObj.group.rules[1].condition;
                    domainSeries.filterObj.conditionTwo.value = jsonObj.group.rules[1].data;
                }
            }
        }

        function getIndexInTableForColumnIdx(columns, idx) {
            return columns.findIndex(function (element) {
                return element.idx == idx;
            })
        }

        function setColumnSelected(idx) {
            $log.log('moduleConfigurationWizardStep setSelected ', idx);
            idx = getIndexInTableForColumnIdx(wizardSeries.table.columns, idx);
            wizardSeries.table.columnSelected = wizardSeries.table.columns[idx];
            wizardSeries.table.columns[idx].isActive = !wizardSeries.table.columns[idx].isActive;
            if (wizardSeries.table.columns[idx].isActive === true) {
                getColumnData(idx);
            } else {
                wizardSeries.table.columnPreviewData = [];
                buildTableConfiguration();
            }
        }

        function refreshColumn() {
            $log.debug('wizardSeries.chartConfiguration.dataSource', wizardSeries.chartConfiguration.dataSource);
            wizardSeries.table.columns = [];

            function compare(a, b) {
                if (a.id < b.id)
                    return -1;
                if (a.id > b.id)
                    return 1;
                return 0;
            }

            wizardSeries.chartConfiguration.dataSource.columns.sort(compare).forEach(function (column) {
                var isActive = false;
                if (wizardSeries.chartConfiguration.dataSetChart && wizardSeries.chartConfiguration.dataSetChart.domainXSeriesColumn.dataSourceColumn !== null) {
                    isActive = wizardSeries.chartConfiguration.dataSetChart.domainXSeriesColumn.dataSourceColumn.name === column.name;
                }
                if (isActive === false && wizardSeries.chartConfiguration.dataSetChart && wizardSeries.chartConfiguration.dataSetChart.dataSetSeries !== null) {
                    wizardSeries.chartConfiguration.dataSetChart.dataSetSeries.forEach(function (series) {
                        if (isActive === false && series.valueSeriesColumn.name === column.name) {
                            isActive = true;
                        }
                    })
                }

                wizardSeries.table.columns.push({
                    idx: column.id,
                    name: column.name,
                    isValid: true,
                    isActive: isActive,
                    dataSourceColumn: column
                });
            })
        }

        function moduleConfigurationWizardStepSelected() {
            $log.log('moduleConfigurationWizardStep selected');
            wizardSeries.chartConfiguration.moduleId = $scope.moduleId;
            if (!chartTypeWizardStepIsChart()) {
                refreshColumn();
            }
        }

        function getColumnData(idx) {
            wizardSeries.table.columnPreviewData = [];
            if (wizardSeries.table.columnSelected.name != undefined && wizardSeries.table.columnSelected.name !== '') {
                CharData.query({seriesName: wizardSeries.table.columnSelected.name}, buildTableConfiguration())
                    .$promise
                    .then(onQueryResult)
            }

            function onQueryResult(data) {
                $log.log('CharData.query: ', data);
                data.forEach(function (el) {
                    wizardSeries.table.columnPreviewData.push({
                        value: el[wizardSeries.table.columnSelected.name].value
                    });
                })
            }
        }

        function getSeriesData() {
            wizardSeries.chart.seriesPreviewData = [];
            if (wizardSeries.dataSetSeriesSelected) {
                if (wizardSeries.dataSetSeriesSelected.valueSeriesColumn.dataSourceColumn !== null && wizardSeries.dataSetSeriesSelected.valueSeriesColumn.dataSourceColumn.name !== undefined && wizardSeries.dataSetSeriesSelected.valueSeriesColumn.dataSourceColumn.name !== '') {
                    wizardSeries.chartConfiguration = prepareFilterForDomain(wizardSeries.chartConfiguration);
                    CharData.query({seriesName: wizardSeries.dataSetSeriesSelected.name}, wizardSeries.chartConfiguration)
                        .$promise
                        .then(onQueryResult);
                }
            }

            function onQueryResult(data) {
                $log.log('CharData.query: ', data);
                data.forEach(function (el) {
                    wizardSeries.chart.seriesPreviewData.push({
                        x: el[wizardSeries.chartConfiguration.dataSetChart.domainXSeriesColumn.dataSourceColumn.name].value,
                        y: el[wizardSeries.dataSetSeriesSelected.valueSeriesColumn.dataSourceColumn.name].value
                    });
                })
            }
        }


        function dataConfigurationWizardStepValidate() {
            if (wizardSeries.entityForm) {
                if (wizardSeries.chartConfiguration.chartType === 'TABLE'){
                    var isActive = false;
                    wizardSeries.table.columns.forEach(function (column) {
                        if (column.isActive === true) {
                            isActive = true;
                        }
                    });
                    return isActive;
                }
                else {
                    return wizardSeries.entityForm.$valid;
                }
            }
            return false;
        }

        function buildTableConfiguration() {
            var tableConfiguration = {
                axisXName: '',
                axisYName: '',
                chartType: 'TABLE',
                dataSource: wizardSeries.chartConfiguration.dataSource,
                moduleId: wizardSeries.chartConfiguration.moduleId,
                id: wizardSeries.chartConfiguration.id,
                dataSetChart: {
                    dataSetSeries: [],
                    domainXSeriesColumn: null,
                    domainZSeriesColumn: null
                }
            };

            var i = 0;
            wizardSeries.table.columns.forEach(function (column) {
                $log.debug('column: ', column);
                if (column.isActive === true) {
                    if (i === 0) {
                        tableConfiguration.dataSetChart.domainXSeriesColumn = {
                            id: null,
                            name: '',
                            type: 'X',
                            dataSourceColumn: column.dataSourceColumn
                        };
                        i++
                    } else {
                        tableConfiguration.dataSetChart.dataSetSeries.push({
                            id: null,
                            name: column.dataSourceColumn.name,
                            isValid: true,
                            valueSeriesColumn: {
                                id: null,
                                name: column.dataSourceColumn.name,
                                type: 'Y',
                                dataSourceColumn: column.dataSourceColumn
                            }
                        });
                        i++;
                    }
                }
            });
            wizardSeries.chartConfiguration = tableConfiguration;
            return tableConfiguration
        }

        function conditionsForFilter() {
            return [
                {name: ''},
                {name: '='},
                {name: '<>'},
                {name: '<'},
                {name: '<='},
                {name: '>'},
                {name: '>='}
            ];
        }

        function getValidators() {
            var validators = [];

            validators['axisXName'] =
                [
                    new NotNull(),
                    new Length(1, 32)
                ];

            validators['axisYName'] =
                [
                    new NotNull(),
                    new Length(1, 32)
                ];

            validators['singleSelect'] =
                [
                    new NotNull(),
                    new Length(1, 32)
                ];

            validators['seriesSelectedName'] =
                [
                    new NotNull(),
                    new Length(1, 32)
                ];
            validators['inputDataInteger'] =
                [
                    new Size(-2147483648, 2147483648)
                ];
            validators['inputDataDouble'] =
                [
                    new Size(-2147483648, 2147483648)
                ];
            return validators;
        }

        $scope.$on("$destroy", function() {
            MultiVisualisationConfigObject.setChangedConfig(wizardSeries.chartConfiguration)
        });
        
        function chartTypeWizardStepIsChart() {
            return !(wizardSeries.chartConfiguration.chartType === 'TABLE')
        }
    }
})();