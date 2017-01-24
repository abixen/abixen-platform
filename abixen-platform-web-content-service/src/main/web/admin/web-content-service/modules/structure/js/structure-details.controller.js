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
        .module('webContentServiceStructureModule',['ui.codemirror'])
        .controller('WebContentDetailsController', WebContentDetailsController);

    WebContentDetailsController.$inject = [
        '$scope',
        'Structure',
        '$state',
        '$stateParams',
        '$log',
        'responseHandler'
    ];

    function WebContentDetailsController($scope, Structure, $state, $stateParams, $log, responseHandler) {
        $log.log('WebContentDetailsController');
        var structureDetails = this;

        new AbstractDetailsController(structureDetails, Structure, responseHandler, $scope,
            {
                entityId: $stateParams.id,
                getValidators: getValidators,
                onSuccessSaveForm: onSuccessSaveForm
            }
        );

        function onSuccessSaveForm() {
            $state.go('application.webContentService.structure.list');
        }

        function goToViewMode() {
            $state.go('application.webContentService.structure.list');
        }

        function beforeSaveForm() {
            $log.log('beforeSaveForm', structureDetails.entity);

            structureDetails.entity.columns = [];
            var columnPosition = 1;
            structureDetails.saveForm();
        }

        function getValidators() {
            var validators = [];

            validators['title'] =
                [
                    new NotNull(),
                    new Length(6, 60)
                ];

            validators['content'] =
                [
                    new Length(0, 1000)
                ];

            validators['template'] =
                [
                    new NotNull()
                ];
            return validators;
        }

        structureDetails.templates = [
            { key: 'Template1' },
            { key: 'Template2' },
            { key: 'Template3' }
        ];

        angular.element(document).ready(function () {
            var editor = CodeMirror.fromTextArea(document.getElementById("contentInput"), {
                lineNumbers: true,
                lineWrapping: true,
                mode: "text/html",
                matchBrackets: true,
                theme: 'default'
            });
    });
    }
})();