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
        .module('field')
        .config(fieldTranslateConfig);

    fieldTranslateConfig.$inject = ['$translateProvider'];
    function fieldTranslateConfig($translateProvider) {

        $translateProvider.useSanitizeValueStrategy('escape');

        $translateProvider.translations('pl', {
            'application.validator.invalid.minlength': 'Min długość pola "{{label}}" wynosi {{min}}',
            'application.validator.invalid.maxlength': 'Max długość pola "{{label}}" wynosi {{max}}',
            'application.validator.invalid.required': '{{label}} jest wymagane',
            'application.validator.invalid.email': '{{label}} ma niepoprawny format'

        });

        $translateProvider.translations('en', {
            'application.validator.invalid.minlength': 'Min length of field "{{label}}" is {{min}}',
            'application.validator.invalid.maxlength': 'Max length of field "{{label}}" is {{max}}',
            'application.validator.invalid.required': '{{label}} is required',
            'application.validator.invalid.email': '{{label}} has invalid format'
        });
    }
})();