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
        .module('multiVisualizationModule')
        .controller('MultiVisualizationController', MultiVisualizationController);

    MultiVisualizationController.$inject = [
        '$scope',
        '$log',
        '$state'
    ];

    function MultiVisualizationController($scope, $log, $state) {
        $log.log('$state.$current.name: ' + $state.$current.name);

        if ($state.$current.name === 'application.multiVisualization.modules.databaseDataSource.list' ||
            $state.$current.name === 'application.multiVisualization.modules.databaseDataSource.edit' ||
            $state.$current.name === 'application.multiVisualization.modules.databaseDataSource.add') {
            $scope.selectedModule = 'databaseDataSource';
        } else if ($state.$current.name === 'application.multiVisualization.modules.databaseConnection.list' ||
            $state.$current.name === 'application.multiVisualization.modules.databaseConnection.edit' ||
            $state.$current.name === 'application.multiVisualization.modules.databaseConnection.add') {
            $scope.selectedModule = 'databaseConnection';
        } else if ($state.$current.name === 'application.multiVisualization.modules.fileData.list' ||
            $state.$current.name === 'application.multiVisualization.modules.fileData.edit' ||
            $state.$current.name === 'application.multiVisualization.modules.fileData.add') {
            $scope.selectedModule = 'fileData';
        } else if ($state.$current.name === 'application.multiVisualization.modules.fileDataSource.list' ||
            $state.$current.name === 'application.multiVisualization.modules.fileDataSource.edit' ||
            $state.$current.name === 'application.multiVisualization.modules.fileDataSource.add') {
            $scope.selectedModule = 'fileDataSource';
        }
        $scope.selectDatabaseDataSources = function () {
            $scope.selectedModule = 'databaseDataSource'
        };
        $scope.selectDatabaseConnections = function () {
            $scope.selectedModule = 'databaseConnection';
        };
        $scope.selectFileDataSources = function () {
            $scope.selectedModule = 'fileDataSource'
        };
        $scope.selectFileData = function () {
            $scope.selectedModule = 'fileData'
        };
    }
})();