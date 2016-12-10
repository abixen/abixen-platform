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
        .module('platformThumbModule')
        .directive('platformThumb', platformThumbDirective);


    platformThumbDirective.$inject = ['$window', '$log'];

    function platformThumbDirective($window, $log) {
        return {
            restrict: 'A',
            template: '<canvas/>',
            link: link
        };


        function link(scope, element, attributes) {
            var helper = {
                support: !!($window.FileReader && $window.CanvasRenderingContext2D),
                isFile: function (item) {
                    return angular.isObject(item) && item instanceof $window.File;
                },
                isImage: function (file) {
                    var type = '|' + file.type.slice(file.type.lastIndexOf('/') + 1) + '|';
                    return '|jpg|png|jpeg|'.indexOf(type) !== -1;
                }
            };

            $log.debug('platformThumbDirective');
            if (!helper.support) return;

            var params = scope.$eval(attributes.platformThumb);

            if (!helper.isFile(params.file)) return;
            if (!helper.isImage(params.file)) return;

            var canvas = element.find('canvas');
            var reader = new FileReader();

            reader.onload = onLoadFile;
            reader.readAsDataURL(params.file);

            function onLoadFile(event) {
                var img = new Image();
                img.onload = onLoadImage;
                img.src = event.target.result;
            }

            function onLoadImage() {
                var width = params.width || this.width / this.height * params.height;
                var height = params.height || this.height / this.width * params.width;
                canvas.attr({width: width, height: height});
                canvas[0].getContext('2d').drawImage(this, 0, 0, width, height);
            }
        }

    }
})();