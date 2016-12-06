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
        .service('responseHandler', responseHandler);

    responseHandler.$inject = ['$log', '$parse', 'validation'];
    function responseHandler($log, $parse, validation) {
        var SERVER_ERROR_CODE = 'serverMessage';
        this.handle = handle;

        function handle(form, formValidationResult, scope) {
            $log.log('responseHandler.handle()', form, formValidationResult);

            form.$setPristine();
            angular.forEach(formValidationResult.form, function (rejectedValue, fieldName) {
                if (fieldName !== 'id') {
                    validation.setValid(form[fieldName], SERVER_ERROR_CODE);
                }
            });

            for (var i = 0; i < formValidationResult.formErrors.length; i++) {
                var fieldName = formValidationResult.formErrors[i].field;
                var message = formValidationResult.formErrors[i].message;
                validation.setInvalid(scope, form, fieldName, SERVER_ERROR_CODE, message);
            }

            scope.$broadcast('show-errors-check-validity');

            return formValidationResult;
        }
    }
})();