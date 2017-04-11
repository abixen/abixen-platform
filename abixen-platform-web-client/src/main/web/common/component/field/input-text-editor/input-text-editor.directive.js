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
        .directive('inputTextEditor', inputTextEditor);

    inputTextEditor.$inject = ['$parse', 'validation'];
    function inputTextEditor($parse, validation) {

        return {
            restrict: 'E',
            require: '^form',
            templateUrl: 'common/component/field/input-text-editor/input-text-editor.template.html',
            scope: {
                model: '=',
                validators: '=',
                label: '@',
                placeholder: '@',
                name: '@'
            },
            link: link,
            controller: inputTextEditorController,
            controllerAs: 'inputTextEditor',
            bindToController: true
        };

        function link(scope, element, attrs, formCtrl) {
            scope.form = formCtrl;
        }

        function inputTextEditorController(fieldSize, $element, validation) {
            var inputTextEditor = this;
            var fieldName = $element.attr('name');

            angular.element(document).ready(function () {
                inputTextEditor.form = $element.parent().controller('form');
                inputTextEditor.editor = CodeMirror.fromTextArea(document.getElementById(fieldName + "Input"), {
                    lineNumbers: true,
                    lineWrapping: true,
                    mode: "text/html",
                    matchBrackets: true,
                    theme: 'default'
                });
                initialize();
                function initialize() {
                    if (inputTextEditor.form == null || inputTextEditor.form[fieldName].$viewValue == null) {
                        window.setTimeout(initialize, 100);
                    } else {
                        inputTextEditor.editor.setValue(inputTextEditor.form[fieldName].$viewValue);
                        inputTextEditor.editor.on('change', function (editor) {
                            validation.setValid(inputTextEditor.form[fieldName], 'serverMessage');
                            inputTextEditor.form[fieldName].$setDirty();
                            inputTextEditor.form[fieldName].$setViewValue(editor.getValue());
                        });
                    }
                }
            });

            initValidators();
            initResponsiveClasses();

            function initValidators() {
                if (!inputTextEditor.validators) {
                    return;
                }

                inputTextEditor.fieldValidators = {};
                angular.forEach(inputTextEditor.validators[inputTextEditor.name], function (validator) {
                    angular.extend(inputTextEditor.fieldValidators, validator);
                });
            }

            function initResponsiveClasses() {
                inputTextEditor.responsiveClasses = fieldSize.getClasses(inputTextEditor.size);
            }
        }

        inputTextEditorController.$inject = ['fieldSize', '$element', 'validation'];
    }
})();