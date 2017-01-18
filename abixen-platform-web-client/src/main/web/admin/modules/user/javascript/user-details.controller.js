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
        '$http',
        '$state',
        '$stateParams',
        '$log',
        'User',
        '$parse',
        'responseHandler'
    ];

    function UserDetailsController($scope, $http, $state, $stateParams, $log, User, $parse, responseHandler) {
        $log.log('UserDetailController');

        var userDetails = this;

        new AbstractDetailsController(userDetails, User, responseHandler, $scope,
            {
                entityId: $stateParams.id,
                getValidators: getValidators,
                onSuccessSaveForm: onSuccessSaveForm
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
        userDetails.avatarUrl = '';

        userDetails.today = today;
        userDetails.clear = clear;

        getUserAvatarUrl($stateParams.id);

        function clear() {
            $scope.entity.birthday = null;
        }

        function today() {
            $scope.entity.birthday = new Date();
        }

        function onSuccessSaveForm() {
            $state.go('application.users.list');
        }

        function getUserAvatarUrl(id) {
            if (userDetails.avatarUrl === '') {
                if (id) {
                    User.get({id: id}, function (data) {
                        userDetails.avatarUrl = userDetails.userBaseUrl + id + '/avatar/' + data.avatarFileName;
                    });
                } else {
                    userDetails.avatarUrl = '';
                }
            }
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
                    new NotNull(),
                ];

            return validators;
        }
    }
})();