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
        .module('multiVisualizationModule')
        .directive('queryBuilder', queryBuilderDirective);


    queryBuilderDirective.$inject = ['$compile'];

    function queryBuilderDirective($compile) {
        return {
            restrict: 'E',
            scope: {
                group: '=',
                fields: '='
            },
            templateUrl: '/admin/businessintelligence/abixen/multi-visualization/html/query-builder.html',
            compile: compile
        };


        function compile(element, attrs) {
            var content, directive;
            content = element.contents().remove();
            return function (scope, element, attrs) {
                scope.operators = [
                    {name: 'AND'},
                    {name: 'OR'}
                ];

                scope.conditions = [
                    {name: '='},
                    {name: '<>'},
                    {name: '<'},
                    {name: '<='},
                    {name: '>'},
                    {name: '>='}
                ];

                scope.addCondition = function () {
                    scope.group.rules.push({
                        condition: '=',
                        field: 'Firstname',
                        data: ''
                    });
                };

                scope.removeCondition = function (index) {
                    scope.group.rules.splice(index, 1);
                };

                scope.addGroup = function () {
                    scope.group.rules.push({
                        group: {
                            operator: 'AND',
                            rules: []
                        }
                    });
                };

                scope.removeGroup = function () {
                    'group' in scope.$parent && scope.$parent.group.rules.splice(scope.$parent.$index, 1);
                };

                directive || (directive = $compile(content));

                element.append(directive(scope, function ($compile) {
                    return $compile;
                }));
            }
        }

    }
})();