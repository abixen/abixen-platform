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
        .module('platformRoleModule')
        .controller('RoleDefinePermissionsController', RoleDefinePermissionsController);

    RoleDefinePermissionsController.$inject = [
        '$http',
        '$state',
        '$stateParams',
        '$log',
        'RolePermission'
    ];

    function RoleDefinePermissionsController($http, $state, $stateParams, $log, RolePermission) {
        $log.log('RoleDetailController');

        var roleDefinePermissions = this;

        roleDefinePermissions.rolePermission = null;
        roleDefinePermissions.selectedPermissionCategory = null;
        roleDefinePermissions.uniquePermissionCategories = [];

        roleDefinePermissions.get = get;
        roleDefinePermissions.saveForm = saveForm;
        roleDefinePermissions.setSelectedPermissionCategory = setSelectedPermissionCategory;

        roleDefinePermissions.get($stateParams.id);


        function get(id) {
            $log.log('selected role id:', id);
            if (!id) {
                throw 'Role id is required';
            }
            RolePermission.get({id: id}, function (data) {
                roleDefinePermissions.rolePermission = data;
                $log.log('RolePermission has been got: ', roleDefinePermissions.rolePermission);
                getUniquePermissionCategories(roleDefinePermissions.rolePermission.rolePermissions);
            });
        }

        function saveForm() {
            $log.log('update() - rolePermission: ', roleDefinePermissions.rolePermission);
            RolePermission.update({id: $stateParams.id}, roleDefinePermissions.rolePermission, function () {
                $log.log('RolePermission has been updated: ', roleDefinePermissions.rolePermission);
                $state.go('application.roles.list')
            });
        }

        function setSelectedPermissionCategory(permissionCategory) {
            roleDefinePermissions.selectedPermissionCategory = permissionCategory;
        }

        function getUniquePermissionCategories(rolePermissions) {
            var uniquePermissionCategories = ['ALL'];
            for (var i = 0; i < rolePermissions.length; i++) {
                if (uniquePermissionCategories.indexOf(rolePermissions[i].permission.permissionAclClassCategory.title) == -1) {
                    uniquePermissionCategories.push(rolePermissions[i].permission.permissionAclClassCategory.title);
                }
            }
            roleDefinePermissions.uniquePermissionCategories = uniquePermissionCategories;
            $log.log('roleDefinePermissions.uniquePermissionCategories: ', roleDefinePermissions.uniquePermissionCategories);

            roleDefinePermissions.selectedPermissionCategory = roleDefinePermissions.uniquePermissionCategories[0];
        }
    }
})();