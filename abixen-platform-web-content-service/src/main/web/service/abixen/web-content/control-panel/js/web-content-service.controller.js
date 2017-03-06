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
        .controller('WebContentServiceController', WebContentServiceController);

    WebContentServiceController.$inject = ['$log', '$state', '$scope'];

    function WebContentServiceController($log, $state, $scope) {
        $log.log('$state.$current.name: ' + $state.$current.name);

        var webContentService = this;

        if ($state.$current.name === 'application.webContentService.webContent.list' ) {
            $scope.selectedModule = 'webContentServiceWebContentModule';
        } else if ($state.$current.name === 'application.webContentService.template.list') {
            $scope.selectedModule = 'webContentServiceTemplateModule';
        } else if ($state.$current.name === 'application.webContentService.structure.list') {
            $scope.selectedModule = 'webContentServiceStructureModule';
        }
        $scope.selectWebContentServiceWebContent = function () {
            $scope.selectedModule = 'webContentServiceWebContentModule'
        };
        $scope.selectWebContentServiceTemplate = function () {
            $scope.selectedModule = 'webContentServiceTemplateModule';
        };
        $scope.selectWebContentServiceStructure = function () {
            $scope.selectedModule = 'webContentServiceStructureModule'
        };

    }
})();