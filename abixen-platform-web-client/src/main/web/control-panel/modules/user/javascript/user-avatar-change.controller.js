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
        .controller('UserAvatarChangeController', UserAvatarChangeController);

    UserAvatarChangeController.$inject = [
        '$state',
        '$stateParams',
        '$http',
        '$log',
        '$cookies',
        'FileUploader'
    ];

    function UserAvatarChangeController($state, $stateParams, $http, $log, $cookies, FileUploader) {
        $log.debug(' UserAvatarChangeController ');

        var userAvatarChange = this;

        userAvatarChange.userBaseUrl = '/api/application/users/';
        userAvatarChange.xsrfToken = $cookies.get($http.defaults.xsrfCookieName);
        userAvatarChange.isNewUser = $stateParams.isNewUser;

        new AbstractUploaderController(userAvatarChange, FileUploader, userAvatarChange.xsrfToken,
            {
                uploaderUrl: userAvatarChange.userBaseUrl + $stateParams.id + '/avatar',
                uploaderAlias: 'avatarFile',
                uploaderMethod: 'POST',
                uploaderFunctionConfig: function (controller) {
                    controller.onCompleteAll = onCompleteAll;
                }
            }
        );

        userAvatarChange.cancelAll = cancelAll;

        function onCompleteAll() {
            if (userAvatarChange.isNewUser) {
                $state.go('application.users.list');
            }else {
                $state.go('application.users.edit.details', {id: $stateParams.id});
            }
        }

        function cancelAll() {
            userAvatarChange.uploader.cancelAll();
            if (userAvatarChange.isNewUser) {
                $state.go('application.users.list');
            }else {
                $state.go('application.users.edit.details', {id: $stateParams.id});
            }
        }
    }
})();