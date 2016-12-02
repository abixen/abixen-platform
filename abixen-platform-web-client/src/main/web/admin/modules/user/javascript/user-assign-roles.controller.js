(function () {

    'use strict';

    angular
        .module('platformUserModule')
        .controller('UserAssignRolesController', UserAssignRolesController);

    UserAssignRolesController.$inject = [
        '$scope',
        '$http',
        '$state',
        '$stateParams',
        '$log',
        'UserRole'
    ];

    function UserAssignRolesController($scope, $http, $state, $stateParams, $log, UserRole) {
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
    }
})();