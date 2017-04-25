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
        .directive('inputNumber', inputNumber);

    inputNumber.$inject = ['validation'];
    function inputNumber(validation) {

        return {
            restrict: 'E',
            require: '^form',
            templateUrl: 'common/component/field/input-number/input-number.template.html',
            scope: {
                model: '=',
                validators: '=',
                label: '@',
                name: '@',
                size: '@'
            },
            link: link,
            controller: InputTextController,
            controllerAs: 'inputNumber',
            bindToController: true
        };

        function link(scope, element, attrs, formCtrl) {
            scope.form = formCtrl;
            removeServerErrorAfterChange(scope, element);
        }

        function removeServerErrorAfterChange(scope, element) {
            element.on('keydown', function () {
                var fieldName = element.attr('name');
                validation.setValid(scope.form[fieldName], 'serverMessage');
            });
        }
    }

    InputTextController.$inject = ['fieldSize'];

    function InputTextController(fieldSize) {
        var inputNumber = this;

        initValidators();
        initResponsiveClasses();

        function initValidators() {
            if (!inputNumber.validators) {
                return;
            }

            inputNumber.fieldValidators = {};
            angular.forEach(inputNumber.validators[inputNumber.name], function (validator) {
                angular.extend(inputNumber.fieldValidators, validator);
            });
        }

        function initResponsiveClasses() {
            inputNumber.responsiveClasses = fieldSize.getClasses(inputNumber.size);
        }
    }
})();