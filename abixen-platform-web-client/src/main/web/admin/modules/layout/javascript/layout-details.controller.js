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
        '$cookies',
        'responseHandler'
    ];

    function LayoutDetailsController($scope, $http, $state, $stateParams, $log, Layout, FileUploader, $cookies, responseHandler) {
        $log.log('LayoutDetailsController');

        var layoutDetails = this;

        new AbstractDetailsController(layoutDetails, Layout, responseHandler, $scope,
            {
                entityId: $stateParams.id,
                getValidators: getValidators,
                onSuccessSaveForm: onSuccessSaveForm
            }
        );
        function getValidators() {
            var validators = [];
            validators['title'] =
                [
                    new NotNull(),
                    new Length(6, 40)
                ];
            validators['content'] =
                [
                    new NotNull(),
                    new Length(10, 4000)
                ];
            validators['iconFileName'] =
                [
                    new NotNull(),
                    new Length(6, 100)
                ];

            return validators;
        }

        function onSuccessSaveForm() {
            $state.go('application.layouts.list');
        }

        layoutDetails.isUploadIcon = false;
        layoutDetails.isUploadLayoutIcon = function () {
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
            if (layoutDetails.isUploadIcon) {
                layoutDetails.isUploadIcon = false;
                layoutIconChange.uploader.clearQueue();
                getLayoutIcon($stateParams.id);
            }
        }

        function cancelAll() {
            layoutIconChange.uploader.cancelAll();
            layoutDetails.isUploadIcon = false;
        }

        layoutDetails.showUploadLayoutIcon = function () {
            layoutDetails.isUploadIcon = true;
        };

        function getLayoutIcon(id) {
            if (id) {
                Layout.get({id: id}, function (data) {
                    layoutDetails.entity.iconFileName = data.iconFileName;
                });
            } else {
                layoutDetails.entity.iconFileName = '';
            }
        }

        angular.element(document).ready(function () {
            layoutDetails.editor = CodeMirror.fromTextArea(document.getElementById("contentInput"), {
                lineNumbers: true,
                lineWrapping: true,
                mode: "text/html",
                matchBrackets: true,
                theme: 'default'
            });
        });

        $scope.$watch('layoutDetails.entity.content', function () {
            if (layoutDetails.entity.content != null) {
                layoutDetails.editor.getDoc().setValue(layoutDetails.entity.content);
            }
        });

    }
})();