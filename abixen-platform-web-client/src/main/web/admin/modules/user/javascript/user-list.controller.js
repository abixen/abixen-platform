(function () {

    'use strict';

    angular
        .module('platformUserModule')
        .controller('UserListController', UserListController);

    UserListController.$inject = [
        '$log',
        'User',
        'applicationNavigationItems',
        '$state'
    ];

    function UserListController($log, User, applicationNavigationItems, $state) {
        $log.log('UserListController');

        var userList = this;

        angular.extend(userList, new AbstractListGridController(User,
            {
                getTableColumns: getTableColumns
            }
        ));

        updateNavigation();


        function getTableColumns() {
            return [
                {field: 'id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
                {field: 'username', pinnedLeft: true, width: 200},
                {field: 'firstName', pinnedLeft: true, width: 200},
                {field: 'lastName', pinnedLeft: true, width: 200},
                {field: 'state', pinnedLeft: true, width: 100},
                {field: 'registrationIp', width: 200},
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

        function updateNavigation() {
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
        }
    }
})();