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

        angular.extend(moduleTypeList, new AbstractListGridController(ModuleType,
            {
                getTableColumns: getTableColumns
            }
        ));

        moduleTypeList.reload = reload;
        moduleTypeList.reloadAll = reloadAll;

        updateNavigation();


        function getTableColumns() {
            return [
                {field: 'id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
                {field: 'name', pinnedLeft: true, width: 200},
                {field: 'title', pinnedLeft: true, width: 200},
                {field: 'serviceId', pinnedLeft: true, width: 200},
                {field: 'initUrl', width: 400},
                {field: 'description', width: 400},
                {field: 'createdBy.username', name: 'Created By', width: 200},
                {
                    field: 'createdDate',
                    width: 200,
                    cellFilter: "date:'" + platformParameters.formats.DATE_TIME_FORMAT + "'"
                },
                {field: 'lastModifiedBy.username', name: 'Last Modified By', width: 200},
                {
                    field: 'lastModifiedDate',
                    width: 200,
                    cellFilter: "date:'" + platformParameters.formats.DATE_TIME_FORMAT + "'"
                }
            ];
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
    }
})();