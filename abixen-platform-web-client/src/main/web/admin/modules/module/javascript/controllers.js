var moduleControllers = angular.module('moduleControllers', []);

moduleControllers.controller('ModuleListController', ['$scope', '$http', '$log', 'uiGridConstants', 'Module', 'gridFilter', 'applicationNavigationItems', '$state', function ($scope, $http, $log, uiGridConstants, Module, gridFilter, applicationNavigationItems, $state) {
    $log.log('ModuleListController');

    angular.extend(this, new AbstractCrudListController($scope, $http, $log, uiGridConstants, Module, gridFilter));

    $scope.entityListGrid.columnDefs = [
        {field: 'id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
        {field: 'title', pinnedLeft: true, width: 200},
        {field: 'description', pinnedLeft: true, width: 200},
        {field: 'moduleType.title', pinnedLeft: true, width: 200},
        {field: 'createdBy.username', name: 'Created By', width: 200},
        {field: 'createdDate', width: 200, cellFilter: "date:'" + platformParameters.formats.DATE_TIME_FORMAT + "'"},
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

    applicationNavigationItems.clearTopbarItems();

    $scope.read();
}]);

moduleControllers.controller('ModuleDetailController', ['$scope', '$http', '$state', '$parse', '$stateParams', '$log', 'Module', 'Layout', function ($scope, $http, $state, $parse, $stateParams, $log, Module, Layout) {
    $log.log('ModuleDetailController');

    angular.extend(this, new AbstractCrudDetailController($scope, $http, $state, $stateParams, $log, Module, $parse, 'application.modules'));

    $scope.layouts = [];

    var readLayouts = function () {
        var queryParameters = {
            page: 0,
            size: 20,
            sort: 'id,asc'
        };

        Layout.query(queryParameters, function (data) {
            $scope.layouts = data.content;
        });
    };

    readLayouts();

    $scope.get($stateParams.id);
}]);

moduleControllers.controller('ModulePermissionsController', ['$scope', '$stateParams', '$log', '$state', 'AclRolesPermissions', function ($scope, $stateParams, $log, $state, AclRolesPermissions) {
    $log.log('ModulePermissionsController');

    angular.extend(this, new AbstractPermissionsController($scope, $stateParams, $log, $state, AclRolesPermissions, 'module', $stateParams.id, 'application.modules'));

    $scope.get();
}]);
