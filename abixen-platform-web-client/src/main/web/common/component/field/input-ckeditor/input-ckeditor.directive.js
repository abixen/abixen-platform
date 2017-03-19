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
        .directive('inputCkeditor', inputCkeditor);

    inputCkeditor.$inject = ['$parse', 'validation'];
    function inputCkeditor($parse, validation) {

        return {
            restrict: 'E',
            require: '^form',
            templateUrl: 'common/component/field/input-ckeditor/input-ckeditor.template.html',
            scope: {
                model: '=',
                validators: '=',
                label: '@',
                placeholder: '@',
                name: '@',
                size: '@',
                rows: '@',
                options: '='
            },
            link: link,
            controller: InputCkeditorController,
            controllerAs: 'inputCkeditor',
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

    InputCkeditorController.$inject = ['fieldSize'];

    function InputCkeditorController(fieldSize) {
        var inputCkeditor = this;

        initValidators();
        initResponsiveClasses();

        function initValidators() {
            if (!inputCkeditor.validators) {
                return;
            }

            inputCkeditor.fieldValidators = {};
            angular.forEach(inputCkeditor.validators[inputCkeditor.name], function (validator) {
                angular.extend(inputCkeditor.fieldValidators, validator);
            });
        }

        function initResponsiveClasses() {
            inputCkeditor.responsiveClasses = fieldSize.getClasses(inputCkeditor.size);
        }
    }
})();