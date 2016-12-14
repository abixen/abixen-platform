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
        .module('platformLayoutModule', [
            'ui.codemirror',
            'angularFileUpload',
            'ngCookies'
        ])
        .controller('LayoutDetailsController', LayoutDetailsController);

    LayoutDetailsController.$inject = [
        '$scope',
        '$http',
        '$state',
        '$stateParams',
        '$log',
        'Layout',
        'FileUploader',
        '$cookies'
    ];

    function LayoutDetailsController($scope, $http, $state, $stateParams, $log, Layout, FileUploader, $cookies) {
        $log.log('LayoutDetailsController');

        $scope.isUploadIcon = false;
        angular.extend(this, new AbstractCrudDetailController($scope, $http, $state, $stateParams, $log, Layout, 'application.layouts'));
        $scope.editorOptions = {
            lineWrapping: true,
            lineNumbers: true,
            readOnly: 'nocursor',
            mode: 'xml',
            theme: 'theme'
        };
        $scope.editor = CodeMirror.fromTextArea(document.getElementById("xmlInput"), {
            lineNumbers: true,
            mode: "text/html",
            matchBrackets: true
        });
        $scope.get($stateParams.id);

        $scope.$watch('entity', function () {

            if ($scope.entity != null) {
                $scope.editor.getDoc().setValue($scope.entity.content);
                if ($scope.entity.iconFileName === null) {
                    entity.iconFileName = 'default-layout-icon.png';
                }
            }
        });

        $scope.isUploadLayoutIcon = function () {
            return $state.current.name === 'application.layouts.edit'
        };

        var layoutIconChange = $scope.layoutIconChange = this;
        layoutIconChange.userBaseUrl = '/api/admin/layouts/';
        layoutIconChange.xsrfToken = $cookies.get($http.defaults.xsrfCookieName);

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
            if ($scope.isUploadIcon) {
                $scope.isUploadIcon = false;
                layoutIconChange.uploader.clearQueue();
                $scope.get($stateParams.id);
            }
        }

        function cancelAll() {
            layoutIconChange.uploader.cancelAll();
            $scope.isUploadIcon = false;
        }

        $scope.showUploadLayoutIcon = function () {
            $scope.isUploadIcon = true;
        };

    }
})();