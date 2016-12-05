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
        .service('fieldSize', fieldSize);

    function fieldSize() {
        this.TINY_SIZE = '1x';
        this.SMALL_SIZE = '2x';
        this.MEDIUM_SIZE = '4x';
        this.LARGE_SIZE = '8x';
        var DEFAULT_SIZE = this.SMALL_SIZE;

        var service = this;
        this.getClasses = getClasses;


        function getClasses(size) {
            if (!size) {
                size = DEFAULT_SIZE;
            }

            switch (size) {

                case service.TINY_SIZE:
                    return 'col-xs-12 col-sm-4 col-md-4 col-lg-2';
                case service.SMALL_SIZE:
                    return 'col-xs-12 col-sm-6 col-md-6 col-lg-4';
                case service.MEDIUM_SIZE:
                    return 'col-xs-12 col-sm-12 col-md-12 col-lg-6';
                case service.LARGE_SIZE:
                    return 'col-xs-12 col-sm-12 col-md-12 col-lg-12';
                default:
                    throw new Error('Wrong input size ' + size);
            }
        }
    }
})();