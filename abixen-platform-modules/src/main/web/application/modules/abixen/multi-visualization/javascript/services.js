var platformChartModuleServices = angular.module('platformChartModuleServices', ['ngResource']);

platformChartModuleServices.factory('ChartModuleInit', ['$resource',
    function ($resource) {
        return $resource('/application/modules/abixen/multi-visualization/:id', {}, {});
    }]);

platformChartModuleServices.factory('ApplicationDatabaseDataSource', ['$resource',
    function ($resource) {
        return $resource('/admin/modules/abixen/multi-visualization/database-data-sources', {}, {
            query: {method: 'GET', isArray: false}
        });
    }]);

platformChartModuleServices.factory('ChartModuleConfiguration', ['$resource',
    function ($resource) {
        return $resource('/application/modules/abixen/multi-visualization/configuration/:id', {}, {
            update: {method: 'PUT'}
        });
    }]);

platformChartModuleServices.factory('CharData', ['$resource',
    function ($resource) {
        return $resource('/application/modules/abixen/multi-visualization/data', {}, {
            query: {method: 'POST', isArray: true}
        });
    }]);

platformChartModuleServices.factory('CharDataPreview', ['$resource',
    function ($resource) {
        return $resource('/application/modules/abixen/multi-visualization/data-preview/:seriesName', {}, {
            query: {method: 'POST', params: {seriesName: '@seriesName'}, isArray: true}
        });
    }]);

platformChartModuleServices.provider('dataChartAdapter', function ($logProvider, $injector, $windowProvider) {
    var window = $injector.instantiate($windowProvider.$get);
    var $log = $injector.instantiate($logProvider.$get, {$window: window});

    var getDefaultChartConfig = function () {
        return {
            chart: {
                type: 'lineChart',
                height: 450,
                margin: {
                    top: 20,
                    right: 45,
                    bottom: 40,
                    left: 80
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
                    tickFormat: function (d) {
                        return d;
                    }
                },
                callback: function (chart) {
                    console.log('!!! lineChart callback !!!');
                }
            }
        }
    };

    function buildDefaultChartOption(chartType, configurationData, preparedChartData) {
        $log.debug('buildChartOptions for ' + chartType + 'Adapter started');
        var chartConfig = getDefaultChartConfig();
        chartConfig.chart.type = chartType;
        chartConfig.chart.xAxis.axisLabel = configurationData.axisXName;
        chartConfig.chart.xAxis.tickFormat = function (d) {
            return findXLabel(preparedChartData[0].values, d);
        };
        chartConfig.chart.yAxis.tickFormat = function (d) {
            return d;
        };
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
        data.forEach(function (dataElement, iterator) {
            var valuesElement = getPointData(dataElement, dataSetSeriesElement, iterator, dataSetChart);
            if (valuesElement != null && domainIsNotDuplicated(values, valuesElement)) {
                values.push(valuesElement);
            }
        });
        return values;
    }

    function ChartBuilder() {
        this.buildChartOption = null;
        this.buildChartData = null;

        this.setBuildChartOptions = function(customBuildChartOption) {
            this.buildChartOption = customBuildChartOption;
        };

        this.setBuildChartData = function(customBuildChartData) {
            this.buildChartData = customBuildChartData;
        };

        this.setDefaultChartBuilderFunction = function(chartType) {
            this.setBuildChartOptions(function (configurationData, preparedChartData) {
                return buildDefaultChartOption(chartType, configurationData, preparedChartData);
            });
            this.setBuildChartData(function (configurationData, data) {
                return buildMultiSeriesChartData(chartType, configurationData, data);
            });
        };
        this.build = function() {
            return {
                buildChartOption: this.buildChartOption,
                buildChartData: this.buildChartData
            }
        }
    }

    var pieChartAdapter = function () {
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
    };

    var donutChartAdapter = function () {
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
    };

    var cumulativeLineChartAdapter = function () {
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
    };

    var genericChartAdapter = function (chartType) {
        var chartBuilder = new ChartBuilder();
        chartBuilder.setDefaultChartBuilderFunction(chartType);
        return chartBuilder.build();
    };

    var convertToChart = function (configurationData, rawData, adapter) {
        $log.debug('convertToChart started');
        var chartData = adapter.buildChartData(configurationData, rawData);
        var chartOption = adapter.buildChartOption(configurationData, chartData);
        var chart = {
            options: chartOption,
            data: chartData
        };
        $log.debug('convertToChart ended. Chart : ', chart);
        return chart;
    };

    var convertTo = function (configurationData, data) {
        var chartType = configurationData.chartType;
        $log.debug('convertTo chartType: ' + chartType);

        if (chartType === 'LINE' || chartType === 'LINE_TABLE') {
            return convertToChart(configurationData, data, genericChartAdapter('lineChart'));
        }
        if (chartType === 'PIE' || chartType === 'PIE_TABLE') {
            return convertToChart(configurationData, data, pieChartAdapter());
        }
        if (chartType === 'MULTI_BAR' || chartType === 'MULTI_BAR_TABLE') {
            return convertToChart(configurationData, data, genericChartAdapter('multiBarHorizontalChart'));
        }
        if (chartType === 'MULTI_COLUMN' || chartType === 'MULTI_COLUMN_TABLE') {
            return convertToChart(configurationData, data, genericChartAdapter('genericChartAdapter'));
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
        throw new Error('Wrong chart type');
    };
    return {
        $get: function () {
            return {
                convertTo: convertTo
            };
        }
    };

});

