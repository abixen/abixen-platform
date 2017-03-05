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
        .module('platformModalSelectionModule')
        .factory('ModalSelectionWindowService', ModalSelectionWindowService);

    ModalSelectionWindowService.$inject = ['$uibModal'];

    function ModalSelectionWindowService($uibModal) {

        var okButtonClass = '';
        var cancelButtonClass = '';
        var windowClass = '';

        return ({
            getOkButtonClass: getOkButtonClass,
            setOkButtonClass: setOkButtonClass,
            getCancelButtonClass: getCancelButtonClass,
            setCancelButtonClass: setCancelButtonClass,
            openSelectionDialog: openSelectionDialog
        });

        function getOkButtonClass() {
            return okButtonClass;
        }

        function setOkButtonClass(newOkButtonClass) {
            okButtonClass = newOkButtonClass;
        }

        function getCancelButtonClass() {
            return cancelButtonClass;
        }

        function setCancelButtonClass(newCancelButtonClass) {
            cancelButtonClass = newCancelButtonClass;
        }

        function setWindowClass(newWindowClass) {
            windowClass = newWindowClass;
        }

        function openSelectionDialog(title, variable, data, selectionType, windowClass, callback) {
            return $uibModal.open({
                animation: true,
                templateUrl: '/common/modal-selection/html/modal-selection.html',
                controller: 'ModalSelectionWindowController',
                windowClass: 'modal-selection-dialog',
                size: 'md', // medium by default
                resolve: {
                    title: function () {
                        return title;
                    },
                    variable: function () {
                        return variable;
                    },
                    data: function () {
                        return data;
                    },
                    selectionType: function () {
                        return selectionType;
                    },
                    acceptFunction: function () {
                        return callback;
                    },
                    okButtonClass: function () {
                        return getOkButtonClass();
                    },
                    cancelButtonClass: function () {
                        return getCancelButtonClass();
                    }
                }
            });

        }
    }

})();