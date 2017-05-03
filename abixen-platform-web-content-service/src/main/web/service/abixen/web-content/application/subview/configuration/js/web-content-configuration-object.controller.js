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
        .module('webContentConfigurationModule')
        .factory('WebContentConfig', WebContentConfig);

    WebContentConfig.$inject = ['$resource'];

    function WebContentConfig() {
        var webContentConfigList = {};
        var webContentChangedConfigList = {};


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
            webContentConfigList[config.moduleId] = angular.copy(config);
        }

        function setChangedConfig(config) {
            webContentChangedConfigList[config.moduleId] = angular.copy(config);
        }

        function getConfig(moduleId) {
            return webContentConfigList[moduleId];
        }

        function getChangedConfig(moduleId) {
            return webContentChangedConfigList[moduleId];
        }

        function clearConfig(moduleId) {
            webContentConfigList[moduleId] = {};
        }

        function clearChangedConfig(moduleId) {
            webContentChangedConfigList[moduleId] = {};
        }

        function getDefaultConfig() {
            return {
                moduleId: null,
                contentId: null
            }
        }

    }
})();
