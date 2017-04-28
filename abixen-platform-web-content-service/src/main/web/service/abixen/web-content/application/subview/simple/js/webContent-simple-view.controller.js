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
        .module('webContentSimpleModule')
        .controller('WebContentSimpleViewController', WebContentSimpleViewController);

    WebContentSimpleViewController.$inject = [
        '$scope',
        '$log',
        '$sce',
        'WebContentView',
        'WebContentConfig',
        'moduleResponseErrorHandler'
    ];

    function WebContentSimpleViewController($scope, $log, $sce, WebContentView, WebContentConfig, moduleResponseErrorHandler) {
        $log.log('PreviewSimpleController');

        var webContentSimpleView = this;

        webContentSimpleView.entity = {};


        function getSimpleWebContent(id) {
            WebContentView.get({id:id})
                .$promise
                .then(onGetResult);
        }

        function onGetResult(webContent) {
            if (webContent){
                webContentSimpleView.entity = webContent;
                webContentSimpleView.entity.content = $sce.trustAsHtml(webContentSimpleView.entity.content);
            }
            else {
                webContentSimpleView.entity = {};
            }
        }

        getSimpleWebContent(WebContentConfig.getConfig($scope.moduleId).contentId)


    }
})();