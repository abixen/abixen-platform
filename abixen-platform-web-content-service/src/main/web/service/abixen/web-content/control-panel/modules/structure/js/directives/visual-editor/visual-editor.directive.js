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
        .directive('visualEditor', visualEditor);

    visualEditor.$inject = ['$parse', 'validation'];
    function visualEditor($parse, validation) {

        return {
            restrict: 'E',
            templateUrl: 'service/abixen/web-content/control-panel/modules/structure/js/directives/visual-editor/visual-editor.template.html',
            scope: {
                model: '=',
                label: '@'
            },
            link: link,
            controller: visualEditorController,
            controllerAs: 'visualEditor',
            bindToController: true
        };

        function link(scope, element, attrs) {
        }

        function visualEditorController() {

           var visualEditor = this;
            visualEditor.visual = {
                rows: {
                    row: []
                },
                row: function (rowId, name, selectedFieldType) {
                    return {
                        fieldset: {
                            rowId: rowId,
                            name: name || '',
                            selectedFieldType: selectedFieldType || 'text',
                            fieldTypes: [
                                {
                                    name: 'text',
                                    value: 'text'
                                },
                                {
                                    name: 'textArea',
                                    value: 'textArea'
                                },
                                {
                                    name: 'richTextArea',
                                    value: 'richTextArea'
                                },
                                {
                                    name: 'date',
                                    value: 'date'
                                }
                            ]
                        }
                    };
                },
                addRow: function () {
                    var rowId = visualEditor.visual.rows.row.length;
                    visualEditor.visual.rows.row.push(new visualEditor.visual.row(rowId));

                },
                removeRow: function (rowId) {
                    visualEditor.visual.rows.row.splice(rowId, 1);
                    visualEditor.visual.rows.row.forEach(function (rowElement) {
                        if (rowElement.fieldset.rowId > rowId) {
                            rowElement.fieldset.rowId -= 1;
                        }
                    });
                    visualEditor.model =visualEditor.transform.rowToXml(visualEditor.visual.rows);
                }
            };
            visualEditor.transform = {
                    rowToXml : function (rows) {
                            var xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><fields></fields>";
                            var parser = new DOMParser();
                            var xmlDoc = parser.parseFromString(xmlString, "text/xml");
                               var elements  = xmlDoc.getElementsByTagName("fields");
                                rows.row.forEach(function (rowElement) {
                                    var node = xmlDoc.createElement("field");
                                    node.setAttribute('name', rowElement.fieldset.name);
                                    node.setAttribute('type', rowElement.fieldset.selectedFieldType);
                                    elements[0].appendChild(node);
                                });
                                var serializer = new XMLSerializer();
                            return serializer.serializeToString(xmlDoc);
                    },
                    xmlToRow : function (xmlString) {
                            var parser = new DOMParser();
                            var xmlDoc = parser.parseFromString(xmlString, "text/xml");
                            var  rows = {row: []};
                                var elements = xmlDoc.getElementsByTagName("field");
                                for (var i = 0; i < elements.length; i++) {
                                    var row = visualEditor.visual.row(i,
                                        elements[i].getAttribute('name'),
                                        elements[i].getAttribute('type'));
                                    rows.row.push(row);
                                };
                                return rows;
                    }
                };

            visualEditor.transformToXml = function () {
                visualEditor.model = visualEditor.transform.rowToXml(visualEditor.visual.rows);
            };
            visualEditor.visual.rows = visualEditor.transform.xmlToRow(visualEditor.model);
        };

        visualEditorController.$inject = [];
    };
})();