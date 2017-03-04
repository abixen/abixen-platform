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
        .config(fieldTranslateConfig);

    fieldTranslateConfig.$inject = ['$translateProvider'];
    function fieldTranslateConfig($translateProvider) {

        $translateProvider.useSanitizeValueStrategy('escape');

        $translateProvider.translations('POLISH', {
            'application.validator.invalid.minlength': 'Min długość pola "{{label}}" wynosi {{min}}',
            'application.validator.invalid.maxlength': 'Max długość pola "{{label}}" wynosi {{max}}',
            'application.validator.invalid.min': 'Min wartość pola "{{label}}" wynosi {{min}}',
            'application.validator.invalid.max': 'Max wartość pola "{{label}}" wynosi {{max}}',
            'application.validator.invalid.required': '{{label}} jest wymagane',
            'application.validator.invalid.email': '{{label}} ma niepoprawny format'

        });

        $translateProvider.translations('ENGLISH', {
            'application.validator.invalid.minlength': 'Min length of field "{{label}}" is {{min}}',
            'application.validator.invalid.maxlength': 'Max length of field "{{label}}" is {{max}}',
            'application.validator.invalid.min': 'Min value of field "{{label}}" is {{min}}',
            'application.validator.invalid.max': 'Max value of field "{{label}}" is {{max}}',
            'application.validator.invalid.required': '{{label}} is required',
            'application.validator.invalid.email': '{{label}} has invalid format'
        });

        $translateProvider.translations('RUSSIAN', {
            'application.validator.invalid.minlength': 'Длина поля "{{label}}" не менее {{min}}',
            'application.validator.invalid.maxlength': 'Длина поля "{{label}}" не более {{max}}',
            'application.validator.invalid.min': 'Min value of field "{{label}}" is {{min}}',
            'application.validator.invalid.max': 'Max value of field "{{label}}" is {{max}}',
            'application.validator.invalid.required': '{{label}} обязательное поле',
            'application.validator.invalid.email': '{{label}} имеет неверный формат'
        });

        $translateProvider.translations('UKRAINIAN', {
            'application.validator.invalid.minlength': 'Довжина поля "{{label}}" не менше {{min}}',
            'application.validator.invalid.maxlength': 'Довжина поля "{{label}}" не більше {{max}}',
            'application.validator.invalid.min': 'Min value of field "{{label}}" is {{min}}',
            'application.validator.invalid.max': 'Max value of field "{{label}}" is {{max}}',
            'application.validator.invalid.required': '{{label}} обов\'язкове поле',
            'application.validator.invalid.email': '{{label}} має невірний формат'
        });

        $translateProvider.translations('SPANISH', {
            'application.validator.invalid.minlength': 'La longitud mínima del campo "{{label}}" es {{min}}',
            'application.validator.invalid.maxlength': 'La longitud máxima del campo "{{label}}" es {{max}}',
            'application.validator.invalid.min': 'Min value of field "{{label}}" is {{min}}',
            'application.validator.invalid.max': 'Max value of field "{{label}}" is {{max}}',
            'application.validator.invalid.required': '{{label}} es requerido(a)',
            'application.validator.invalid.email': '{{label}} tiene formato inválido'
        });

    }
})();