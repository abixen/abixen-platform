var layoutControllers = angular.module('layoutControllers', []);

layoutControllers.controller('LayoutListController', ['$scope', '$http', '$log', 'uiGridConstants', 'Layout', 'gridFilter', 'applicationNavigationItems', '$state', function ($scope, $http, $log, uiGridConstants, Layout, gridFilter, applicationNavigationItems, $state) {
    $log.log('LayoutListController');

    angular.extend(this, new AbstractCrudListController($scope, $http, $log, uiGridConstants, Layout, gridFilter));

    $scope.entityListGrid.columnDefs = [
        {field: 'id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
        {field: 'title', pinnedLeft: true, width: 200},
        {field: 'createdBy.layoutname', name: 'Created By', width: 200},
        {field: 'createdDate', width: 200, cellFilter: "date:'" + platformParameters.formats.DATE_TIME_FORMAT + "'"},
        {field: 'lastModifiedBy.layoutname', name: 'Last Modified By', width: 200},
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
                value: 'Layout View'
            }

        ]
    };


    $scope.filterCriteria = {
        layout: 0,
        size: 20,
        sort: 'id,asc',
        gridFilterParameters: []
    };

    var newLayoutButton = {
        id: 1,
        styleClass: 'btn add-new-object-button',
        title: 'New Layout',
        faIcon: 'fa fa-plus',
        onClick: function () {
            $state.go('application.layouts.add');
        },
        visible: true,
        disabled: false
    };

    applicationNavigationItems.setTopbarItem(newLayoutButton);

    $scope.read();
}]);

layoutControllers.controller('LayoutDetailController', ['$scope', '$http', '$state', '$stateParams', '$log', 'Layout', function ($scope, $http, $state, $stateParams, $log, Layout) {
    $log.log('LayoutDetailController');

    angular.extend(this, new AbstractCrudDetailController($scope, $http, $state, $stateParams, $log, Layout, 'application.layouts'));

    $scope.get($stateParams.id);
}]);

layoutControllers.controller('LayoutPermissionsController', ['$scope', '$stateParams', '$log', '$state', 'AclRolesPermissions', function ($scope, $stateParams, $log, $state, AclRolesPermissions) {
    $log.log('LayoutPermissionsController');

    angular.extend(this, new AbstractPermissionsController($scope, $stateParams, $log, $state, AclRolesPermissions, 'layout', $stateParams.id, 'application.layouts'));

    $scope.get();
}]);
