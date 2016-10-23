var userControllers = angular.module('userControllers', []);

userControllers.controller('UserListController', ['$scope', '$http', '$log', 'uiGridConstants', 'User', 'gridFilter', 'applicationNavigationItems', '$state', function ($scope, $http, $log, uiGridConstants, User, gridFilter, applicationNavigationItems, $state) {
    $log.log('UserListController');

    angular.extend(this, new AbstractCrudListController($scope, $http, $log, uiGridConstants, User, gridFilter));

    $scope.entityListGrid.columnDefs = [
        {field: 'id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
        {field: 'username', pinnedLeft: true, width: 200},
        {field: 'firstName', pinnedLeft: true, width: 200},
        {field: 'lastName', pinnedLeft: true, width: 200},
        {field: 'registrationIp', pinnedLeft: true, width: 200},
        {field: 'state', pinnedLeft: true, width: 200},
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
                value: 'User View'
            }

        ]
    };


    $scope.filterCriteria = {
        user: 0,
        size: 20,
        sort: 'id,asc',
        gridFilterParameters: []
    };

    var newUserButton = {
        id: 1,
        styleClass: 'btn add-new-object-button',
        faIcon: 'fa fa-plus',
        title: 'New User',
        onClick: function () {
            $state.go('application.users.add');
        },
        visible: true,
        disabled: false
    };

    applicationNavigationItems.setTopbarItem(newUserButton);

    $scope.read();
}]);

userControllers.controller('UserDetailController', ['$scope', '$http', '$state', '$stateParams', '$log', 'User', '$parse', function ($scope, $http, $state, $stateParams, $log, User, $parse) {
    $log.log('UserDetailController');

    $scope.userGender = ['MALE', 'FEMALE'];
    angular.extend(this, new AbstractCrudDetailController($scope, $http, $state, $stateParams, $log, User, $parse, 'application.users'));

    $scope.get($stateParams.id);

    $scope.today = function() {
        $scope.entity.birthday = new Date();
    };

    $scope.clear = function () {
        $scope.entity.birthday = null;
    };

    $scope.open = function($event) {
        $scope.status.opened = true;
    };

    $scope.setDate = function(year, month, day) {
        $scope.entity.birthday = new Date(year, month, day);
    };

    $scope.status = {
        opened: false
    };
}]);

userControllers.controller('UserAssignRolesController', ['$scope', '$http', '$state', '$stateParams', '$log', 'UserRole', function ($scope, $http, $state, $stateParams, $log, UserRole) {
    $log.log('UserAssignRolesController');

    $scope.userRole = null;
    $scope.selectedRoleType = null;
    $scope.uniqueRoleTypes = [];

    $scope.get = function (id) {
        $log.log('selected user id:', id);
        if (!id) {
            throw 'User id is required';
        }
        UserRole.get({id: id}, function (data) {
            $scope.userRole = data;
            $log.log('UserRole has been got: ', $scope.userRole);
            getUniqueRoleTypes($scope.userRole.userRoles);
        });
    };

    $scope.saveForm = function () {
        $log.log('update() - userRole: ', $scope.userRole);
        UserRole.update({id: $stateParams.id}, $scope.userRole, function () {
            $log.log('uUserRole has been updated: ', $scope.userRole);
            //$state.go(stateParent + '.list')
        });
    };

    $scope.setSelectedRoleType = function (roleType) {
        $scope.selectedRoleType = roleType;
    };

    var getUniqueRoleTypes = function (userRoles) {
        var uniqueRoleTypes = ['ALL'];
        for (var i = 0; i < userRoles.length; i++) {
            if (uniqueRoleTypes.indexOf(userRoles[i].role.roleType) == -1) {
                uniqueRoleTypes.push(userRoles[i].role.roleType);
            }
        }
        $scope.uniqueRoleTypes = uniqueRoleTypes;
        $log.log('$scope.uniqueRoleTypes: ', $scope.uniqueRoleTypes);

        $scope.selectedRoleType = $scope.uniqueRoleTypes[0];
    };

    $scope.get($stateParams.id);
}]);
