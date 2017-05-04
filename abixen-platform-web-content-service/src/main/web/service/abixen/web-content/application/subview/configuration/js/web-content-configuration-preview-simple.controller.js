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
        .module('webContentConfigurationModule')
        .controller('PreviewSimpleController', PreviewSimpleController);

    PreviewSimpleController.$inject = [
        '$scope',
        '$log',
        '$sce',
        'WebContent',
        'WebContentConfig',
        'moduleResponseErrorHandler'
    ];

    function PreviewSimpleController($scope, $log, $sce, WebContent, WebContentConfig, moduleResponseErrorHandler) {
        $log.log('PreviewSimpleController');

        var previewSimple = this;

        previewSimple.entity = {};
        previewSimple.webContentConfig = WebContentConfig.getChangedConfig($scope.moduleId);

        function getSimplePreview(id) {
            WebContent.get({id:id})
                .$promise
                .then(onGetResult);
        }

        function onGetResult(webContent) {
            if (webContent){
                previewSimple.entity = webContent;
                previewSimple.entity.content = $sce.trustAsHtml(previewSimple.entity.content);
            }
            else {
                previewSimple.entity = {};
            }
        }
        getSimplePreview(previewSimple.webContentConfig.contentId)
    }
})();