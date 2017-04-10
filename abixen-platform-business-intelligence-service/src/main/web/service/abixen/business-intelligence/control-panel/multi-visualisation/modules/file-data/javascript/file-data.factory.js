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
        .module('platformFileDataModule')
        .factory('FileData', FileData);

    FileData.$inject = ['$resource'];

    function FileData($resource) {

        return $resource('/api/service/abixen/business-intelligence/control-panel/multi-visualisation/file-data/:id', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'},
            columns: {
                method: 'GET',
                url: '/api/service/abixen/business-intelligence/control-panel/multi-visualisation/file-data/:id/columns',
                isArray: true
            },
            parse: {
                method: "POST",
                headers : {
                    'Content-Type' : undefined
                },
                url: '/api/service/abixen/business-intelligence/control-panel/multi-visualisation/file-data/parse/:readFirstColumnAsColumnName'
            }
        });
    }

})();