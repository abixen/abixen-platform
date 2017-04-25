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
        .controller('LayoutIconChangeController', LayoutIconChangeController);

    LayoutIconChangeController.$inject = [
        '$state',
        '$stateParams',
        '$http',
        '$log',
        '$cookies',
        'FileUploader'
    ];

    function LayoutIconChangeController($state, $stateParams, $http, $log, $cookies, FileUploader) {
        $log.debug(' LayoutIconChangeController ');

        var layoutIconChange = this;
        layoutIconChange.userBaseUrl = '/api/control-panel/layouts/';
        layoutIconChange.xsrfToken = $cookies.get($http.defaults.xsrfCookieName);
        layoutIconChange.isNewLayout = $stateParams.isNew;

        new AbstractUploaderController(layoutIconChange, FileUploader, layoutIconChange.xsrfToken,
            {
                uploaderUrl: layoutIconChange.userBaseUrl + $stateParams.id + '/icon',
                uploaderAlias: 'iconFile',
                uploaderMethod: 'POST',
                uploaderFunctionConfig: function (controller) {
                    controller.onCompleteAll = onCompleteAll;
                }
            }
        );
        layoutIconChange.cancelAll = cancelAll;

        function onCompleteAll() {
            $state.go('application.layouts.list');
        }

        function cancelAll() {
            if (layoutIconChange.isNewLayout) {
                $state.go('application.layouts.edit,details');
            } else {
                $state.go('application.layouts.list');
            }
        }

    }
})();