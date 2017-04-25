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
        .controller('RoleDetailsController', RoleDetailsController);

    RoleDetailsController.$inject = [
        '$scope',
        '$state',
        '$stateParams',
        '$log',
        'Role',
        'RoleDictionary',
        'responseHandler'
    ];

    function RoleDetailsController($scope, $state, $stateParams, $log, Role, RoleDictionary, responseHandler) {
        $log.log('RoleDetailsController');

        var roleDetails = this;
        roleDetails.roles = [];


        new AbstractDetailsController(roleDetails, Role, responseHandler, $scope,
            {
                entityId: $stateParams.id,
                getValidators: getValidators,
                onSuccessSaveForm: onSuccessSaveForm
            }
        );
        function onSuccessSaveForm() {
            $state.go('application.roles.list');
        }

        function getRoles() {
            RoleDictionary.query(function (data) {
                var roles = [];
                data.forEach(function (element) {
                    $log.debug("element: ",element);
                    roles.push({key: element})
                });
                roleDetails.roles = roles;
            });
        }

        function getValidators() {
            var validators = [];
            validators['name'] =
                [
                    new NotNull(),
                    new Length(1, 300)
                ];
            validators['roleType'] =
                [
                    new NotNull()
                ];

            return validators;

        }
        getRoles();
    }
})();