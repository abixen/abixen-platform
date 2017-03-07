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

function AbstractPermissionsController(extendedController, $state, AclRolesPermissions, permissionAclClassCategoryName, objectId, stateParent) {
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