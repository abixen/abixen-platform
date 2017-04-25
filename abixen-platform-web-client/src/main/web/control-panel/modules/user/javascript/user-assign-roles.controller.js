/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

(function () {

    'use strict';

    angular
        .module('platformUserModule')
        .controller('UserAssignRolesController', UserAssignRolesController);

    UserAssignRolesController.$inject = [
        '$scope',
        '$stateParams',
        '$log',
        'UserRole',
        'toaster'
    ];

    function UserAssignRolesController($scope, $stateParams, $log, UserRole, toaster) {
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
                toaster.pop(platformParameters.statusAlertTypes.SUCCESS, 'Saved', 'User\'s roles have been updated.');
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