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
        .module('webContentServiceWebContentModule')
        .controller('WebContentServiceAdvancedWebContentDetailsController', WebContentServiceAdvancedWebContentDetailsController);

    WebContentServiceAdvancedWebContentDetailsController.$inject = [
        '$scope',
        '$state',
        '$stateParams',
        '$log',
        'AdvancedWebContent',
        'responseHandler',
        'Structure'
    ];

    function WebContentServiceAdvancedWebContentDetailsController($scope, $state, $stateParams, $log, AdvancedWebContent, responseHandler, Structure) {

        var advancedWebContentDetails = this;
        advancedWebContentDetails.entity = null;
        advancedWebContentDetails.onSuccessSaveForm = onSuccessSaveForm;
        new AbstractDetailsController(advancedWebContentDetails, AdvancedWebContent, responseHandler, $scope,
            {
                entityId: $stateParams.id,
                initEntity: {
                    type: 'ADVANCED'
                },
                getValidators: getValidators,
                onSuccessSaveForm: onSuccessSaveForm
            }
        );

        advancedWebContentDetails.ckeditorOptions = {
            language: 'en',
            allowedContent: true,
            entities: false
        };

        getStructures();


        function onSuccessSaveForm() {
            $state.go('application.webContentService.webContent.list');
        }

        function getValidators() {
            var validators = [];

            validators['title'] =
                [
                    new NotNull(),
                    new Length(6, 255)
                ];
            validators['structure'] =
                [
                    new NotNull()
                ];

            return validators;
        }

        function getStructures() {
            Structure.queryAll().$promise.then(onQueryResult);

            function onQueryResult(structures) {
                advancedWebContentDetails.structures = structures;
            }
        }

    }
})();