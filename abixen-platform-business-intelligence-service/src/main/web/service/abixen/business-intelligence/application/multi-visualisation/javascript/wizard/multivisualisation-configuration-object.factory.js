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
        .factory('MultiVisualisationConfigObject', MultiVisualisationConfigObject);

    MultiVisualisationConfigObject.$inject = ['$resource'];

    function MultiVisualisationConfigObject() {
        var multiVisualisationConfigList = {};
        var multiVisualisationChangedConfigList = {};


        return {
            setConfig: setConfig,
            setChangedConfig: setChangedConfig,
            getConfig: getConfig,
            getChangedConfig: getChangedConfig,
            clearConfig: clearConfig,
            clearChangedConfig: clearChangedConfig,
            getDefaultConfig: getDefaultConfig
        };

        function setConfig(config) {
            multiVisualisationConfigList[config.moduleId] = angular.copy(config);
        }

        function setChangedConfig(config) {
            multiVisualisationChangedConfigList[config.moduleId] = angular.copy(config);
        }

        function getConfig(moduleId) {
            return multiVisualisationConfigList[moduleId];
        }

        function getChangedConfig(moduleId) {
            return multiVisualisationChangedConfigList[moduleId];
        }

        function clearConfig(moduleId) {
            multiVisualisationConfigList[moduleId] = {};
        }

        function clearChangedConfig(moduleId) {
            multiVisualisationChangedConfigList[moduleId] = {};
        }

        function getDefaultConfig() {
            return {
                moduleId: null,
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

    }
})();