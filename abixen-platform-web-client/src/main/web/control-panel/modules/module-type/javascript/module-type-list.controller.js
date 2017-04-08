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
        .module('platformModuleTypeModule')
        .controller('ModuleTypeListController', ModuleTypeListController);

    ModuleTypeListController.$inject = [
        '$log',
        'ModuleType',
        'applicationNavigationItems',
        'toaster'
    ];

    function ModuleTypeListController($log, ModuleType, applicationNavigationItems, toaster) {
        $log.log('ModuleTypeListController');

        var moduleTypeList = this;

        moduleTypeList.searchFields = createSearchFields();
        moduleTypeList.searchFilter = {};

        angular.extend(moduleTypeList, new AbstractListGridController(ModuleType,
            {
                getTableColumns: getTableColumns,
                filter: moduleTypeList.searchFilter
            }
        ));

        moduleTypeList.reload = reload;
        moduleTypeList.reloadAll = reloadAll;

        updateNavigation();


        function getTableColumns() {
            return [
                {field: 'id', name: 'Id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
                {field: 'name', name: 'Name', width: 200},
                {field: 'title', name: 'Title', width: 200},
                {field: 'serviceId', name: 'Service Id', width: 400},
                {field: 'initUrl', name: 'Init Url', width: 400},
                {field: 'description', name: 'Description', width: 400}
            ].concat(getAuditingTableColumns());
        }

        function reload() {
            ModuleType.reload({id: moduleTypeList.listGridConfig.selectedEntity.id})
                .$promise
                .then(onReload);

            function onReload() {
                toaster.pop(platformParameters.statusAlertTypes.SUCCESS, 'Success', 'Module\'s configuration has been reloaded.');
            }
        }

        function reloadAll() {
            ModuleType.reloadAll({})
                .$promise
                .then(onReloadAll);

            function onReloadAll() {
                toaster.pop(platformParameters.statusAlertTypes.SUCCESS, 'Success', 'All services\' configuration has been reloaded.');
            }
        }

        function updateNavigation() {
            applicationNavigationItems.clearTopbarItems();
        }

        function createSearchFields() {
            return [
                {
                    name: 'name',
                    label: 'module.module-type.name.label',
                    type: 'input-text'
                },
                {
                    name: 'title',
                    label: 'module.module-type.title.label',
                    type: 'input-text'
                },
                {
                    name: 'serviceId',
                    label: 'module.module-type.serviceId.label',
                    type: 'input-text'
                }
            ];
        }
    }
})();