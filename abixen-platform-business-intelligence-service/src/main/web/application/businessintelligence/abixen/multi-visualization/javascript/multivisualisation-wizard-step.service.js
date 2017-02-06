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
        .service('multivisualisationWizardStep', multivisualisationWizardStep);

    function multivisualisationWizardStep() {
        this.getChartTypes = getChartTypes;

        function getChartTypes() {

            return [
                {
                    id: 0,
                    type: 'TABLE',
                    name: 'module.multivisualisation.configuration.chartType.table.name',
                    description: 'module.multivisualisation.configuration.chartType.table.description'
                },
                {
                    id: 1,
                    type: 'LINE',
                    name: 'module.multivisualisation.configuration.chartType.lineChart.name',
                    description: 'module.multivisualisation.configuration.chartType.lineChart.description'
                },
                {
                    id: 2,
                    type: 'LINE_TABLE',
                    name: 'module.multivisualisation.configuration.chartType.lineChartWithTable.name',
                    description: 'module.multivisualisation.configuration.chartType.lineChartWithTable.description'
                },
                {
                    id: 3,
                    type: 'CUMULATIVE_LINE',
                    name: 'module.multivisualisation.configuration.chartType.cumalativeLineChart.name',
                    description: 'module.multivisualisation.configuration.chartType.cumalativeLineChart.description'
                },
                {
                    id: 4,
                    type: 'CUMULATIVE_LINE_TABLE',
                    name: 'module.multivisualisation.configuration.chartType.cumalativeLineChartWithTable.name',
                    description: 'module.multivisualisation.configuration.chartType.cumalativeLineChartWithTable.description'
                },
                {
                    id: 5,
                    type: 'STACKED_AREA',
                    name: 'module.multivisualisation.configuration.chartType.stackedAreaChart.name',
                    description: 'module.multivisualisation.configuration.chartType.stackedAreaChart.description'
                },
                {
                    id: 6,
                    type: 'STACKED_AREA_TABLE',
                    name: 'module.multivisualisation.configuration.chartType.stackedAreaChartWithTable.name',
                    description: 'module.multivisualisation.configuration.chartType.stackedAreaChartWithTable.description'
                },
                {
                    id: 7,
                    type: 'MULTI_COLUMN',
                    name: 'module.multivisualisation.configuration.chartType.multiColumnChart.name',
                    description: 'module.multivisualisation.configuration.chartType.multiColumnChart.description'
                },
                {
                    id: 8,
                    type: 'MULTI_COLUMN_TABLE',
                    name: 'module.multivisualisation.configuration.chartType.multiColumnChartWithTable.name',
                    description: 'module.multivisualisation.configuration.chartType.multiColumnChartWithTable.description'
                },
                {
                    id: 9,
                    type: 'DISCRETE_COLUMN',
                    name: 'module.multivisualisation.configuration.chartType.discreteColumnChart.name',
                    description: 'module.multivisualisation.configuration.chartType.discreteColumnChart.description'
                },
                {
                    id: 10,
                    type: 'DISCRETE_COLUMN_TABLE',
                    name: 'module.multivisualisation.configuration.chartType.discreteColumnChartWithTable.name',
                    description: 'module.multivisualisation.configuration.chartType.discreteColumnChartWithTable.description'
                },
                {
                    id: 11,
                    type: 'HISTORICAL_COLUMN',
                    name: 'module.multivisualisation.configuration.chartType.historicalColumnChart.name',
                    description: 'module.multivisualisation.configuration.chartType.historicalColumnChart.description'
                },
                {
                    id: 12,
                    type: 'HISTORICAL_COLUMN_TABLE',
                    name: 'module.multivisualisation.configuration.chartType.historicalColumnChartWithTable.name',
                    description: 'module.multivisualisation.configuration.chartType.historicalColumnChartWithTable.description'
                },
                {
                    id: 13,
                    type: 'MULTI_BAR',
                    name: 'module.multivisualisation.configuration.chartType.multiBarChart.name',
                    description: 'module.multivisualisation.configuration.chartType.multiBarChart.description'
                },
                {
                    id: 14,
                    type: 'MULTI_BAR_TABLE',
                    name: 'module.multivisualisation.configuration.chartType.multiBarChartWithTable.name',
                    description: 'module.multivisualisation.configuration.chartType.multiBarChartWithTable.description'
                },
                {
                    id: 15,
                    type: 'PIE',
                    name: 'module.multivisualisation.configuration.chartType.pieChart.name',
                    description: 'module.multivisualisation.configuration.chartType.pieChart.description'
                },
                {
                    id: 16,
                    type: 'PIE_TABLE',
                    name: 'module.multivisualisation.configuration.chartType.pieChartWithTable.name',
                    description: 'module.multivisualisation.configuration.chartType.pieChartWithTable.description'
                },
                {
                    id: 17,
                    type: 'SCATTER',
                    name: 'module.multivisualisation.configuration.chartType.scatterChart.name',
                    description: 'module.multivisualisation.configuration.chartType.scatterChart.description'
                },
                {
                    id: 18,
                    type: 'SCATTER_TABLE',
                    name: 'module.multivisualisation.configuration.chartType.scatterChartWithTable.name',
                    description: 'module.multivisualisation.configuration.chartType.scatterChartWithTable.description'
                },
                {
                    id: 19,
                    type: 'DONUT',
                    name: 'module.multivisualisation.configuration.chartType.donutChart.name',
                    description: 'module.multivisualisation.configuration.chartType.donutChart.description'
                },
                {
                    id: 20,
                    type: 'DONUT_TABLE',
                    name: 'module.multivisualisation.configuration.chartType.donutChartWithTable.name',
                    description: 'module.multivisualisation.configuration.chartType.donutChartWithTable.description'
                },
                {
                    id: 21,
                    type: 'LINE_WITH_FOCUS_CHART',
                    name: 'module.multivisualisation.configuration.chartType.lineChartWithFocus.name',
                    description: 'module.multivisualisation.configuration.chartType.lineChartWithFocus.description'
                },
                {
                    id: 22,
                    type: 'LINE_WITH_FOCUS_CHART_TABLE',
                    name: 'module.multivisualisation.configuration.chartType.lineChartWithFocusAndTable.name',
                    description: 'module.multivisualisation.configuration.chartType.lineChartWithFocusAndTable.description'
                },
                {
                    id: 23,
                    type: 'LINE_AND_BAR_WITH_FOCUS_CHART',
                    name: 'module.multivisualisation.configuration.chartType.lineAndBarChartWithFocus.name',
                    description: 'module.multivisualisation.configuration.chartType.lineAndBarChartWithFocus.description'
                },
                {
                    id: 24,
                    type: 'LINE_AND_BAR_WITH_FOCUS_CHART_Table',
                    name: 'module.multivisualisation.configuration.chartType.llineAndBarChartWithFocusAndTable.name',
                    description: 'module.multivisualisation.configuration.chartType.llineAndBarChartWithFocusAndTable.description'
                }
            ]
        }
    }
})();