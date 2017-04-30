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
        .module('webContentServiceStructureModule', ['ui.codemirror'])
        .controller('WebContentServiceStructureDetailsController', WebContentServiceStructureDetailsController);

    WebContentServiceStructureDetailsController.$inject = [
        '$scope',
        'Structure',
        'Template',
        '$state',
        '$stateParams',
        '$log',
        'responseHandler'
    ];

    function WebContentServiceStructureDetailsController($scope, Structure, Template, $state, $stateParams, $log, responseHandler) {
        $log.log('WebContentServiceStructureDetailsController');
        var structureDetails = this;

        new AbstractDetailsController(structureDetails, Structure, responseHandler, $scope,
            {
                entityId: $stateParams.id,
                getValidators: getValidators,
                onSuccessSaveForm: onSuccessSaveForm
            }
        );

        if($state.current.name === 'application.webContentService.structure.add'){
            structureDetails.entity.content='';
        }

        structureDetails.cancel = cancel;
        getTemplates();

        function onSuccessSaveForm() {
            $state.go('application.webContentService.structure.list');
        }

        function cancel() {
            $state.go('application.webContentService.structure.list');
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
                    new NotNull(),
                    new Length(1, 1000)
                ];

            validators['template'] =
                [
                    new NotNull()
                ];
            return validators;
        }

        function getTemplates() {
            Template.queryAll().$promise.then(onQueryResult);

            function onQueryResult(templates) {
                structureDetails.templates = templates;
            }
        }

        structureDetails.isVisualEditor =false;
        structureDetails.toggleEditor= function () {
          structureDetails.isVisualEditor = (structureDetails.isVisualEditor)? false : true;
          return  structureDetails.isVisualEditor;
        };
    }
})();