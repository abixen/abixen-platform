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
        .module('platformUploadFileModule')
        .directive('platformUploadFile', platformUploadFileDirective);

    platformUploadFileDirective.$inject = ['$log'];

    function platformUploadFileDirective($log) {
        return {
            scope: {
                onUpload: '&'
            },
            link: link
        };

        function link(scope, element) {
            $log.debug('platformUploadFileDirective');

            var el = angular.element(element);
            var button = el.children()[0];

            el.css({
                position: 'relative',
                overflow: 'hidden',
                width: button.offsetWidth,
                height: button.offsetHeight
            });

            var fileInput = angular.element('<input type="file" />')
            fileInput.css({
                position: 'absolute',
                top: 0,
                left: 0,
                'z-index': '2',
                width: '100%',
                height: '100%',
                opacity: '0',
                cursor: 'pointer'
            });

            fileInput.on('change', function (changeEvent) {
                if (scope.onUpload){
                    console.log("file", changeEvent.target.files[0]);
                    scope.onUpload()(changeEvent.target.files[0]);
                }
            });

            el.append(fileInput)
        }
    }
})();