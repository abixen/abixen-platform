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
        .module('platformField')
        .directive('inputDropDownFiller', inputDropDownFillerDirective);

    inputDropDownFillerDirective.$inject = ['$compile'];

    function inputDropDownFillerDirective($compile) {

        return {
            restrict: 'A',
            scope: {
                options: '=',
                showEmptyValue: '=',
                emptyValueLabel: '=',
                keyAsValue: '='
            },
            link: link
        };

        function link(scope, element, attrs) {

            scope.$watch('options', onOptionsChanged);

            function onOptionsChanged(newValue, oldValue) {
                var html = '';

                if (scope.showEmptyValue) {
                    html = '<option value="">-- ' + scope.emptyValueLabel + ' --</option>';
                }

                angular.forEach(newValue, function (option, key) {
                    console.log('option: ', option, scope.keyAsValue);
                    if (scope.keyAsValue) {
                        html += '<option value="' + option.key + '">' +
                            option.key + '</option>';
                    } else {
                        html += '<option value="' + option.key + '">' +
                            option.value + '</option>';
                    }
                });

                element.html(html);
                $compile(element.contents())(scope);
            }
        }
    }

})();