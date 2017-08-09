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
        .controller('WizardDatasourceController', WizardDatasourceController);

    WizardDatasourceController.$inject = [
        '$scope',
        '$log',
        'MultiVisualisationConfigObject',
        'ApplicationDataSource'
    ];

    function WizardDatasourceController($scope, $log, MultiVisualisationConfigObject, ApplicationDataSource) {

        var wizardDatasource = this;
        wizardDatasource.setDataSourceSelected = setDataSourceSelected;
        wizardDatasource.chartConfiguration = MultiVisualisationConfigObject.getChangedConfig($scope.moduleId);


        dataSourceWizardStepStepSelected();

        function getDataSources() {
            $scope.$emit(platformParameters.events.START_REQUEST);
            var queryParameters = {
                dataSourceType: null,
                page: 0,
                size: 10000,
                sort: 'id,asc'
            };

            ApplicationDataSource.query(queryParameters)
                .$promise
                .then(onQueryResult, onQueryError);

            function onQueryResult(data) {
                wizardDatasource.dataSources = data.content;
                $scope.$emit(platformParameters.events.STOP_REQUEST);
            }

            function onQueryError() {
                $scope.$emit(platformParameters.events.STOP_REQUEST);
            }
        }

        function setDataSourceSelected(dataSource) {
            dataSource.classType = dataSource.dataSourceType;
            wizardDatasource.chartConfiguration.dataSource = dataSource;
        }

        function dataSourceWizardStepStepSelected() {
            if (wizardDatasource.dataSources == null) {
                getDataSources();
            }
        }

        $scope.$on("$destroy", function() {
            MultiVisualisationConfigObject.setChangedConfig(wizardDatasource.chartConfiguration)
        });



    }
})();