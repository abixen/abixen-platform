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
        .module('platformThemeModule')
        .controller('ThemeAddController', ThemeAddController);

    ThemeAddController.$inject = [
        '$state',
        '$stateParams',
        '$http',
        '$log',
        '$cookies',
        'FileUploader'
    ];

    function ThemeAddController($state, $stateParams, $http, $log, $cookies, FileUploader) {
        $log.debug(' ThemeAddController ');

        var themeAdd = this;

        themeAdd.themeBaseUrl = '/api/admin/themes/';
        themeAdd.xsrfToken = $cookies.get($http.defaults.xsrfCookieName);

        new AbstractUploaderController(themeAdd, FileUploader, themeAdd.xsrfToken,
            {
                uploaderUrl: themeAdd.themeBaseUrl + '/upload',
                uploaderAlias: 'themeFile',
                uploaderMethod: 'POST',
                uploaderFunctionConfig: function (controller) {
                    controller.onCompleteAll = onCompleteAll;
                }
            }
        );

        themeAdd.cancelAll = cancelAll;

        function onCompleteAll() {
            $state.go('application.themes.list');
        }

        function cancelAll() {
            themeAdd.uploader.cancelAll();
            $state.go('application.themes.list');
        }
    }
})();