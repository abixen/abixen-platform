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
        .controller('ContentSelectionStepController', ContentSelectionStepController);

    ContentSelectionStepController.$inject = [
        '$scope',
        '$log',
        'WebContent',
        'WebContentConfig',
        'moduleResponseErrorHandler'
    ];

    function ContentSelectionStepController($scope, $log, WebContent, WebContentConfig, moduleResponseErrorHandler) {
        $log.log('ContentSelectionStepController');

        var contentSelectionStep = this;

        contentSelectionStep.searchFields = createSearchFields();
        contentSelectionStep.searchFilter = {};
        contentSelectionStep.webContentConfig = WebContentConfig.getChangedConfig($scope.moduleId);


        angular.extend(contentSelectionStep, new AbstractListGridController(WebContent,
            {
                getTableColumns: getTableColumns,
                filter: contentSelectionStep.searchFilter,
                onRowSelected: onSelectRow,
                onGetDataResult: onGetDataResult
            }
        ));

        function onSelectRow(row) {
            if (row && contentSelectionStep.webContentConfig) {
                contentSelectionStep.webContentConfig.contentId = row.entity.id;
                WebContentConfig.setChangedConfig(contentSelectionStep.webContentConfig);
            }
        }

        function getTableColumns() {
            return [
                {field: 'id', name: 'Id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
                {field: 'title', name: 'Title', pinnedLeft: true}
            ];
        }

        function createSearchFields() {
            return [
                {
                    name: 'title',
                    label: 'wizard.contentSelectionStep.search.title.label',
                    type: 'input-text'
                }
            ];
        }

        function selectRow(contentId){
            contentSelectionStep.listGridConfig.getListGridData().forEach(function(row){
                if (row.id === contentId) {
                    contentSelectionStep.listGridConfig.selectRow(row);
                }
            });
        }
        function onGetDataResult() {
            selectRow(contentSelectionStep.webContentConfig.contentId);
        }
    }
})();