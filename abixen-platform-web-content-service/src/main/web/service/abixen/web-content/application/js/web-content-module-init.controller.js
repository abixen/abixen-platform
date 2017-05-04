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
        .module('webContentService')
        .controller('WebContentModuleInitController', WebContentModuleInitController);

    WebContentModuleInitController.$inject = [
        '$scope',
        '$log',
        'WebContentConfig',
        'WebContentConfigData',
        'WebContent',
        'moduleResponseErrorHandler'
    ];

    function WebContentModuleInitController($scope, $log, WebContentConfig, WebContentConfigData, WebContent, moduleResponseErrorHandler) {
        $log.log('WebContentModuleInitController');

        var webContentModuleInit = this;

        var SUBVIEW_CONFIGURATION = 'configuration';
        var SUBVIEW_SIMPLE = 'simple';
        var SUBVIEW_ADVANCED = 'advanced';


        $scope.$on(platformParameters.events.RELOAD_MODULE, function (event, id, viewMode) {
            $log.log('RELOAD MODULE EVENT', id, viewMode);

            $scope.moduleId = id;

            WebContentConfigData.get({moduleId:id})
                .$promise
                .then(onGetResult);

            function onGetResult(webContentConfigData) {
                if (webContentConfigData.moduleId) {
                    WebContentConfig.setConfig(webContentConfigData);
                    WebContent.get({id:webContentConfigData.contentId})
                        .$promise
                        .then(onGetResult);
                } else {
                    $scope.$emit(platformParameters.events.MODULE_CONFIGURATION_MISSING);
                    var webContentConfig = WebContentConfig.getDefaultConfig();
                    webContentConfig.moduleId = id;
                    WebContentConfig.setConfig(webContentConfig)
                }

                function onGetResult(webContent) {
                    setView(webContent.type);
                }
            }

        });

        function setView(type){
            if (type == 'ADVANCED') {
                webContentModuleInit.subview = SUBVIEW_ADVANCED;
            }
            if(type == 'SIMPLE') {
                webContentModuleInit.subview = SUBVIEW_SIMPLE;
            }
        }

        $scope.$on('CONFIGURATION_MODE', function (event, id) {
            $log.log('CONFIGURATION_MODE EVENT', event, id);
            webContentModuleInit.subview = SUBVIEW_CONFIGURATION;
            $scope.$emit(platformParameters.events.CONFIGURATION_MODE_READY);
        });

        $scope.$on('VIEW_MODE', function (event, id) {
            $log.log('VIEW_MODE EVENT', event, id);
            WebContentConfig.clearChangedConfig(id);
            webContentModuleInit.subview = SUBVIEW_SIMPLE;
            $scope.$emit(platformParameters.events.VIEW_MODE_READY);
        });

        $scope.$emit(platformParameters.events.MODULE_READY);
    }
})();