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
        .module('platformPageModule')
        .factory('PageModel', PageModel);

    PageModel.$inject = ['$resource'];

    function PageModel($resource) {

        return $resource('/api/page-configurations/:id', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'},
            configure: {
                method: 'PUT',
                params: {id: '@id'},
                url: '/api/page-configurations/:id/configure'
            }
        });
    }

})();