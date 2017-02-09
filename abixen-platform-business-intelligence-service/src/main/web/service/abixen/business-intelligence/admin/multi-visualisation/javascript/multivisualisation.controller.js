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
        .module('multiVisualisationModule')
        .controller('MultiVisualisationController', MultiVisualisationController);

    MultiVisualisationController.$inject = [
        '$scope',
        '$log',
        '$state'
    ];

    function MultiVisualisationController($scope, $log, $state) {
        $log.log('$state.$current.name: ' + $state.$current.name);

        if ($state.$current.name === 'application.multiVisualisation.modules.databaseDataSource.list' ||
            $state.$current.name === 'application.multiVisualisation.modules.databaseDataSource.edit' ||
            $state.$current.name === 'application.multiVisualisation.modules.databaseDataSource.add') {
            $scope.selectedModule = 'databaseDataSource';
        } else if ($state.$current.name === 'application.multiVisualisation.modules.databaseConnection.list' ||
            $state.$current.name === 'application.multiVisualisation.modules.databaseConnection.edit' ||
            $state.$current.name === 'application.multiVisualisation.modules.databaseConnection.add') {
            $scope.selectedModule = 'databaseConnection';
        } else if ($state.$current.name === 'application.multiVisualisation.modules.fileData.list' ||
            $state.$current.name === 'application.multiVisualisation.modules.fileData.edit' ||
            $state.$current.name === 'application.multiVisualisation.modules.fileData.add') {
            $scope.selectedModule = 'fileData';
        } else if ($state.$current.name === 'application.multiVisualisation.modules.fileDataSource.list' ||
            $state.$current.name === 'application.multiVisualisation.modules.fileDataSource.edit' ||
            $state.$current.name === 'application.multiVisualisation.modules.fileDataSource.add') {
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