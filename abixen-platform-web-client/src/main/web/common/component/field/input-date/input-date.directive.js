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
        .directive('inputDate', inputDate);

    inputDate.$inject = ['$parse', 'validation'];
    function inputDate($parse, validation) {

        return {
            restrict: 'E',
            require: '^form',
            templateUrl: 'common/component/field/input-date/input-date.template.html',
            scope: {
                model: '=',
                validators: '=',
                label: '@',
                name: '@',
                size: '@'
            },
            link: link,
            controller: InputDateController,
            controllerAs: 'inputDate',
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

    InputDateController.$inject = ['fieldSize'];

    function InputDateController(fieldSize) {
        var inputDate = this;

        inputDate.calendarStatus = {
            opened: false
        };

        inputDate.openCalendar = openCalendar;

        initValidators();
        initResponsiveClasses();

        function initValidators() {
            if (!inputDate.validators) {
                return;
            }

            inputDate.fieldValidators = {};
            angular.forEach(inputDate.validators[inputDate.name], function (validator) {
                angular.extend(inputDate.fieldValidators, validator);
            });
        }

        function initResponsiveClasses() {
            inputDate.responsiveClasses = fieldSize.getClasses(inputDate.size);
        }

        function openCalendar($event) {
            inputDate.calendarStatus.opened = true;
        }
    }
})();