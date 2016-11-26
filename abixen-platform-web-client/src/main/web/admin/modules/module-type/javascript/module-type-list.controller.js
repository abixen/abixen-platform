(function () {

    'use strict';

    angular
        .module('platformModuleTypeModule')
        .controller('ModuleTypeListController', ModuleTypeListController);

    ModuleTypeListController.$inject = [
        '$scope',
        '$http',
        '$log',
        'uiGridConstants',
        'ModuleType',
        'gridFilter',
        'applicationNavigationItems',
        '$state',
        'toaster'
    ];

    function ModuleTypeListController($scope, $http, $log, uiGridConstants, ModuleType, gridFilter, applicationNavigationItems, $state, toaster) {
        $log.log('ModuleListController');

        angular.extend(this, new AbstractCrudListController($scope, $http, $log, uiGridConstants, ModuleType, gridFilter));

        $scope.entityListGrid.columnDefs = [
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

        $scope.query = {
            and: [
                {
                    name: 'title',
                    operation: '=',
                    value: 'Module View'
                }

            ]
        };

        $scope.filterCriteria = {
            page: 0,
            size: 20,
            sort: 'id,asc',
            gridFilterParameters: []
        };

        $scope.reload = function () {
            ModuleType.reload({id: $scope.selectedEntityId}, function (data) {
                toaster.pop(platformParameters.statusAlertTypes.SUCCESS, 'Success', 'Module\'s configuration has been reloaded.');
            });
        };

        $scope.reloadAll = function () {
            ModuleType.reloadAll({}, function (data) {
                toaster.pop(platformParameters.statusAlertTypes.SUCCESS, 'Success', 'All services\' configuration has been reloaded.');
            });
        };

        applicationNavigationItems.clearTopbarItems();

        $scope.read();
    }
})();