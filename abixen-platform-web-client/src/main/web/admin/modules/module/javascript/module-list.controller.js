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
        .module('platformModuleModule')
        .controller('ModuleListController', ModuleListController);

    ModuleListController.$inject = [
        '$log',
        'Module',
        'applicationNavigationItems'
    ];

    function ModuleListController($log, Module, applicationNavigationItems) {
        $log.log('ModuleListController');

        var moduleList = this;

        moduleList.searchFields = createSearchFields();
        moduleList.searchFilter = {};

        angular.extend(moduleList, new AbstractListGridController(Module,
            {
                getTableColumns: getTableColumns,
                filter: moduleList.searchFilter
            }
        ));

        updateNavigation();


        function getTableColumns() {
            return [
                {field: 'id', name: 'Id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
                {field: 'title', name: 'Title', pinnedLeft: true, width: 200},
                {field: 'description', name: 'Description', pinnedLeft: true, width: 200},
                {field: 'moduleType.title', name: 'Module type', pinnedLeft: true, width: 200}
            ].concat(getAuditingTableColumns());
        }

        function updateNavigation() {
            applicationNavigationItems.clearTopbarItems();
        }

        function createSearchFields() {
            return [
                {
                    name: 'title',
                    label: 'module.module.title.label',
                    type: 'input-text'
                },
                {
                    name: 'description',
                    label: 'module.module.description.label',
                    type: 'input-text'
                }
            ];
        }
    }
})();