/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 * Copyright (c) 2015, Sebastian Sdorra
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
        .module('platformDashboardModule')
        .service('moduleContentUtils', moduleContentUtils);

    moduleContentUtils.$inject = [
        '$q',
        '$sce',
        '$http',
        '$templateCache',
        '$compile',
        '$injector'
    ];

    function moduleContentUtils($q, $sce, $http, $templateCache, $compile, $injector) {
        this.compileModule = compileModule;


        function compileModule($scope, $element, currentScope) {
            var model = $scope.model;
            var content = $scope.content;

            $scope.$emit(platformParameters.events.SHOW_LOADER);

            // create new scope
            var templateScope = $scope.$new();

            // pass config object to scope
            if (!model.config) {
                model.config = {};
            }

            templateScope.config = model.config;

            // local injections
            var base = {
                $scope: templateScope,
                module: model,
                config: model.config
            };

            // get resolve promises from content object
            var resolvers = {};
            resolvers.$tpl = getTemplate(content);
            if (content.resolve) {
                angular.forEach(content.resolve, function (promise, key) {
                    if (angular.isString(promise)) {
                        resolvers[key] = $injector.get(promise);
                    } else {
                        resolvers[key] = $injector.invoke(promise, promise, base);
                    }
                });
            }

            // resolve all resolvers
            $q.all(resolvers).then(function (locals) {
                angular.extend(locals, base);

                // compile & render template
                var template = locals.$tpl;
                $element.html(template);
                $scope.$emit(platformParameters.events.HIDE_LOADER);

                $compile($element.contents())(templateScope);
            }, function (reason) {
                //TODO needed ?
                $element.html('');
                $scope.$emit(platformParameters.events.MODULE_TEMPORARY_UNAVAILABLE);
            });

            // destroy old scope
            if (currentScope) {
                currentScope.$destroy();
            }

            return templateScope;
        }

        function getTemplate(module) {
            //TODO
            var deferred = $q.defer();
            if (module.template) {
                deferred.resolve(module.template);
            } else if (module.templateUrl) {
                // try to fetch template from cache
                var tpl = $templateCache.get(module.templateUrl);
                if (tpl) {
                    deferred.resolve(tpl);
                } else {
                    var url = $sce.getTrustedResourceUrl(module.templateUrl);
                    $http.get(url)
                        .success(function (response) {
                            // put response to cache, with unmodified url as key
                            $templateCache.put(module.templateUrl, response);
                            deferred.resolve(response);
                        })
                        .error(function () {
                            deferred.reject('could not load template');
                        });
                }
            }

            return deferred.promise;
        }
    }
})();