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
        .controller('UserDetailsController', UserDetailsController);

    UserDetailsController.$inject = [
        '$scope',
        '$state',
        '$stateParams',
        '$log',
        'User',
        'responseHandler',
        'toaster'
    ];

    function UserDetailsController($scope, $state, $stateParams, $log, User, responseHandler, toaster) {
        $log.log('UserDetailController');

        var userDetails = this;

        new AbstractDetailsController(userDetails, User, responseHandler, $scope,
            {
                entityId: $stateParams.id,
                getValidators: getValidators,
                onSuccessSaveForm: onSuccessSaveForm,
                onSuccessGetEntity: onSuccessGetEntity
            }
        );

        userDetails.languageTypes = [
            {key: 'ENGLISH'},
            {key: 'POLISH'},
            {key: 'RUSSIAN'},
            {key: 'SPANISH'},
            {key: 'UKRAINIAN'}
        ];
        userDetails.genderTypes = [{key: 'MALE'}, {key: 'FEMALE'}];
        userDetails.userBaseUrl = "/api/application/users/";
        userDetails.avatarUrl = userDetails.userBaseUrl + ' ';
        userDetails.isNewUser = true;

        userDetails.today = today;
        userDetails.clear = clear;


        function clear() {
            $scope.entity.birthday = null;
        }

        function today() {
            $scope.entity.birthday = new Date();
        }

        function onSuccessSaveForm() {
            if (!userDetails.isNewUser) {
                $state.go('application.users.list');
            }else {
                toaster.pop(platformParameters.statusAlertTypes.SUCCESS, 'Created', 'New user has been created. Now you can upload his avatar.');
                $state.go('application.users.edit.avatar', {id:userDetails.entity.id, isNewUser: true});
            }
        }

        function onSuccessGetEntity() {
            userDetails.isNewUser = userDetails.entity.id == undefined || userDetails.entity.id == null;
            userDetails.avatarUrl = userDetails.userBaseUrl + userDetails.entity.id + '/avatar/' + userDetails.entity.avatarFileName
        }

        function getValidators() {
            var validators = [];

            validators['screenName'] =
                [
                    new NotNull(),
                    new Length(3, 32)
                ];

            validators['username'] =
                [
                    new NotNull(),
                    new Length(3, 32),
                    new Email()
                ];

            validators['firstName'] =
                [
                    new NotNull(),
                    new Length(2, 64)
                ];

            validators['middleName'] =
                [
                    new Length(2, 64)
                ];

            validators['lastName'] =
                [
                    new NotNull(),
                    new Length(2, 64)
                ];

            validators['jobTitle'] =
                [
                    new Length(2, 64)
                ];
            validators['selectedLanguage'] =
                [
                    new NotNull()
                ];

            return validators;
        }
    }
})();