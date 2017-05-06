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
        .controller('ConfigurationWizardController', ConfigurationWizardController);

    ConfigurationWizardController.$inject = [
        '$scope',
        '$log',
        'WebContentConfigObject',
        'WebContentConfig',
        'moduleResponseErrorHandler'
    ];

    function ConfigurationWizardController($scope, $log, WebContentConfigObject, WebContentConfig, moduleResponseErrorHandler) {
        $log.log('WebContentConfigurationWizardController');

        var webContentConfigurationWizard = this;

        webContentConfigurationWizard.stepCurrent = 0;
        webContentConfigurationWizard.stepMax = 1;
        webContentConfigurationWizard.moduleId = null;
        webContentConfigurationWizard.prev = prev;
        webContentConfigurationWizard.next = next;
        webContentConfigurationWizard.canNext = canNext;

        function prev() {
            if (webContentConfigurationWizard.stepCurrent > 0) {
                webContentConfigurationWizard.stepCurrent--;
            }
        }

        function next() {
            if (webContentConfigurationWizard.stepCurrent < webContentConfigurationWizard.stepMax) {
                webContentConfigurationWizard.stepCurrent++;
            } else if (webContentConfigurationWizard.stepCurrent === webContentConfigurationWizard.stepMax) {

                WebContentConfigObject.setConfig(WebContentConfigObject.getChangedConfig($scope.moduleId));
                var configuration = WebContentConfigObject.getConfig($scope.moduleId);

                if (configuration.id !== null) {
                    WebContentConfig.update({moduleId: configuration.moduleId}, configuration)
                        .$promise
                        .then(onResult);
                } else {
                    WebContentConfig.save(configuration)
                        .$promise
                        .then(onResult);
                }
            }
            function onResult() {
                WebContentConfigObject.setConfig(WebContentConfigObject.getChangedConfig($scope.moduleId));
                $scope.$emit('VIEW_MODE');
            }
        }

        function canNext() {
            if (webContentConfigurationWizard.stepCurrent < webContentConfigurationWizard.stepMax) {
                return true;
            } else if (webContentConfigurationWizard.stepCurrent === webContentConfigurationWizard.stepMax) {
                return true;
            }
            return false
        }

        function prepareNewObjectForConfig() {
            WebContentConfigObject.setChangedConfig(WebContentConfigObject.getConfig($scope.moduleId));
        }

        prepareNewObjectForConfig()
    }
})();