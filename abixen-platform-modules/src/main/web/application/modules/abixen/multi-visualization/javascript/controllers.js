var platformChartModuleControllers = angular.module('platformChartModuleControllers', []);

platformChartModuleControllers.controller('ChartModuleInitController', ['$scope', '$http', '$log', 'ChartModuleInit', function ($scope, $http, $log, ChartModuleInit) {
    $log.log('ChartModuleInitController');

    $scope.moduleId = null;

    $scope.showConfigurationWizard = function () {
        $scope.subview = 'configuration';
    };

    $scope.showChart = function () {
        $scope.subview = 'chart';
    };

    $scope.$on(platformParameters.events.RELOAD_MODULE, function (event, id, viewMode) {
        $log.log('RELOAD MODULE EVENT', event, id, viewMode);

        $scope.moduleId = id;

        $scope.$emit(platformParameters.events.START_REQUEST);
        ChartModuleInit.get({id: id}, function (data) {
            $log.log('ChartModuleInit has been got: ', data);
            if (viewMode == 'view') {
                $scope.subview = 'chart';
            } else if (viewMode == 'edit') {
                $scope.subview = 'configuration';
            } else {
                $scope.subview = 'chart';
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
    });

    $scope.$on('CONFIGURATION_MODE', function (event, id) {
        $log.log('CONFIGURATION_MODE EVENT', event, id)
        $scope.subview = 'configuration';

    });

    $scope.$on('VIEW_MODE', function (event, id) {
        $log.log('VIEW_MODE EVENT', event, id)
        $scope.subview = 'chart';

    });

    $scope.$emit(platformParameters.events.MODULE_READY);
}]);

platformChartModuleControllers.controller('ChartModuleConfigurationWizardController', ['$scope', '$http', '$log', 'ApplicationDatabaseDataSource', 'ChartModuleConfiguration', function ($scope, $http, $log, ApplicationDatabaseDataSource, ChartModuleConfiguration) {
    $log.log('ChartModuleConfigurationWizardController');
    $scope.stepCurrent = 0;
    $scope.stepMax = 3;

    $scope.chartConfiguration = {};

    var getChartConfiguration = function (moduleId) {
        if (moduleId) {
            ChartModuleConfiguration.get({id: moduleId}, function (data) {
                $scope.chartConfiguration = data;
                if ($scope.chartConfiguration.id == null) {
                    $scope.chartConfiguration = {
                        moduleId: $scope.moduleId,
                        dataSetChart: {
                            dataSetSeries: []
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
        $scope.chartTypes = [];

        var chartTypesTmp = [];

        chartTypesTmp.push({
            id: 0,
            type: 'TABLE',
            name: 'Table view',
            description: 'Table view only. Use this module to presents table data without chart visualization'
        });

        chartTypesTmp.push({
            id: 1,
            type: 'LINE',
            name: 'Line chart view',
            description: 'Use this module to represents data as a chart'
        });
        chartTypesTmp.push({
            id: 2,
            type: 'LINE_TABLE',
            name: 'Line chart with table view',
            description: 'Use this module to represents data as a chart'
        });

        chartTypesTmp.push({
            id: 3,
            type: 'CUMULATIVE_LINE',
            name: 'Cumulative line chart view',
            description: 'Use this module to represents data as a chart'
        });
        chartTypesTmp.push({
            id: 4,
            type: 'CUMULATIVE_LINE_TABLE',
            name: 'Cumulative line chart with table view',
            description: 'Use this module to represents data as a chart'
        });

        chartTypesTmp.push({
            id: 5,
            type: 'STACKED_AREA',
            name: 'Stacked area chart view',
            description: 'Use this module to represents data as a chart'
        });

        chartTypesTmp.push({
            id: 6,
            type: 'STACKED_AREA_TABLE',
            name: 'Stacked area chart with table view',
            description: 'Use this module to represents data as a chart'
        });

        chartTypesTmp.push({
            id: 7,
            type: 'MULTI_COLUMN',
            name: 'Multi column chart view',
            description: 'Use this module to represents data as a chart'
        });

        chartTypesTmp.push({
            id: 8,
            type: 'MULTI_COLUMN_TABLE',
            name: 'Multi column chart with table view',
            description: 'Use this module to represents data as a chart'
        });

        chartTypesTmp.push({
            id: 9,
            type: 'DISCRETE_COLUMN',
            name: 'Discrete column chart view',
            description: 'Use this module to represents data as a chart'
        });

        chartTypesTmp.push({
            id: 10,
            type: 'DISCRETE_COLUMN_TABLE',
            name: 'Discrete column chart with table view',
            description: 'Use this module to represents data as a chart'
        });

        chartTypesTmp.push({
            id: 11,
            type: 'HISTORICAL_COLUMN',
            name: 'Historical column chart view',
            description: 'Use this module to represents data as a chart'
        });

        chartTypesTmp.push({
            id: 12,
            type: 'HISTORICAL_COLUMN_TABLE',
            name: 'Historical column chart with table view',
            description: 'Use this module to represents data as a chart'
        });

        chartTypesTmp.push({
            id: 13,
            type: 'MULTI_BAR',
            name: 'Multi bar chart view',
            description: 'Use this module to represents data as a chart'
        });

        chartTypesTmp.push({
            id: 14,
            type: 'MULTI_BAR_TABLE',
            name: 'Multi bar chart with table view',
            description: 'Use this module to represents data as a chart'
        });

        chartTypesTmp.push({
            id: 15,
            type: 'PIE',
            name: 'Pie chart view',
            description: 'Use this module to represents data as a chart'
        });

        chartTypesTmp.push({
            id: 16,
            type: 'PIE_TABLE',
            name: 'Pie chart with table view',
            description: 'Use this module to represents data as a chart'
        });

        chartTypesTmp.push({
            id: 17,
            type: 'SCATTER',
            name: 'Scatter chart view',
            description: 'Use this module to represents data as a chart'
        });

        chartTypesTmp.push({
            id: 18,
            type: 'SCATTER_TABLE',
            name: 'Scatter chart with table view',
            description: 'Use this module to represents data as a chart'
        });

        chartTypesTmp.push({
            id: 19,
            type: 'DONUT',
            name: 'Donut chart view',
            description: 'Use this module to represents data as a chart'
        });

        chartTypesTmp.push({
            id: 20,
            type: 'DONUT_TABLE',
            name: 'Donut chart with table view',
            description: 'Use this module to represents data as a chart'
        });

        $scope.chartTypes = chartTypesTmp;

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

        $scope.addDataSetSeries = function () {
            $log.log('moduleConfigurationWizardStep series', $scope.chartConfiguration.dataSetChart.dataSetSeries);

            if ($scope.chartConfiguration.dataSetChart.dataSetSeries.length == 0) {
                $scope.seriesNumber = 1;
            }

            $scope.chartConfiguration.dataSetChart.dataSetSeries.push({
                id: null,
                name: ('Series ' + $scope.seriesNumber),
                isValid: true,
                seriesColumns: [
                    {
                        type: 'X',
                        name: null
                    },
                    {
                        type: 'Y',
                        name: null
                    }
                ]
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

        $scope.moduleConfigurationWizardStep.getSeriesData = function () {
            $scope.moduleConfigurationWizardStep.chart.seriesPreviewData = [];
            $scope.moduleConfigurationWizardStep.chart.seriesPreviewData.push({x: 1, y: 2});
            $scope.moduleConfigurationWizardStep.chart.seriesPreviewData.push({x: 2, y: 7});
            $scope.moduleConfigurationWizardStep.chart.seriesPreviewData.push({x: 4, y: 2});
            $scope.moduleConfigurationWizardStep.chart.seriesPreviewData.push({x: 5, y: 4});
            $scope.moduleConfigurationWizardStep.chart.seriesPreviewData.push({x: 6, y: 2});
            $scope.moduleConfigurationWizardStep.chart.seriesPreviewData.push({x: 7, y: 22});
            //todo more
        }

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
])
;


platformChartModuleControllers.controller('ChartModuleController', ['$scope', '$http', '$log', 'ChartModuleConfiguration', 'mockupData', function ($scope, $http, $log, ChartModuleConfiguration, mockupData) {
    $log.log('ChartModuleController');

    $log.log('$scope.moduleId: ' + $scope.moduleId);

    $scope.moduleConfiguration = {};

    if ($scope.moduleId) {
        ChartModuleConfiguration.get({id: $scope.moduleId}, function (data) {
            $scope.moduleConfiguration = data;
            $log.log('ChartModuleConfiguration has been got: ', $scope.moduleConfiguration);

            var chartParams = mockupData.getChartData($scope.moduleConfiguration.chartType);

            if (chartParams != null) {
                $scope.options = chartParams.options;
                $scope.data = chartParams.data;
            }
        });
    }

}]);

platformChartModuleControllers.controller('ChartModulePreviewController', ['$scope', '$http', '$log', 'mockupData', 'CharData', function ($scope, $http, $log, mockupData,CharData) {
    $log.log('ChartModulePreviewController');

    $log.log('$scope.moduleId: ' + $scope.moduleId);
    $log.log('$scope.initWizardStep.idSelected: ' + $scope.initWizardStep.idSelected);

    $log.log('CharData.query started ');
    CharData.query({}, $scope.chartConfiguration, function (data) {
        $log.log('CharData.query: ', data);
    });

    var chartParams = mockupData.getChartData($scope.chartConfiguration.chartType);

    if (chartParams != null) {
        $scope.options = chartParams.options;
        $scope.data = chartParams.data;
    }

}
])
;
