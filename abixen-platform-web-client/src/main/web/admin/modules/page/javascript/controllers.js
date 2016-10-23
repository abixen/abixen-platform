var pageControllers = angular.module('pageControllers', []);

pageControllers.controller('PageListController', ['$scope', '$http', '$log', 'uiGridConstants', 'Page', 'gridFilter', 'applicationNavigationItems', '$state', function ($scope, $http, $log, uiGridConstants, Page, gridFilter, applicationNavigationItems, $state) {
    $log.log('PageListController');

    angular.extend(this, new AbstractCrudListController($scope, $http, $log, uiGridConstants, Page, gridFilter));

    $scope.entityListGrid.columnDefs = [
        {field: 'id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
        {field: 'name', pinnedLeft: true, width: 200},
        {field: 'title', pinnedLeft: true, width: 200},
        {field: 'description', pinnedLeft: true, width: 200},
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
                value: 'Page View'
            }

        ]
    };

    $scope.filterCriteria = {
        page: 0,
        size: 20,
        sort: 'id,asc',
        gridFilterParameters: []
    };

    var addPageButton = {
        id: 1,
        styleClass: 'btn add-new-object-button',
        faIcon: 'fa fa-plus',
        title: 'Add Page',
        onClick: function () {
            $state.go('application.pages.add');
        },
        visible: true,
        disabled: false
    };

    applicationNavigationItems.setTopbarItem(addPageButton);

    $scope.read();
}]);

pageControllers.controller('PageDetailController', ['$scope', '$http', '$state', '$parse', '$stateParams', '$log', 'Page', 'Layout', function ($scope, $http, $state, $parse, $stateParams, $log, Page, Layout) {
    $log.log('PageDetailController');

    angular.extend(this, new AbstractCrudDetailController($scope, $http, $state, $stateParams, $log, Page, $parse, 'application.pages'));

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

pageControllers.controller('PagePermissionsController', ['$scope', '$stateParams', '$log', '$state', 'AclRolesPermissions', function ($scope, $stateParams, $log, $state, AclRolesPermissions) {
    $log.log('PagePermissionsController');

    angular.extend(this, new AbstractPermissionsController($scope, $stateParams, $log, $state, AclRolesPermissions, 'page', $stateParams.id, 'application.pages'));

    $scope.get();
}]);
