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
        .service('multivisualisationDisplayingDataRules', multivisualisationDisplayingDataRules);

    function multivisualisationDisplayingDataRules() {
        this.isTableViewAvailable = isTableViewAvailable;
        this.isChartViewAvailable = isChartViewAvailable;

        function isTableViewAvailable(chartType) {
            switch (chartType) {
                case 'LINE_TABLE':
                    return true;
                case 'PIE_TABLE':
                    return true;
                case 'MULTI_BAR_TABLE':
                    return true;
                case 'MULTI_COLUMN_TABLE':
                    return true;
                case 'STACKED_AREA_TABLE':
                    return true;
                case 'DONUT_TABLE':
                    return true;
                case 'DISCRETE_COLUMN_TABLE':
                    return true;
                case 'HISTORICAL_COLUMN_TABLE':
                    return true;
                case 'CUMULATIVE_LINE_TABLE':
                    return true;
                case 'SCATTER_TABLE':
                    return true;
                case 'LINE_WITH_FOCUS_CHART_TABLE':
                    return true;
                case 'LINE_AND_BAR_WITH_FOCUS_CHART_TABLE':
                    return true;
                default:
                    return false;
            }
        }

        function isChartViewAvailable(chartType) {
            if (chartType !== 'TABLE') {
                return true;
            }
            return false;
        }
    }
})();