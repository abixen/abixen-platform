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
        .module('platformComponent')
        .directive('confirmField', confirmFieldDirective);

    function confirmFieldDirective() {

        return {
            restrict: 'A',
            require: ['^ngModel', '^form'],
            link: link
        };

        function link(scope, element, attrs, ctrls) {
            var formController = ctrls[1];
            var ngModel = ctrls[0];
            var otherFieldModel = formController[attrs.confirmField];

            if (!otherFieldModel) {
                return;
            }

            function getMatchValue() {
                return otherFieldModel.$viewValue;
            }

            scope.$watch(getMatchValue, function () {
                ngModel.$$parseAndValidate();
            });

            ngModel.$validators.fieldMatch = function (value) {
                return equals(value, otherFieldModel.$viewValue);
            };

            otherFieldModel.$parsers.push(function (value) {
                ngModel.$setValidity('fieldMatch', equals(value, ngModel.$viewValue));
                return value;
            });
        }

        function equals(value1, value2) {
            return !value1 && !value2 || value1 === value2;
        }
    }

})();