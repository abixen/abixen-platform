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
        .provider('dataChartAdapter', dataChartAdapter);

    dataChartAdapter.$inject = ['$logProvider',
        '$injector',
        '$windowProvider'
    ];

    function dataChartAdapter($logProvider, $injector, $windowProvider) {
        var window = $injector.instantiate($windowProvider.$get);
        var $log = $injector.instantiate($logProvider.$get, {$window: window});

        return {
            $get: function () {
                return {
                    convertTo: convertTo
                };
            }
        };

        function getDefaultChartConfig() {
            return {
                chart: {
                    type: 'lineChart',
                    height: 450,
                    margin: {
                        top: 20,
                        right: 55,
                        bottom: 45,
                        left: 100
                    },
                    x: function (d) {
                        return d.x;

                    },
                    y: function (d) {
                        return d.yLabel;
                    },
                    xAxis: {
                        axisLabel: 'DefaultAxisLabel',
                        tickFormat: function (d) {
                            return d;
                        }
                    },
                    yAxis: {
                        axisLabel: 'DefaultAxisLabel',
                        axisLabelDistance: 30,
                        tickFormat: function (d) {
                            return d3.format('.02f')(d);
                        }
                    },
                    callback: function (chart) {
                        $log.log('lineChart callback');
                    }
                }
            }
        }

        function buildDefaultChartOption(chartType, configurationData, preparedChartData) {
            $log.debug('buildChartOptions for ' + chartType + 'Adapter started');
            var chartConfig = getDefaultChartConfig();
            chartConfig.chart.type = chartType;
            chartConfig.chart.xAxis.axisLabel = configurationData.axisXName;
            chartConfig.chart.xAxis.tickFormat = function (d) {
                var label = findXLabel(preparedChartData[0].values, d);
                if (isDate(label) && isNaN(preparedChartData[0].values[0].xLabel)){
                    return d3.time.format('%d-%m-%Y')(new Date(label))
                } else {
                    return label;
                }
            };
            chartConfig.chart.yAxis.tickFormat = function (d) {
                return d3.format('.02f')(d);
            };
            if (preparedChartData[0].values && preparedChartData[0].values[0] && isNaN(preparedChartData[0].values[0].xLabel)){
                chartConfig.chart.xAxis.rotateLabels = 45;
                chartConfig.chart.margin.bottom += 50;
            }
            chartConfig.chart.yAxis.axisLabel = configurationData.axisYName;
            $log.debug('buildChartOptions for ' + chartType + 'Adapter ended');
            return chartConfig;
        }

        function buildMultiSeriesChartData(chartType, configurationData, data) {
            $log.debug('buildChartData for ' + chartType + 'Adapter started');
            var series = [];
            configurationData.dataSetChart.dataSetSeries.forEach(function (dataSetSeriesElement) {
                $log.debug('dataSetSeriesElement: ', dataSetSeriesElement);
                series.push({
                    values: getValues(data, dataSetSeriesElement, configurationData.dataSetChart),
                    key: dataSetSeriesElement.name
                });
            });
            $log.debug('series: ', series);
            $log.debug('buildChartData for ' + chartType + 'Adapter ended');
            return series
        }

        function buildSingleSeriesChartData(chartType, configurationData, data) {
            $log.debug('buildChartData for ' + chartType + 'Adapter started');
            var preparedData = [];
            configurationData.dataSetChart.dataSetSeries.forEach(function (dataSetSeriesElement) {
                $log.debug('dataSetSeriesElement: ', dataSetSeriesElement);
                preparedData = getValues(data, dataSetSeriesElement, configurationData.dataSetChart);
            });
            $log.debug('preparedData: ', preparedData);
            $log.debug('buildChartData for ' + chartType + 'Adapter ended');
            return preparedData;
        }

        function calculateMean(data) {
            var sum = 0;
            for (var i = 0; i < data.length; i++) {
                if (!isNaN(data.xLabel)) {
                    sum += data.xLabel;
                }
            }
            return sum / data.length;
        }

        function getPointData(dataElement, dataSetSeriesElement, index, dataSetChart) {
            var x = null;
            var xLabel = null;
            var y = null;
            var yLabel = null;
            if (dataElement[dataSetChart.domainXSeriesColumn.dataSourceColumn.name]) {
                x = index;
                xLabel = dataElement[dataSetChart.domainXSeriesColumn.dataSourceColumn.name].value;
            }
            if (dataElement[dataSetSeriesElement.valueSeriesColumn.dataSourceColumn.name]) {
                y = index;
                yLabel = dataElement[dataSetSeriesElement.valueSeriesColumn.dataSourceColumn.name].value;
            }
            return {
                x: x,
                xLabel: xLabel,
                y: y,
                yLabel: yLabel
            }
        }

        function findXLabel(values, x) {
            for (var i = 0; i < values.length; i++) {
                if (values[i].x === x) {
                    return values[i].xLabel;
                }
            }
        }

        function getDomainXDuplication(values, valuesElement) {
            for (var i = 0; i < values.length; i++) {
                if (values[i].xLabel === valuesElement.xLabel) {
                    return valuesElement;
                }
            }
            return null;
        }

        function domainIsNotDuplicated(values, valuesElement) {
            return (getDomainXDuplication(values, valuesElement) === null);
        }

        function getValues(data, dataSetSeriesElement, dataSetChart) {
            var values = [];
            var index = 0;
            data.forEach(function (dataElement) {
                var valuesElement = getPointData(dataElement, dataSetSeriesElement, index, dataSetChart);
                if (valuesElement && domainIsNotDuplicated(values, valuesElement)) {
                    values.push(valuesElement);
                    index++;
                }
            });
            return values;
        }

        function ChartBuilder() {
            this.buildChartOption = null;
            this.buildChartData = null;

            this.setBuildChartOptions = function (customBuildChartOption) {
                this.buildChartOption = customBuildChartOption;
            };

            this.setBuildChartData = function (customBuildChartData) {
                this.buildChartData = customBuildChartData;
            };

            this.setDefaultChartBuilderFunction = function (chartType) {
                this.setBuildChartOptions(function (configurationData, preparedChartData) {
                    return buildDefaultChartOption(chartType, configurationData, preparedChartData);
                });
                this.setBuildChartData(function (configurationData, data) {
                    return buildMultiSeriesChartData(chartType, configurationData, data);
                });
            };
            this.build = function () {
                return {
                    buildChartOption: this.buildChartOption,
                    buildChartData: this.buildChartData
                }
            }
        }

        function pieChartAdapter() {
            var chartBuilder = new ChartBuilder();
            chartBuilder.setBuildChartOptions(function (configurationData, preparedChartData) {
                var chartConfig = buildDefaultChartOption('pieChart', configurationData, preparedChartData);
                chartConfig.chart.x = function (d) {
                    return d.xLabel
                };
                return chartConfig;
            });

            chartBuilder.setBuildChartData(
                function (configurationData, data) {
                    return buildSingleSeriesChartData('pieChart', configurationData, data)
                });
            return chartBuilder.build();
        }

        function donutChartAdapter() {
            var chartBuilder = new ChartBuilder();
            chartBuilder.setBuildChartOptions(function (configurationData, preparedChartData) {
                var chartConfig = buildDefaultChartOption('pieChart', configurationData, preparedChartData);
                chartConfig.chart.x = function (d) {
                    return d.xLabel
                };
                chartConfig.chart.donut = true;
                return chartConfig;
            });

            chartBuilder.setBuildChartData(
                function (configurationData, data) {
                    return buildSingleSeriesChartData('pieChart', configurationData, data)
                });
            return chartBuilder.build();
        }

        function cumulativeLineChartAdapter() {
            var chartBuilder = new ChartBuilder();
            chartBuilder.setBuildChartOptions(function (configurationData, preparedChartData) {
                var chartConfig = buildDefaultChartOption('cumulativeLineChart', configurationData, preparedChartData);
                chartConfig.chart.average = function (d) {
                    return d.mean / 100;
                };
                return chartConfig;
            });

            chartBuilder.setBuildChartData(
                function buildChartData(configurationData, data) {
                    $log.debug('buildChartData for cumulativeLineChartAdapter started');
                    var series = [];
                    configurationData.dataSetChart.dataSetSeries.forEach(function (dataSetSeriesElement) {
                        $log.debug('dataSetSeriesElement: ', dataSetSeriesElement);
                        var values = getValues(data, dataSetSeriesElement, configurationData.dataSetChart);
                        series.push({
                            values: values,
                            mean: calculateMean(values),
                            key: dataSetSeriesElement.name
                        });
                    });
                    $log.debug('series: ', series);
                    $log.debug('buildChartData for cumulativeLineChartAdapter ended');
                    return series;
                });
            return chartBuilder.build();
        }

        function lineWithFocusChartAdapter() {
            var chartBuilder = new ChartBuilder();
            chartBuilder.setBuildChartOptions(function (configurationData, preparedChartData) {
                var chartConfig = buildDefaultChartOption('lineWithFocusChart', configurationData, preparedChartData);
                $log.debug('Adding additional setting for lineWithFocusChart');
                chartConfig.chart.x2Axis = {};
                chartConfig.chart.x2Axis.tickFormat = function (d) {
                    return findXLabel(preparedChartData[0].values, d);
                };
                $log.debug('Added additional setting for lineWithFocusChart');
                return chartConfig;
            });

            chartBuilder.setBuildChartData(
                function (configurationData, data) {
                    return buildMultiSeriesChartData('lineWithFocusChart', configurationData, data)
                });
            return chartBuilder.build();
        }

        function linePlusBarChartAdapter() {
            var chartBuilder = new ChartBuilder();
            chartBuilder.setBuildChartOptions(function (configurationData, preparedChartData) {
                var chartConfig = buildDefaultChartOption('linePlusBarChart', configurationData, preparedChartData);
                $log.debug('Adding additional setting for linePlusBarChart');
                chartConfig.chart.x2Axis = {};
                chartConfig.chart.x2Axis.tickFormat = function (d) {
                    return findXLabel(preparedChartData[0].values, d);
                };
                $log.debug('Added additional setting for linePlusBarChart');
                return chartConfig;
            });

            chartBuilder.setBuildChartData(
                function (configurationData, data) {
                    var chartData = buildMultiSeriesChartData('linePlusBarChart', configurationData, data)
                    chartData[0].bar = true;
                    return chartData;
                });
            return chartBuilder.build();
        }

        function multiBarHorizontalChartAdapter() {
            var chartBuilder = new ChartBuilder();
            chartBuilder.setBuildChartOptions(function (configurationData, preparedChartData) {
                var chartConfig = buildDefaultChartOption('multiBarHorizontalChart', configurationData, preparedChartData);
                chartConfig.chart.xAxis.axisLabelDistance = chartConfig.chart.yAxis.axisLabelDistance;
                chartConfig.chart.yAxis.axisLabelDistance = 0;
                $log.debug('Added additional setting for multiBarHorizontalChart');
                return chartConfig;
            });

            chartBuilder.setBuildChartData(
                function (configurationData, data) {
                    var chartData = buildMultiSeriesChartData('multiBarHorizontalChart', configurationData, data)
                    return chartData;
                });
            return chartBuilder.build();
        }

        function genericChartAdapter(chartType) {
            var chartBuilder = new ChartBuilder();
            chartBuilder.setDefaultChartBuilderFunction(chartType);
            return chartBuilder.build();
        }

        function convertToChart(configurationData, rawData, adapter) {
            $log.debug('convertToChart started');
            var chartData = adapter.buildChartData(configurationData, rawData);
            var chartOption = adapter.buildChartOption(configurationData, chartData);
            var chart = {
                options: chartOption,
                data: chartData
            };
            $log.debug('convertToChart ended. Chart : ', chart);
            return chart;
        }

        function isDate(date) {
            return (new Date(date) !== "Invalid Date") && !isNaN(new Date(date));
        }

        function convertTo(configurationData, data) {
            var chartType = configurationData.chartType;
            $log.debug('convertTo chartType: ' + chartType);

            if (chartType === 'TABLE') {
                return convertToChart(configurationData, data, genericChartAdapter('table'));
            }
            if (chartType === 'LINE' || chartType === 'LINE_TABLE') {
                return convertToChart(configurationData, data, genericChartAdapter('lineChart'));
            }
            if (chartType === 'PIE' || chartType === 'PIE_TABLE') {
                return convertToChart(configurationData, data, pieChartAdapter());
            }
            if (chartType === 'MULTI_BAR' || chartType === 'MULTI_BAR_TABLE') {
                return convertToChart(configurationData, data, multiBarHorizontalChartAdapter());
            }
            if (chartType === 'MULTI_COLUMN' || chartType === 'MULTI_COLUMN_TABLE') {
                return convertToChart(configurationData, data, genericChartAdapter('multiBarChart'));
            }
            if (chartType === 'STACKED_AREA' || chartType === 'STACKED_AREA_TABLE') {
                return convertToChart(configurationData, data, genericChartAdapter('stackedAreaChart'));
            }
            if (chartType === 'DONUT' || chartType === 'DONUT_TABLE') {
                return convertToChart(configurationData, data, donutChartAdapter());
            }
            if (chartType === 'DISCRETE_COLUMN' || chartType === 'DISCRETE_COLUMN_TABLE') {
                return convertToChart(configurationData, data, genericChartAdapter('discreteBarChart'));
            }
            if (chartType === 'HISTORICAL_COLUMN' || chartType === 'HISTORICAL_COLUMN_TABLE') {
                return convertToChart(configurationData, data, genericChartAdapter('historicalBarChart'));
            }
            if (chartType === 'CUMULATIVE_LINE' || chartType === 'CUMULATIVE_LINE_TABLE') {
                return convertToChart(configurationData, data, cumulativeLineChartAdapter());
            }
            if (chartType === 'SCATTER' || chartType === 'SCATTER_TABLE') {
                return convertToChart(configurationData, data, genericChartAdapter('scatterChart'));
            }
            if (chartType === 'LINE_WITH_FOCUS_CHART' || chartType === 'LINE_WITH_FOCUS_CHART_TABLE') {
                return convertToChart(configurationData, data, lineWithFocusChartAdapter());
            }
            if (chartType === 'LINE_AND_BAR_WITH_FOCUS_CHART' || chartType === 'LINE_AND_BAR_WITH_FOCUS_CHART_TABLE') {
                return convertToChart(configurationData, data, linePlusBarChartAdapter());
            }
            throw new Error('Wrong chart type');
        }
    }
})();