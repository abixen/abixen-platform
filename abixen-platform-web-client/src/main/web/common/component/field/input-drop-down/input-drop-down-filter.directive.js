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

    inputDropDownFillerDirective.$inject = ['$compile', '$filter', 'platformSecurity'];

    function inputDropDownFillerDirective($compile, $filter, platformSecurity) {

        return {
            restrict: 'A',
            require: 'ngModel',
            scope: {
                options: '=',
                showEmptyValue: '=',
                emptyValueLabel: '=',
                ngModelAsObject: '=',
                valueKey: '=',
                valueLabel: '=',
                keyAsValue: '='
            },
            link: link
        };

        function link(scope, element, attrs, ngModel) {
            var reloadOptions = false;
            if (scope.valueKey === undefined || scope.valueKey === null) {
                scope.valueKey = 'key';
            }

            ngModel.$parsers.push(function (val) {
                if (val === '') {
                    return null;
                }

                var filterMap = {};
                filterMap[scope.valueKey] = val;
                var resultValues = $filter('filter')(scope.options, filterMap);

                if (resultValues.length > 0) {
                    if (scope.ngModelAsObject) {
                        return resultValues[0];
                    } else {
                        return resultValues[0][scope.valueKey];
                    }

                }
                return null;
            });

            ngModel.$formatters.push(function (val) {
                if (val === undefined || val === null) {
                    return '';
                }

                if (scope.ngModelAsObject) {
                    return '' + val[scope.valueKey];
                }

                return '' + val;
            });

            scope.$watch('options', onOptionsChanged);
            scope.$watch(platformSecurity.getPlatformUser, onUserChanged);

            function onUserChanged() {
                if (!reloadOptions) {
                    reloadOptions = true;
                    return;
                }
                onOptionsChanged();
            }

            function onOptionsChanged() {

                var html = '';

                if (scope.showEmptyValue) {
                    html = '<option value="">-- ' + scope.emptyValueLabel + ' --</option>';
                }

                angular.forEach(scope.options, function (option, key) {
                    if (scope.keyAsValue) {
                        html += '<option value="' + option[scope.valueKey] + '">' +
                            option[scope.valueKey] + '</option>';
                    } else {
                        html += '<option value="' + option[scope.valueKey] + '">' +
                            option[scope.valueLabel] + '</option>';
                    }
                });

                element.html(html);
                $compile(element.contents())(scope);
            }
        }
    }

})();