var roleControllers = angular.module('roleControllers', []);

roleControllers.controller('RoleListController', ['$scope', '$http', '$log', 'uiGridConstants', 'Role', 'gridFilter', 'applicationNavigationItems', '$state', function ($scope, $http, $log, uiGridConstants, Role, gridFilter, applicationNavigationItems, $state) {
    $log.log('RoleListController');

    angular.extend(this, new AbstractCrudListController($scope, $http, $log, uiGridConstants, Role, gridFilter));

    $scope.entityListGrid.columnDefs = [
        {field: 'id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
        {field: 'name', pinnedLeft: true, width: 200},
        {field: 'roleType', pinnedLeft: true, width: 200},
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
            },
            {
                name: 'description',
                operation: '=',
                value: 'Allows to view a page.'
            },
            {
                name: 'permissionCategory',
                operation: '=',
                value: 'PAGE'
            }
        ]
    };


    $scope.filterCriteria = {
        page: 0,
        size: 20,
        sort: 'id,asc',
        gridFilterParameters: [
            {
                name: 'permissionName',
                operation: '=',
                value: 'PAGE_VIEW'
            },
            {
                name: 'permissionCategory',
                operation: '=',
                value: null
            },
            {
                name: 'title',
                operation: '=',
                value: 'aaa'
            },
            {
                name: 'description',
                operation: '=',
                value: null
            }
        ]
    };

    var newRoleButton = {
        id: 1,
        styleClass: 'btn add-new-object-button',
        faIcon: 'fa fa-plus',
        title: 'New Role',
        onClick: function () {
            $state.go('application.roles.add');
        },
        visible: true,
        disabled: false
    };

    applicationNavigationItems.setTopbarItem(newRoleButton);

    $scope.read();
}]);

roleControllers.controller('RoleDetailController', ['$scope', '$http', '$state', '$stateParams', '$log', '$parse', 'Role', function ($scope, $http, $state, $stateParams, $log, $parse, Role) {
    $log.log('RoleDetailController');

    angular.extend(this, new AbstractCrudDetailController($scope, $http, $state, $stateParams, $log, Role, $parse, 'application.roles'));

    $scope.get($stateParams.id);
}]);

roleControllers.controller('RoleDefinePermissionsController', ['$scope', '$http', '$state', '$stateParams', '$log', 'RolePermission', function ($scope, $http, $state, $stateParams, $log, RolePermission) {
    $log.log('RoleDetailController');

    $scope.rolePermission = null;
    $scope.selectedPermissionCategory = null;
    $scope.uniquePermissionCategories = [];

    $scope.get = function (id) {
        $log.log('selected role id:', id);
        if (!id) {
            throw 'Role id is required';
        }
        RolePermission.get({id: id}, function (data) {
            $scope.rolePermission = data;
            $log.log('RolePermission has been got: ', $scope.rolePermission);
            getUniquePermissionCategories($scope.rolePermission.rolePermissions);
        });
    };

    $scope.saveForm = function () {
        $log.log('update() - rolePermission: ', $scope.rolePermission);
        RolePermission.update({id: $stateParams.id}, $scope.rolePermission, function () {
            $log.log('RolePermission has been updated: ', $scope.rolePermission);
            $state.go('application.roles.list')
        });
    };

    $scope.setSelectedPermissionCategory = function (permissionCategory) {
        $scope.selectedPermissionCategory = permissionCategory;
    }

    var getUniquePermissionCategories = function (rolePermissions) {
        var uniquePermissionCategories = ['ALL'];
        for (var i = 0; i < rolePermissions.length; i++) {
            if (uniquePermissionCategories.indexOf(rolePermissions[i].permission.permissionAclClassCategory.title) == -1) {
                uniquePermissionCategories.push(rolePermissions[i].permission.permissionAclClassCategory.title);
            }
        }
        $scope.uniquePermissionCategories = uniquePermissionCategories;
        $log.log('$scope.uniquePermissionCategories: ', $scope.uniquePermissionCategories);

        $scope.selectedPermissionCategory = $scope.uniquePermissionCategories[0];
    }

    $scope.get($stateParams.id);
}]);
