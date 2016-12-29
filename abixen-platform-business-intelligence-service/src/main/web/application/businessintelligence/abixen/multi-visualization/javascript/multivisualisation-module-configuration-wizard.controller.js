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
        '$http',
        '$log',
        'ApplicationDatabaseDataSource',
        'ChartModuleConfiguration',
        'CharDataPreview',
        'multivisualisationWizardStep'
    ];

    function ChartModuleConfigurationWizardController($scope, $http, $log, ApplicationDatabaseDataSource, ChartModuleConfiguration, CharDataPreview, multivisualisationWizardStep) {
        $log.log('ChartModuleConfigurationWizardController');
        $scope.stepCurrent = 0;
        $scope.stepMax = 3;

        $scope.chartConfiguration = {
            axisXName: '',
            axisYName: ''
        };

        var getChartConfiguration = function (moduleId) {
            if (moduleId) {
                ChartModuleConfiguration.get({id: moduleId}, function (data) {
                    $scope.chartConfiguration = data;
                    if ($scope.chartConfiguration.id == null) {
                        $scope.chartConfiguration = {
                            moduleId: $scope.moduleId,
                            axisXName: '',
                            axisYName: '',
                            dataSetChart: {
                                dataSetSeries: [],
                                domainXSeriesColumn: {
                                    id: null,
                                    name: '',
                                    type: 'X',
                                    dataSourceColumn: null
                                }
                            }
                        }
                    }
                    $log.log('ChartModuleConfiguration has been got 2: ', data, $scope.chartConfiguration);
                });
            }
        };

        getChartConfiguration($scope.moduleId);


        //$scope.name;


        var init = function () {
            $scope.stepCurrent = 0;
            initInitWizardStep();
            initDataSourceWizardStep();
            initModuleConfigurationWizardStep();

        };
        var initInitWizardStep = function () {

            $scope.initWizardStep = {};
            $scope.chartTypes = multivisualisationWizardStep.getChartTypes();

            //$scope.initWizardStep.selected = null;
            $scope.setChartTypeSelected = function (chartType) {
                $log.log('setChartTypeSelected: ', chartType);
                //$scope.initWizardStep.selected = moduleType;
                $scope.chartConfiguration.chartType = chartType.type;
            };

            $scope.initWizardStep.validate = function () {
                if ($scope.chartConfiguration.chartType == null) {
                    return false;
                }
                return true;
            };

            $scope.initWizardStep.isChart = function () {
                if ($scope.chartConfiguration.chartType == 'TABLE') {
                    return false;
                } else {
                    return true;
                }
            }

        };

        var saveConfiguration = function () {
            if ($scope.chartConfiguration.id) {
                ChartModuleConfiguration.update({id: $scope.chartConfiguration.id}, $scope.chartConfiguration, function () {
                    $log.log('ChartModuleConfiguration has been updated: ', $scope.chartConfiguration);
                    $scope.$emit('VIEW_MODE');
                });
            } else {
                ChartModuleConfiguration.save($scope.chartConfiguration, function () {
                    $log.log('ChartModuleConfiguration has been saved: ', $scope.chartConfiguration);
                    $scope.$emit('VIEW_MODE');
                });
            }
        };


        var initModuleConfigurationWizardStep = function () {
            $scope.moduleConfigurationWizardStep = {};
            $scope.moduleConfigurationWizardStep.chart = {};
            $scope.moduleConfigurationWizardStep.chart.name = null;
            $scope.moduleConfigurationWizardStep.chart.hasTable = false;
            $scope.moduleConfigurationWizardStep.chart.axisX = null;
            $scope.moduleConfigurationWizardStep.chart.axisY = null;
            $scope.moduleConfigurationWizardStep.chart.series = null;
            $scope.moduleConfigurationWizardStep.chart.seriesSelected = null;
            $scope.seriesNumber = 1; //todo remove in the future

            $scope.initDataSetSeries = function () {
                if ($scope.chartConfiguration.dataSetChart.dataSetSeries.length === 0) {
                    $scope.addDataSetSeries();
                } else {
                    $log.debug('$scope.dataSetSeriesSelected ', $scope.dataSetSeriesSelected);
                    if ($scope.dataSetSeriesSelected === undefined || $scope.dataSetSeriesSelected === null) {
                        $scope.setDataSetSeriesSelected($scope.chartConfiguration.dataSetChart.dataSetSeries[0]);
                    }
                }
            };

            $scope.addDataSetSeries = function () {
                $log.log('moduleConfigurationWizardStep series', $scope.chartConfiguration.dataSetChart.dataSetSeries);

                if ($scope.chartConfiguration.dataSetChart.dataSetSeries.length == 0) {
                    $scope.seriesNumber = 1;
                }

                $scope.chartConfiguration.dataSetChart.dataSetSeries.push({
                    id: null,
                    name: ('Series ' + $scope.seriesNumber),
                    isValid: true,
                    valueSeriesColumn: {
                        id: null,
                        name: '',
                        type: 'Y',
                        dataSourceColumn: null
                    }

                });
                $scope.seriesNumber++;

                if ($scope.dataSetSeriesSelected == null) {
                    $scope.setDataSetSeriesSelected($scope.chartConfiguration.dataSetChart.dataSetSeries[0]);
                }
            };

            $scope.removeDataSetSeries = function (dataSetSeries) {
                $log.log('moduleConfigurationWizardStep removeSeries ', dataSetSeries);

                var index = $scope.chartConfiguration.dataSetChart.dataSetSeries.indexOf(dataSetSeries);

                if (index + 1 < $scope.chartConfiguration.dataSetChart.dataSetSeries.length) {
                    var nextDataSetSeries = $scope.chartConfiguration.dataSetChart.dataSetSeries[index + 1];
                    $scope.setDataSetSeriesSelected(nextDataSetSeries);
                } else if (index > 0) {
                    var prevDataSetSeries = $scope.chartConfiguration.dataSetChart.dataSetSeries[index - 1];
                    $scope.setDataSetSeriesSelected(prevDataSetSeries);
                } else {
                    $scope.setDataSetSeriesSelected(null);
                }

                $scope.chartConfiguration.dataSetChart.dataSetSeries.splice(index, 1);

                //TODO - select previous

                /*for (var i = 0; i < $scope.moduleConfigurationWizardStep.chart.series.length; i++) {
                 if ($scope.moduleConfigurationWizardStep.chart.series[i].idx == idx) {
                 $scope.moduleConfigurationWizardStep.chart.series[i].isValid = false;
                 }
                 }*/

            };
            $scope.setDataSetSeriesSelected = function (dataSetSeries) {
                $log.log('moduleConfigurationWizardStep setSelected ', dataSetSeries);

                $scope.dataSetSeriesSelected = dataSetSeries; //$scope.moduleConfigurationWizardStep.chart.series[idx - 1];


                //TODO - pull from backend
                $scope.moduleConfigurationWizardStep.getSeriesData();
                $log.log('dataSetSeriesSelected:', $scope.dataSetSeriesSelected);


            };
            $scope.moduleConfigurationWizardStep.stepSelected = function () {
                $log.log('moduleConfigurationWizardStep selected');
                /*if ($scope.moduleConfigurationWizardStep.chart.series == null) {
                 $scope.moduleConfigurationWizardStep.chart.series = [];
                 $scope.moduleConfigurationWizardStep.chart.addSeries();
                 $scope.moduleConfigurationWizardStep.chart.seriesSelected = $scope.moduleConfigurationWizardStep.chart.series[0];
                 }*/
            };

            $scope.reloadPreviewData = function () {
                $scope.moduleConfigurationWizardStep.getSeriesData();
            };

            $scope.moduleConfigurationWizardStep.getSeriesData = function () {
                $scope.moduleConfigurationWizardStep.chart.seriesPreviewData = [];
                if ($scope.dataSetSeriesSelected.valueSeriesColumn.dataSourceColumn.name != undefined && $scope.dataSetSeriesSelected.valueSeriesColumn.dataSourceColumn.name !== '') {
                    CharDataPreview.query({seriesName: $scope.dataSetSeriesSelected.name}, $scope.chartConfiguration, function (data) {
                        $log.log('CharDataPreview.query: ', data);
                        data.forEach(function (el) {
                            $scope.moduleConfigurationWizardStep.chart.seriesPreviewData.push({
                                x: el[$scope.chartConfiguration.dataSetChart.domainXSeriesColumn.dataSourceColumn.name].value,
                                y: el[$scope.dataSetSeriesSelected.valueSeriesColumn.dataSourceColumn.name].value
                            });
                        })

                    }, function (error) {

                    });
                }
            };

            //table

            $scope.moduleConfigurationWizardStep.table = {};
            $scope.moduleConfigurationWizardStep.table.columns = [];
            $scope.moduleConfigurationWizardStep.table.columnSelected = null;

            $scope.moduleConfigurationWizardStep.table.getColumnData = function (idx) {
                $scope.moduleConfigurationWizardStep.table.columnPreviewData = [];
                $scope.moduleConfigurationWizardStep.table.columnPreviewData.push({value: 5});
                $scope.moduleConfigurationWizardStep.table.columnPreviewData.push({value: 243});
                $scope.moduleConfigurationWizardStep.table.columnPreviewData.push({value: 333});
                $scope.moduleConfigurationWizardStep.table.columnPreviewData.push({value: 13});
                $scope.moduleConfigurationWizardStep.table.columnPreviewData.push({value: 34});
                $scope.moduleConfigurationWizardStep.table.columnPreviewData.push({value: 32});
                //todo more
            }

            $scope.moduleConfigurationWizardStep.table.setSelected = function (idx) {
                $log.log('moduleConfigurationWizardStep setSelected ', idx);

                $scope.moduleConfigurationWizardStep.table.columnSelected = $scope.moduleConfigurationWizardStep.table.columns[idx];
                $scope.moduleConfigurationWizardStep.table.getColumnData(idx);

            };
            $scope.moduleConfigurationWizardStep.table.toggleColumn = function (idx) {

                var column = $scope.moduleConfigurationWizardStep.table.columns[idx];
                if (column.isActive) {
                    column.isActive = false;
                } else {
                    column.isActive = true;
                }


            };

            $scope.moduleConfigurationWizardStep.table.columns.push({
                idx: 0,
                name: 'Column 1',
                isValid: true,
                isActive: false
            });
            $scope.moduleConfigurationWizardStep.table.columns.push({
                idx: 1,
                name: 'Column 2',
                isValid: true,
                isActive: false
            });
            $scope.moduleConfigurationWizardStep.table.columns.push({
                idx: 2,
                name: 'Column 3',
                isValid: true,
                isActive: false
            });
            $scope.moduleConfigurationWizardStep.table.columns.push({
                idx: 3,
                name: 'Column 4',
                isValid: true,
                isActive: false
            });
            $scope.moduleConfigurationWizardStep.table.columns.push({
                idx: 4,
                name: 'Column 5',
                isValid: true,
                isActive: false
            });
            $scope.moduleConfigurationWizardStep.table.columns.push({
                idx: 5,
                name: 'Column 6',
                isValid: true,
                isActive: false
            });
            $scope.moduleConfigurationWizardStep.table.columns.push({
                idx: 6,
                name: 'Column 7',
                isValid: true,
                isActive: false
            });


        };

        $scope.dataSources = null;


        var getDataSources = function () {
            $scope.$emit(platformParameters.events.START_REQUEST);
            var queryParameters = {
                page: 0,
                size: 10000,
                sort: 'id,asc'
            };

            ApplicationDatabaseDataSource.query(queryParameters, function (data) {
                $log.log('ApplicationDatabaseDataSource query', data);
                $scope.dataSources = data.content;
                $scope.$emit(platformParameters.events.STOP_REQUEST);
            });

        };

        $scope.setDataSourceSelected = function (dataSource) {
            $log.log('setDataSourceSelected', dataSource);
            $scope.chartConfiguration.dataSource = dataSource;
        }

        var initDataSourceWizardStep = function () {
            $scope.dataSourceWizardStep = {};

            $scope.dataSourceWizardStep.stepSelected = function () {
                if ($scope.dataSources == null) {
                    getDataSources();
                }
            };

            //$scope.dataSourceWizardStep.selected = null;
            //$scope.dataSourceWizardStep.idSelected = null;

            //$scope.dataSourceWizardStep.setSelected = function (id) {
            //   $scope.dataSourceWizardStep.idSelected = id;
            //};

            $scope.dataSourceWizardStep.validate = function () {
                if ($scope.chartConfiguration.dataSource == null) return false;
                return true;
            };


        };


        $scope.canNext = function () {

            //$log.info('step current ', $scope.stepCurrent);
            var validate = true;
            if ($scope.stepCurrent == 0) {
                validate = $scope.initWizardStep.validate();
                //$log.info('validate 0', validate);
                return validate;
            }
            if ($scope.stepCurrent == 1) {
                validate = $scope.dataSourceWizardStep.validate();
                //$log.info('validate 1', validate);
                return validate;
            }
            //$log.info('validate default', validate);
            return validate
            // validate($scope.stepCurrent)
            //todo
        }

        $scope.next = function () {
            if ($scope.stepCurrent < $scope.stepMax) {
                $scope.stepCurrent++;
                changeWizardView();
            } else {
                saveConfiguration();
            }
            $log.log('next step:', $scope.stepCurrent);
        };

        $scope.prev = function () {
            if ($scope.stepCurrent > 0) {
                $scope.stepCurrent--;
            }
            $log.log('prev step:', $scope.stepCurrent);
        };


        var changeWizardView = function () {
            switch ($scope.stepCurrent) {
                case 0:

                case 1:
                    $scope.dataSourceWizardStep.stepSelected();
                    break;
                case 2:
                    $scope.moduleConfigurationWizardStep.stepSelected();
                    break;
            }
        };

        init();
        changeWizardView();

    }
})();