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
                    name: 'Table view',
                    description: 'Table view only. Use this module to presents table data without chart visualization'
                },
                {
                    id: 1,
                    type: 'LINE',
                    name: 'Line chart view',
                    description: 'Use this module to represents data as a chart'
                },
                {
                    id: 2,
                    type: 'LINE_TABLE',
                    name: 'Line chart with table view',
                    description: 'Use this module to represents data as a chart'
                },
                {
                    id: 3,
                    type: 'CUMULATIVE_LINE',
                    name: 'Cumulative line chart view',
                    description: 'Use this module to represents data as a chart'
                },
                {
                    id: 4,
                    type: 'CUMULATIVE_LINE_TABLE',
                    name: 'Cumulative line chart with table view',
                    description: 'Use this module to represents data as a chart'
                },
                {
                    id: 5,
                    type: 'STACKED_AREA',
                    name: 'Stacked area chart view',
                    description: 'Use this module to represents data as a chart'
                },
                {
                    id: 6,
                    type: 'STACKED_AREA_TABLE',
                    name: 'Stacked area chart with table view',
                    description: 'Use this module to represents data as a chart'
                },
                {
                    id: 7,
                    type: 'MULTI_COLUMN',
                    name: 'Multi column chart view',
                    description: 'Use this module to represents data as a chart'
                },
                {
                    id: 8,
                    type: 'MULTI_COLUMN_TABLE',
                    name: 'Multi column chart with table view',
                    description: 'Use this module to represents data as a chart'
                },
                {
                    id: 9,
                    type: 'DISCRETE_COLUMN',
                    name: 'Discrete column chart view',
                    description: 'Use this module to represents data as a chart'
                },
                {
                    id: 10,
                    type: 'DISCRETE_COLUMN_TABLE',
                    name: 'Discrete column chart with table view',
                    description: 'Use this module to represents data as a chart'
                },
                {
                    id: 11,
                    type: 'HISTORICAL_COLUMN',
                    name: 'Historical column chart view',
                    description: 'Use this module to represents data as a chart'
                },
                {
                    id: 12,
                    type: 'HISTORICAL_COLUMN_TABLE',
                    name: 'Historical column chart with table view',
                    description: 'Use this module to represents data as a chart'
                },
                {
                    id: 13,
                    type: 'MULTI_BAR',
                    name: 'Multi bar chart view',
                    description: 'Use this module to represents data as a chart'
                },
                {
                    id: 14,
                    type: 'MULTI_BAR_TABLE',
                    name: 'Multi bar chart with table view',
                    description: 'Use this module to represents data as a chart'
                },
                {
                    id: 15,
                    type: 'PIE',
                    name: 'Pie chart view',
                    description: 'Use this module to represents data as a chart'
                },
                {
                    id: 16,
                    type: 'PIE_TABLE',
                    name: 'Pie chart with table view',
                    description: 'Use this module to represents data as a chart'
                },
                {
                    id: 17,
                    type: 'SCATTER',
                    name: 'Scatter chart view',
                    description: 'Use this module to represents data as a chart'
                },
                {
                    id: 18,
                    type: 'SCATTER_TABLE',
                    name: 'Scatter chart with table view',
                    description: 'Use this module to represents data as a chart'
                },
                {
                    id: 19,
                    type: 'DONUT',
                    name: 'Donut chart view',
                    description: 'Use this module to represents data as a chart'
                },
                {
                    id: 20,
                    type: 'DONUT_TABLE',
                    name: 'Donut chart with table view',
                    description: 'Use this module to represents data as a chart'
                },
                {
                    id: 21,
                    type: 'LINE_WITH_FOCUS_CHART',
                    name: 'Line Chart with focus mode',
                    description: 'Use this module to represents data as a chart'
                },
                {
                    id: 22,
                    type: 'LINE_WITH_FOCUS_CHART_TABLE',
                    name: 'Line Chart with focus mode with table view',
                    description: 'Use this module to represents data as a chart'
                }
            ]
        }
    }
})();