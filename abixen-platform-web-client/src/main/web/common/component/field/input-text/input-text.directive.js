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
        .directive('inputText', inputText);

    inputText.$inject = ['$parse', 'validation'];
    function inputText($parse, validation) {

        return {
            restrict: 'E',
            require: '^form',
            templateUrl: '/common/component/field/input-text/input-drop-down.template.html',
            scope: {
                model: '=',
                validators: '=',
                type: '@',
                label: '@',
                placeholder: '@',
                name: '@',
                size: '@'
            },
            link: link,
            controller: InputTextController,
            controllerAs: 'inputText',
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
        var DEFAULT_TYPE = 'text';
        var inputText = this;

        initValidators();
        initType();
        initResponsiveClasses();

        function initValidators() {
            if (!inputText.validators) {
                return;
            }

            inputText.fieldValidators = {};
            angular.forEach(inputText.validators[inputText.name], function (validator) {
                angular.extend(inputText.fieldValidators, validator);
            });
        }

        function initType() {
            if (inputText.type === undefined) {
                inputText.type = DEFAULT_TYPE;
            }
        }

        function initResponsiveClasses() {
            inputText.responsiveClasses = fieldSize.getClasses(inputText.size);
        }
    }
})();