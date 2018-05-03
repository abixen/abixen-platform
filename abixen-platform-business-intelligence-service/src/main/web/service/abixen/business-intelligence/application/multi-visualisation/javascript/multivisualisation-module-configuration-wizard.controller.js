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
        .controller('ChartModuleConfigurationWizardController', ChartModuleConfigurationWizardController);

    ChartModuleConfigurationWizardController.$inject = [
        '$scope',
        '$log',
        'ApplicationDataSource',
        'ChartModuleConfiguration',
        'CharData',
        'multivisualisationWizardStep'
    ];

    function ChartModuleConfigurationWizardController($scope, $log, ApplicationDataSource, ChartModuleConfiguration, CharData, multivisualisationWizardStep) {
        $log.log('ChartModuleConfigurationWizardController');

        var configWizard = this;
        var seriesNumber = 1; //todo remove in the future

        configWizard.stepCurrent = 0;
        configWizard.stepMax = 3;
        configWizard.validators = getValidators();

        //TODO - check if needed
        $scope.chartConfiguration = configWizard.chartConfiguration = {
            id: null,
            moduleId: null
        };


        configWizard.chartTypes = multivisualisationWizardStep.getChartTypes();
        configWizard.dataSources = null;

        configWizard.chart = {
            name: null,
            hasTable: false,
            axisX: null,
            axisY: null,
            series: null,
            seriesSelected: null
        };

        configWizard.table = {
            columns: [],
            columnSelected: null
        };

        configWizard.conditionsForFilter = [
            {name: ''},
            {name: '='},
            {name: '<>'},
            {name: '<'},
            {name: '<='},
            {name: '>'},
            {name: '>='}
        ];

        configWizard.canNext = canNext;
        configWizard.next = next;
        configWizard.prev = prev;

        configWizard.setDataSourceSelected = setDataSourceSelected;
        configWizard.setChartTypeSelected = setChartTypeSelected;
        configWizard.initDataSetSeries = initDataSetSeries;
        configWizard.addDataSetSeries = addDataSetSeries;
        configWizard.removeDataSetSeries = removeDataSetSeries;
        configWizard.setDataSetSeriesSelected = setDataSetSeriesSelected;
        configWizard.reloadPreviewData = reloadPreviewData;
        configWizard.resetFilters = resetFilters;
        configWizard.isChart = $scope.isChart = chartTypeWizardStepIsChart;
        configWizard.setColumnSelected = setColumnSelected;


        getChartConfiguration($scope.moduleId);
        changeWizardView();


        function chartTypeWizardStepValidate() {
            return configWizard.chartConfiguration.chartType !== null && configWizard.chartConfiguration.chartType !== undefined;
        }

        function chartTypeWizardStepIsChart() {
            return !(configWizard.chartConfiguration.chartType === 'TABLE')
        }

        function setChartTypeSelected(chartType) {
            configWizard.chartConfiguration.chartType = chartType.type;
        }

        function saveConfiguration(configuration) {
            if (configuration === undefined) configuration = configWizard.chartConfiguration;
            configuration = prepareFilterForDomain(configuration);
            if (configWizard.chartConfiguration.id) {
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

        function prepareFilterForDomain(configuration) {
            $log.debug("configuration", configuration);
            configuration.filter = parseObjToJsonCriteriaAsString(configuration.dataSet.domainXSeriesColumn);
            return configuration
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

        function convertToString(jsonObj) {
            return JSON.stringify(jsonObj);
        }

        function getIndexInTableForColumnIdx(columns, idx) {
            return columns.findIndex(function (element) {
                return element.idx == idx;
            })
        }

        function setColumnSelected(idx) {
            $log.log('moduleConfigurationWizardStep setSelected ', idx);
            idx = getIndexInTableForColumnIdx(configWizard.table.columns, idx);
            configWizard.table.columnSelected = configWizard.table.columns[idx];
            configWizard.table.columns[idx].isActive = !configWizard.table.columns[idx].isActive;
            if (configWizard.table.columns[idx].isActive === true) {
                getColumnData(idx);
            } else {
                configWizard.table.columnPreviewData = [];
                buildTableConfiguration();
            }
        }

        function refreshColumn() {
            $log.debug('configWizard.chartConfiguration.dataSource', configWizard.chartConfiguration.dataSource);
            configWizard.table.columns = [];

            function compare(a, b) {
                if (a.id < b.id)
                    return -1;
                if (a.id > b.id)
                    return 1;
                return 0;
            }

            configWizard.chartConfiguration.dataSource.columns.sort(compare).forEach(function (column) {
                var isActive = false;
                if (configWizard.chartConfiguration.dataSet && configWizard.chartConfiguration.dataSet.domainXSeriesColumn.dataSourceColumn !== null) {
                    isActive = configWizard.chartConfiguration.dataSet.domainXSeriesColumn.dataSourceColumn.name === column.name;
                }
                if (isActive === false && configWizard.chartConfiguration.dataSet && configWizard.chartConfiguration.dataSet.dataSetSeries !== null) {
                    configWizard.chartConfiguration.dataSet.dataSetSeries.forEach(function (series) {
                        if (isActive === false && series.valueSeriesColumn.name === column.name) {
                            isActive = true;
                        }
                    })
                }

                configWizard.table.columns.push({
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
            configWizard.chartConfiguration.moduleId = $scope.moduleId;
            if (configWizard.stepCurrent === 2 && !chartTypeWizardStepIsChart()) {
                refreshColumn();
            }
        }

        function getColumnData(idx) {
            configWizard.table.columnPreviewData = [];
            if (configWizard.table.columnSelected.name != undefined && configWizard.table.columnSelected.name !== '') {
                CharData.query({seriesName: configWizard.table.columnSelected.name}, buildTableConfiguration())
                    .$promise
                    .then(onQueryResult)
            }

            function onQueryResult(data) {
                $log.log('CharData.query: ', data);
                data.forEach(function (el) {
                    configWizard.table.columnPreviewData.push({
                        value: el[configWizard.table.columnSelected.name].value
                    });
                })
            }
        }

        function getSeriesData() {
            configWizard.chart.seriesPreviewData = [];
            if (configWizard.dataSetSeriesSelected) {
                if (configWizard.dataSetSeriesSelected.valueSeriesColumn.dataSourceColumn !== null && configWizard.dataSetSeriesSelected.valueSeriesColumn.dataSourceColumn.name !== undefined && configWizard.dataSetSeriesSelected.valueSeriesColumn.dataSourceColumn.name !== '') {
                    configWizard.chartConfiguration = prepareFilterForDomain(configWizard.chartConfiguration);
                    CharData.query({seriesName: configWizard.dataSetSeriesSelected.name}, configWizard.chartConfiguration)
                        .$promise
                        .then(onQueryResult);
                }
            }

            function onQueryResult(data) {
                $log.log('CharData.query: ', data);
                data.forEach(function (el) {
                    configWizard.chart.seriesPreviewData.push({
                        x: el[configWizard.chartConfiguration.dataSet.domainXSeriesColumn.dataSourceColumn.name].value,
                        y: el[configWizard.dataSetSeriesSelected.valueSeriesColumn.dataSourceColumn.name].value
                    });
                })
            }
        }

        function getDataSources() {
            $scope.$emit(platformParameters.events.START_REQUEST);
            var queryParameters = {
                dataSourceType: null,
                page: 0,
                size: 10000,
                sort: 'id,asc'
            };

            ApplicationDataSource.query(queryParameters)
                .$promise
                .then(onQueryResult, onQueryError);

            function onQueryResult(data) {
                configWizard.dataSources = data.content;
                $scope.$emit(platformParameters.events.STOP_REQUEST);
            }

            function onQueryError() {
                $scope.$emit(platformParameters.events.STOP_REQUEST);
            }
        }

        function setDataSourceSelected(dataSource) {
            dataSource.classType = dataSource.dataSourceType;
            configWizard.chartConfiguration.dataSource = dataSource;
        }

        function dataSourceWizardStepStepSelected() {
            if (configWizard.dataSources == null) {
                getDataSources();
            }
        }

        function dataSourceWizardStepValidate() {
            return configWizard.chartConfiguration.dataSource !== null && configWizard.chartConfiguration.dataSource !== undefined;
        }

        function dataConfigurationWizardStepValidate() {
            if (configWizard.entityForm) {
                if (configWizard.chartConfiguration.chartType === 'TABLE'){
                    var isActive = false;
                    configWizard.table.columns.forEach(function (column) {
                        if (column.isActive === true) {
                            isActive = true;
                        }
                    });
                    return isActive;
                }
                else {
                    return configWizard.entityForm.$valid;
                }
            }
            return false;
        }

        function buildTableConfiguration() {
            var tableConfiguration = {
                axisXName: '',
                axisYName: '',
                chartType: 'TABLE',
                dataSource: configWizard.chartConfiguration.dataSource,
                moduleId: configWizard.chartConfiguration.moduleId,
                id: configWizard.chartConfiguration.id,
                dataSet: {
                    dataSetSeries: [],
                    domainXSeriesColumn: null,
                    domainZSeriesColumn: null
                }
            };

            var i = 0;
            configWizard.table.columns.forEach(function (column) {
                $log.debug('column: ', column);
                if (column.isActive === true) {
                    if (i === 0) {
                        tableConfiguration.dataSet.domainXSeriesColumn = {
                            id: null,
                            name: '',
                            type: 'X',
                            dataSourceColumn: column.dataSourceColumn
                        };
                        i++
                    } else {
                        tableConfiguration.dataSet.dataSetSeries.push({
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
            $scope.tableConfiguration = tableConfiguration;
            return tableConfiguration
        }

        function prev() {
            if (configWizard.stepCurrent > 0) {
                configWizard.stepCurrent--;
            }
        }

        function changeWizardView() {
            switch (configWizard.stepCurrent) {
                //TODO - is it OK?
                case 0:

                case 1:
                    dataSourceWizardStepStepSelected();
                    break;
                case 2:
                    moduleConfigurationWizardStepSelected();
                    break;
                case 3:
                    configWizard.chartConfiguration = prepareFilterForDomain(configWizard.chartConfiguration);
                    break;
            }
        }

        function getChartConfiguration(moduleId) {
            if (moduleId === null) {
                return;
            }
            ChartModuleConfiguration.get({id: moduleId})
                .$promise
                .then(onGetResult);

            function onGetResult(chartConfiguration) {
                $scope.chartConfiguration = configWizard.chartConfiguration = chartConfiguration;
                if (configWizard.chartConfiguration.id === null) {
                    configWizard.chartConfiguration = {
                        moduleId: $scope.moduleId,
                        axisXName: '', //TODO maybe null, not '' ?
                        axisYName: '',
                        dataSet: {
                            dataSetSeries: [],
                            domainXSeriesColumn: {
                                id: null,
                                name: '',
                                type: 'X',
                                dataSourceColumn: null
                            }
                        }
                    }
                } else {
                    if (configWizard.chartConfiguration.dataSet) {
                        buildObjFromJson(configWizard.chartConfiguration.dataSet.domainXSeriesColumn, configWizard.chartConfiguration.filter);
                        if (configWizard.chartConfiguration.dataSet.dataSetSeries.length > 0){
                            setDataSetSeriesSelected(configWizard.chartConfiguration.dataSet.dataSetSeries[0]);
                        }
                    }
                }
            }
    }

        function initDataSetSeries() {
            if (!configWizard.chartConfiguration.dataSet){
                configWizard.chartConfiguration.dataSet = {};
            }
            if (!configWizard.chartConfiguration.dataSet.dataSetSeries){
                configWizard.chartConfiguration.dataSet.dataSetSeries = [];
            }
            if (configWizard.chartConfiguration.dataSet.dataSetSeries.length === 0) {
                addDataSetSeries();
            } else {
                if (configWizard.dataSetSeriesSelected === undefined || configWizard.dataSetSeriesSelected === null) {
                    setDataSetSeriesSelected(configWizard.chartConfiguration.dataSet.dataSetSeries[0]);
                }
            }
        }

        function addDataSetSeries() {
            $log.log('moduleConfigurationWizardStep series', configWizard.chartConfiguration.dataSet.dataSetSeries);

            if (configWizard.chartConfiguration.dataSet.dataSetSeries === undefined) {
                configWizard.chartConfiguration.dataSet.dataSetSeries = [];
            }

            if (configWizard.chartConfiguration.dataSet.dataSetSeries.length == 0) {
                seriesNumber = 1;
            }

            configWizard.chartConfiguration.dataSet.dataSetSeries.push({
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

            if (configWizard.dataSetSeriesSelected == null) {
                setDataSetSeriesSelected(configWizard.chartConfiguration.dataSet.dataSetSeries[0]);
            }
        }

        function removeDataSetSeries(dataSetSeries) {
            $log.log('moduleConfigurationWizardStep removeSeries ', dataSetSeries);

            var index = configWizard.chartConfiguration.dataSet.dataSetSeries.indexOf(dataSetSeries);

            if (index + 1 < configWizard.chartConfiguration.dataSet.dataSetSeries.length) {
                var nextDataSetSeries = configWizard.chartConfiguration.dataSet.dataSetSeries[index + 1];
                setDataSetSeriesSelected(nextDataSetSeries);
            } else if (index > 0) {
                var prevDataSetSeries = configWizard.chartConfiguration.dataSet.dataSetSeries[index - 1];
                setDataSetSeriesSelected(prevDataSetSeries);
            } else {
                setDataSetSeriesSelected(null);
                configWizard.chartConfiguration.dataSet.dataSetSeries = [];
            }

            if (configWizard.chartConfiguration.dataSet.dataSetSeries !== []) {
                configWizard.chartConfiguration.dataSet.dataSetSeries.splice(index, 1);
            }

            //TODO - select previous

            /*for (var i = 0; i < configWizard.chart.series.length; i++) {
             if (configWizard.chart.series[i].idx == idx) {
             configWizard.chart.series[i].isValid = false;
             }
             }*/
        }

        function setDataSetSeriesSelected(dataSetSeries) {
            $log.log('moduleConfigurationWizardStep setSelected ', dataSetSeries);

            configWizard.dataSetSeriesSelected = dataSetSeries; //configWizard.chart.series[idx - 1];


        }

        function canNext() {
            var validate = true;
            if (configWizard.stepCurrent == 0) {
                validate = chartTypeWizardStepValidate();
                return validate;
            }
            if (configWizard.stepCurrent == 1) {
                validate = dataSourceWizardStepValidate();
                return validate;
            }
            if (configWizard.stepCurrent === 2) {
                validate = dataConfigurationWizardStepValidate();
            }
            return validate
        }

        function next() {
            if (configWizard.stepCurrent < configWizard.stepMax) {
                configWizard.stepCurrent++;
                changeWizardView();
            } else {
                if (chartTypeWizardStepIsChart()) {
                    saveConfiguration();
                } else {
                    saveConfiguration(buildTableConfiguration())
                }
            }
            $log.log('next step:', configWizard.stepCurrent);
        }

        function reloadPreviewData() {
            getSeriesData();
        }

        function resetFilters() {
            var domainSeries = configWizard.chartConfiguration.dataSet.domainXSeriesColumn;
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

        function isDate(date) {
            return (new Date(date) !== "Invalid Date") && !isNaN(new Date(date));
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

    }
})();