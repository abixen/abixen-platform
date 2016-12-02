(function () {

    'use strict';

    angular
        .module('platformUserModule')
        .controller('UserListController', UserListController);

    UserListController.$inject = [
        '$scope',
        '$http',
        '$log',
        'uiGridConstants',
        'User',
        'gridFilter',
        'applicationNavigationItems',
        '$state'
    ];

    function UserListController($scope, $http, $log, uiGridConstants, User, gridFilter, applicationNavigationItems, $state) {
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
    }
})();