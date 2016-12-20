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
        .module('platformSheetReaderModule')
        .directive('platformSheetReader', platformSheetReaderDirective);

    platformSheetReaderDirective.$inject = ['$log'];

    function platformSheetReaderDirective($log) {
        return {
            scope: {
                parsedData: '='
            },
            link: link
        };

        function link(scope, element) {
            $log.debug('platformSheetReaderDirective');
            var fileTypes = ['xlsx', 'xls', 'csv'];
            var el = angular.element(element);
            var input = angular.element(el.children()[1]);

            $log.debug('el: ', el);
            $log.debug('input: ', input);
            input.on('change', function (changeEvent) {
                var reader = new FileReader();
                var extension = changeEvent.target.files[0].name.split('.').pop().toLowerCase();
                $log.debug('extension: ', extension);

                if (fileTypes[0] === extension || fileTypes[1] === extension) {
                    reader.onload = function (evt) {
                        scope.$apply(function () {
                            var data = evt.target.result;
                            var workbook = XLSX.read(data, {type: 'binary'});
                            var data = XLSX.utils.sheet_to_csv(workbook.Sheets[workbook.SheetNames[0]]);
                            scope.parsedData = getParsedGridData(data.split('\n'));
                            $log.debug('scope.parsedData.length: ', scope.parsedData.length);
                            input.val(null);
                        });
                    };
                }
                if (fileTypes[2] === extension){
                    reader.onload = function (evt) {
                        scope.$apply(function () {
                            var data = evt.target.result;
                            scope.parsedData = getParsedGridData(data.split('\r\n'));
                            $log.debug('scope.parsedData.length: ', scope.parsedData.length);
                            input.val(null);
                        });
                    };

                }
                reader.readAsBinaryString(changeEvent.target.files[0]);
            });

            function getParsedGridData(data) {
                var parsedData = [];
                data.forEach(function (dataRow) {
                    var row = [];
                    dataRow.split(',').forEach(function (h, index) {
                            row['col' + index] = h;
                        }
                    );
                    parsedData.push(row);
                });
                return parsedData;
            }
        }
    }
})();