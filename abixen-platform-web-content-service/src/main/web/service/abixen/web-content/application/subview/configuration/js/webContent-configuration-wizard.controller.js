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
        'moduleResponseErrorHandler'
    ];

    function ConfigurationWizardController($scope, $log, moduleResponseErrorHandler) {
        $log.log('WebContentConfigurationWizardController');

        var webContentConfigurationWizard = this;

        webContentConfigurationWizard.stepCurrent = 0;
        webContentConfigurationWizard.stepMax = 1;
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
            }
            
        }
        
        function canNext() {
            if (webContentConfigurationWizard.stepCurrent < webContentConfigurationWizard.stepMax) {
                return true;
            }
            return false
        }
    }
})();