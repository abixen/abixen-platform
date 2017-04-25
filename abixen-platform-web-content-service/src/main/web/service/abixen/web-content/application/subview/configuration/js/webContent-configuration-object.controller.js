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
        var webContentConfig = {};


        return {
            setConfig: setConfig,
            getConfig: getConfig,
            clearConfig: clearConfig,
            getDefaultConfig: getDefaultConfig
        };

        function setConfig(config) {
            webContentConfig = config;
        }

        function getConfig() {
            return webContentConfig;
        }

        function clearConfig() {
            webContentConfig = {};
        }

        function getDefaultConfig() {
            return {
                moduleId: null,
                contentId: null
            }
        }

    }
})();
