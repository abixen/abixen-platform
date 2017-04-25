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
        .directive('inputTextArea', inputTextArea);

    inputTextArea.$inject = ['$parse', 'validation'];
    function inputTextArea($parse, validation) {

        return {
            restrict: 'E',
            require: '^form',
            templateUrl: 'common/component/field/input-text-area/input-text-area.template.html',
            scope: {
                model: '=',
                validators: '=',
                label: '@',
                placeholder: '@',
                name: '@',
                size: '@',
                rows: '@'
            },
            link: link,
            controller: InputTextAreaController,
            controllerAs: 'inputTextArea',
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

    InputTextAreaController.$inject = ['fieldSize'];

    function InputTextAreaController(fieldSize) {
        var inputTextArea = this;

        initValidators();
        initResponsiveClasses();

        function initValidators() {
            if (!inputTextArea.validators) {
                return;
            }

            inputTextArea.fieldValidators = {};
            angular.forEach(inputTextArea.validators[inputTextArea.name], function (validator) {
                angular.extend(inputTextArea.fieldValidators, validator);
            });
        }

        function initResponsiveClasses() {
            inputTextArea.responsiveClasses = fieldSize.getClasses(inputTextArea.size);
        }
    }
})();