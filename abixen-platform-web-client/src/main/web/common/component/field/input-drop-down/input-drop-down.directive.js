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
        .directive('inputDropDown', inputDropDown);

    inputDropDown.$inject = ['validation'];
    function inputDropDown(validation) {

        return {
            restrict: 'E',
            require: '^form',
            templateUrl: 'common/component/field/input-drop-down/input-drop-down.template.html',
            scope: {
                ngModel: '=',
                ngModelAsObject: '=',
                validators: '=',
                label: '@',
                name: '@',
                size: '@',
                options: '=',
                showEmptyValue: '=',
                valueKey: '@',
                valueLabel: '@',
                emptyValueLabel: '@',
                valueKeyType: '@',
                keyAsValue: '=',
                onChange: '&',
                onChangeSkipInit: '='
            },
            link: link,
            controller: InputDropDownController,
            controllerAs: 'inputDropDown',
            bindToController: true
        };

        function link(scope, element, attrs, formCtrl) {
            if (scope.inputDropDown.onChange) {
                var skipped = scope.inputDropDown.onChangeSkipInit === false || scope.inputDropDown.onChangeSkipInit === undefined ||  scope.inputDropDown.onChangeSkipInit === null;
                scope.$watch('inputDropDown.ngModel', function () {
                    if (skipped) {
                        scope.inputDropDown.onChange();
                    } else {
                        skipped = true;
                    }
                });
            }

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

    InputDropDownController.$inject = ['fieldSize', '$filter'];

    function InputDropDownController(fieldSize, $filter) {
        var inputDropDown = this;

        initValidators();
        initResponsiveClasses();


        function initValidators() {
            if (!inputDropDown.validators) {
                return;
            }

            inputDropDown.fieldValidators = {};
            angular.forEach(inputDropDown.validators[inputDropDown.name], function (validator) {
                angular.extend(inputDropDown.fieldValidators, validator);
            });
        }

        function initResponsiveClasses() {
            inputDropDown.responsiveClasses = fieldSize.getClasses(inputDropDown.size);
        }
    }
})();