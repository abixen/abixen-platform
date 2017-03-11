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
        .module('platformChartModule')
        .controller('MultivisualisationModuleViewTableController', MultivisualisationModuleViewTableController);

    MultivisualisationModuleViewTableController.$inject = [
        '$scope',
        '$log',
        'ChartModuleConfiguration',
        'CharData',
        'dataChartAdapter',
        'moduleResponseErrorHandler'
    ];

    function MultivisualisationModuleViewTableController($scope, $log, ChartModuleConfiguration, CharData, dataChartAdapter, moduleResponseErrorHandler) {
        $log.log('MultivisualisationModuleController');

        $log.log('$scope.moduleId: ' + $scope.moduleId);

        var multivisualisationModuleViewTable = this;
        multivisualisationModuleViewTable.options = undefined;
        multivisualisationModuleViewTable.data = undefined;
        multivisualisationModuleViewTable.renderTable = false;

        if (multivisualisationModuleViewTable.chartConfiguration){
            registerSubviewTableIcons(chartConfiguration.chartType);
        }

        var SHOW_SUBVIEW_CHART_EVENT = 'SHOW_SUBVIEW_CHART_EVENT';

        if ($scope.moduleId) {
            $scope.$emit(platformParameters.events.START_REQUEST);

            ChartModuleConfiguration.get({id: $scope.moduleId})
                .$promise
                .then(onGetResult, onGetError);
        }

        function onGetResult(moduleConfiguration) {
            $log.debug('onGetResult ', moduleConfiguration);
            multivisualisationModuleViewTable.renderTable = true;
            $log.debug('multivisualisationModuleViewTable.renderTable ', multivisualisationModuleViewTable.renderTable);
            angular.extend(multivisualisationModuleViewTable, new MultivisualisationModuleAbstractTableController(multivisualisationModuleViewTable, $log, CharData, moduleResponseErrorHandler, $scope,
                {
                    chartConfiguration: moduleConfiguration
                }
            ));

            registerSubviewTableIcons(moduleConfiguration.chartType);
            $scope.$emit(platformParameters.events.STOP_REQUEST);
        }

        function onGetError(error) {
            moduleResponseErrorHandler.handle(error, $scope);
        }

        function registerSubviewTableIcons(chartType) {
            if (chartType !== 'TABLE') {
                var icons = [
                    {
                        iconClass: 'fa fa-area-chart',
                        event: SHOW_SUBVIEW_CHART_EVENT,
                        title: 'Show chart view'
                    }
                ];
                registerSubviewTableIconsHelper(icons);
            }else {
                registerSubviewTableIconsHelper([]);
            }
        }

        function registerSubviewTableIconsHelper(icons) {
            $scope.$emit(platformParameters.events.REGISTER_MODULE_CONTROL_ICONS, icons);
        }
    }
})();