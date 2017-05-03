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
        'WebContent',
        'responseHandler',
        'Structure'
    ];

    function WebContentServiceAdvancedWebContentDetailsController($scope, $state, $stateParams, $log, WebContent, responseHandler, Structure) {

        var advancedWebContentDetails = this;
        advancedWebContentDetails.entity = null;
        advancedWebContentDetails.onSuccessSaveForm = onSuccessSaveForm;
        new AbstractDetailsController(advancedWebContentDetails, WebContent, responseHandler, $scope,
            {
                entityId: $stateParams.id,
                initEntity: {
                    type: 'ADVANCED',
                    classType: 'ADVANCED'
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
        advancedWebContentDetails.structureFields = [];
        advancedWebContentDetails.changeStructure = changeStructure;
        advancedWebContentDetails.beforeSaveForm = beforeSaveForm;

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

        function getStructureValidators(structureFields) {
            var validators = [];

            angular.forEach(structureFields, function (value) {
                validators['' + value.name] =
                    [
                        new NotNull()
                    ];
            });

            return validators;
        }

        function getStructures() {
            Structure.queryAll().$promise.then(onQueryResult);

            function onQueryResult(structures) {
                advancedWebContentDetails.structures = structures;
            }
        }

        function changeStructure(structure) {
            if (!structure) {
                return;
            }
            advancedWebContentDetails.structureFieldsValues = getValues(advancedWebContentDetails.entity.content);
            advancedWebContentDetails.structureFields = getFields(structure.content);
            advancedWebContentDetails.structureValidators = getStructureValidators(advancedWebContentDetails.structureFields);
        }

        function getFields(xmlString) {
            var parser = new DOMParser();
            var xmlDoc = parser.parseFromString(xmlString, 'text/xml');
            var fields = [];
            var elements = xmlDoc.getElementsByTagName('field');
            for (var i = 0; i < elements.length; i++) {
                var field = {
                    name: elements[i].getAttribute('name'),
                    type: elements[i].getAttribute('type')
                };
                fields.push(field);
            }
            return fields;
        }

        function getValues(xmlContent) {
            var parser = new DOMParser();
            var xmlDoc = parser.parseFromString(xmlContent, 'text/xml');
            var values = {};
            var elements = xmlDoc.getElementsByTagName('field');
            for (var i = 0; i < elements.length; i++) {
                values['' + elements[i].getAttribute('name')] = elements[i].getAttribute('value');
            }
            return values;
        }

        function getXmlContent(fieldsValues) {
            var xmlString = '<?xml version="1.0" encoding="UTF-8"?><fields></fields>';
            var parser = new DOMParser();
            var xmlDoc = parser.parseFromString(xmlString, 'text/xml');
            var elements = xmlDoc.getElementsByTagName('fields');
            angular.forEach(fieldsValues, function (value, name) {
                var node = xmlDoc.createElement("field");
                node.setAttribute('name', name);
                node.setAttribute('value', value);
                elements[0].appendChild(node);
            });
            var serializer = new XMLSerializer();
            return serializer.serializeToString(xmlDoc);
        }

        function beforeSaveForm() {
            var content = getXmlContent(advancedWebContentDetails.structureFieldsValues);
            advancedWebContentDetails.entity.content = content;
            advancedWebContentDetails.saveForm();
        }

    }
})();