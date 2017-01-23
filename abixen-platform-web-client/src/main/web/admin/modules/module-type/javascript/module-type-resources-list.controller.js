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
        'ModuleTypeResource',
        'applicationNavigationItems',
        'toaster'
    ];

    function ModuleTypeResourcesListController($log, $stateParams, ModuleTypeResource, applicationNavigationItems, toaster) {
        $log.log('ModuleTypeResourcesListController - id: ' + $stateParams.id);

        var moduleTypeResourcesList = this;

        angular.extend(moduleTypeResourcesList, new AbstractListGridController(ModuleTypeResource,
            {
                getTableColumns: getTableColumns,
                filter: {
                    moduleId: $stateParams.id
                }
            }
        ));

        updateNavigation();


        function getTableColumns() {
            return [
                {field: 'id', name: 'Id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
                {field: 'resourceType', name: 'Resource Type', pinnedLeft: true, width: 200},
                {field: 'resourcePage', name: 'Resource Page', pinnedLeft: true, width: 200},
                {field: 'resourcePageLocation', name: 'Resource Page Location', width: 200},
                {field: 'relativeUrl', name: 'Relative Url', width: 400}
            ];
        }

        function updateNavigation() {
            applicationNavigationItems.clearTopbarItems();
        }
    }
})();