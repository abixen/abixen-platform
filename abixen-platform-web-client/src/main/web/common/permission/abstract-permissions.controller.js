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

'use strict';

function AbstractPermissionsController(extendedController, $state, AclRolesPermissions, permissionAclClassCategoryName, objectId, stateParent, skipPermissions) {
    var abstractDetailsController = extendedController;

    abstractDetailsController.aclRolesPermissionsDto = null;

    abstractDetailsController.saveForm = saveForm;
    abstractDetailsController.goBack = goBack;

    getAclRolesPermissions();

    function getAclRolesPermissions() {
        AclRolesPermissions.get({
            objectId: objectId,
            permissionAclClassCategoryName: permissionAclClassCategoryName
        })
            .$promise
            .then(onGetResult);

        function onGetResult(data) {
            abstractDetailsController.aclRolesPermissionsDto = data;

            if (skipPermissions) {
                var permissions = [];
                for (var i = 0; i < abstractDetailsController.aclRolesPermissionsDto.permissions.length; i++) {
                    if (skipPermissions.indexOf(abstractDetailsController.aclRolesPermissionsDto.permissions[i].permissionName) === -1) {
                        permissions.push(abstractDetailsController.aclRolesPermissionsDto.permissions[i]);
                    }
                }
                abstractDetailsController.aclRolesPermissionsDto.permissions = permissions;

                for (var i = 0; i < abstractDetailsController.aclRolesPermissionsDto.aclRolePermissionsDtos.length; i++) {
                    var aclRolesPermissionsDtos = abstractDetailsController.aclRolesPermissionsDto.aclRolePermissionsDtos[i];
                    var aclPermissionDtosTmp = [];

                    for (var j = 0; j < aclRolesPermissionsDtos.aclPermissionDtos.length; j++) {
                        if (skipPermissions.indexOf(aclRolesPermissionsDtos.aclPermissionDtos[j].permission.permissionName) === -1) {
                            aclPermissionDtosTmp.push(aclRolesPermissionsDtos.aclPermissionDtos[j]);
                        }
                    }
                    abstractDetailsController.aclRolesPermissionsDto.aclRolePermissionsDtos[i].aclPermissionDtos = aclPermissionDtosTmp;
                }
            }
        }
    }

    function saveForm() {

        AclRolesPermissions.update({
            objectId: objectId,
            permissionAclClassCategoryName: permissionAclClassCategoryName
        }, abstractDetailsController.aclRolesPermissionsDto)
            .$promise
            .then(onUpdate);

        function onUpdate() {
            goBack();
        }
    }

    function goBack() {
        $state.go(stateParent + '.list')
    }
}