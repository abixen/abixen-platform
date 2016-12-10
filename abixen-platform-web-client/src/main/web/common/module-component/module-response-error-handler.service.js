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
        .module('platformModuleComponent')
        .service('moduleResponseErrorHandler', moduleResponseErrorHandler);

    moduleResponseErrorHandler.$inject = ['$log'];
    function moduleResponseErrorHandler($log) {
        this.handle = handle;

        function handle(error, scope) {
            $log.log('moduleResponseErrorHandler.handle()');

            scope.$emit(platformParameters.events.STOP_REQUEST);
            if (error.status === 401) {
                scope.$emit(platformParameters.events.MODULE_UNAUTHENTICATED);
            } else if (error.status === 403) {
                scope.$emit(platformParameters.events.MODULE_FORBIDDEN);
            }
        }
    }
})();