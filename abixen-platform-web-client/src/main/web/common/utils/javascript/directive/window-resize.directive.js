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
        .module('platformUtilsModule')
        .directive('windowResize', windowResizeDirective);

    windowResizeDirective.$inject = [
        '$window',
        '$timeout'
    ];

    function windowResizeDirective($window, $timeout) {

        return {
            link: link,
            scope: {
                header: '@header',
                body: '@body',
                footer: '@footer'
            }
        };

        function link(scope, element, attrs) {

            var CONTENT_HEIGHT_OFFSET = 175; // modal-header + modal-footer + a little of spare whitespace extra

            scope.height = $window.innerHeight;

            scope.redraw = function ($event) {
                var size = {
                    width: window.innerWidth || document.body.clientWidth,
                    height: window.innerHeight || document.body.clientHeight
                };
                var documentWrapper = angular.element(document);
                var wrapperHeight = angular.element(documentWrapper.find('form-container'));

                var offsetHeight = 0;
                for (var i = 0; i < wrapperHeight.length; i++) {
                    offsetHeight += wrapperHeight[i].offsetHeight;
                }
                var contentHeightComputed = (size.height - offsetHeight - CONTENT_HEIGHT_OFFSET);
                angular.element(element[0]).css('height', contentHeightComputed + 'px');
            };
            scope.redraw();

            $timeout(function () {
                scope.redraw();
            }, 500);

            angular.element($window).bind('resize', function () {
                scope.redraw();
            });
        }
    }

})();