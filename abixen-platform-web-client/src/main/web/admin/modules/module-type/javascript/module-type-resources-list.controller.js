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
        .controller('ModuleTypeResourcesListController', ModuleTypeResourcesListController);

    ModuleTypeResourcesListController.$inject = [
        '$log',
        '$stateParams',
        'ModuleTypeResourcesFactory',
        'applicationNavigationItems',
        'toaster'
    ];

    function ModuleTypeResourcesListController($log, $stateParams, ModuleTypeResourcesFactory, applicationNavigationItems, toaster) {
        $log.log('ModuleTypeResourcesListController: id --> ' + $stateParams.id);

        var moduleTypeResourcesList = this;

        angular.extend(moduleTypeResourcesList, new AbstractListGridController(ModuleTypeResourcesFactory,
            {
                getTableColumns: getTableColumns,
                filter: {
                    moduleId: $stateParams.id
                }
            }
        ));

        moduleTypeResourcesList.reload = reload;
        moduleTypeResourcesList.reloadAll = reloadAll;

        updateNavigation();


        function getTableColumns() {
            return [
                {field: 'id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
                {field: 'resourceType', pinnedLeft: true, width: 200},
                {field: 'resourcePage', pinnedLeft: true, width: 200},
                {field: 'resourcePageLocation', pinnedLeft: true, width: 200},
                {field: 'relativeUrl', width: 400}
            ];
        }

        function reload() {
            ModuleTypeResourcesFactory.reload({id: moduleTypeResourcesList.listGridConfig.selectedEntity.id})
                .$promise
                .then(onReload);

            function onReload() {
                toaster.pop(platformParameters.statusAlertTypes.SUCCESS, 'Success', 'Module\'s configuration has been reloaded.');
            }
        }

        function reloadAll() {
            ModuleTypeResourcesFactory.reloadAll({})
                .$promise
                .then(onReloadAll);

            function onReloadAll() {
                toaster.pop(platformParameters.statusAlertTypes.SUCCESS, 'Success', 'All services\' configuration has been reloaded.');
            }
        }

        function updateNavigation() {
            applicationNavigationItems.clearTopbarItems();
        }
    }
})();