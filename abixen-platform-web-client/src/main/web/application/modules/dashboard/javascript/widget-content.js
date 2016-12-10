/*
 * The MIT License
 *
 * Copyright (c) 2015, Sebastian Sdorra
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

'use strict';

angular.module('adf')
    .directive('adfWidgetContent', ['$log', '$q', '$sce', '$http', '$templateCache', '$compile', '$controller', '$injector', '$rootScope', 'dashboard', '$timeout', '$stateParams',
        function ($log, $q, $sce, $http, $templateCache, $compile, $controller, $injector, $rootScope, dashboard, $timeout, $stateParams) {

            $log.log('adf adfWidgetContent +++ !!!!!!!!!');

            function parseUrl(url) {
                var parsedUrl = url;
                if (url.indexOf('{widgetsPath}') >= 0) {
                    parsedUrl = url.replace('{widgetsPath}', dashboard.widgetsPath)
                        .replace('//', '/');
                    if (parsedUrl.indexOf('/') === 0) {
                        parsedUrl = parsedUrl.substring(1);
                    }
                }
                return parsedUrl;
            }

            function getTemplate(widget) {
                var deferred = $q.defer();
                if (widget.template) {
                    deferred.resolve(widget.template);
                } else if (widget.templateUrl) {
                    // try to fetch template from cache
                    var tpl = $templateCache.get(widget.templateUrl);
                    if (tpl) {
                        deferred.resolve(tpl);
                    } else {
                        var url = $sce.getTrustedResourceUrl(parseUrl(widget.templateUrl));
                        $http.get(url)
                            .success(function (response) {
                                // put response to cache, with unmodified url as key
                                $templateCache.put(widget.templateUrl, response);
                                deferred.resolve(response);
                            })
                            .error(function () {
                                deferred.reject('could not load template');
                            });
                    }
                }

                return deferred.promise;
            }

            function compileWidget($scope, $element, currentScope) {
                var model = $scope.model;
                var content = $scope.content;

                // display loading template
                $element.html(dashboard.loadingTemplate);

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
                    widget: model,
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
                    if (content.controller) {
                        var templateCtrl = $controller(content.controller, locals);
                        if (content.controllerAs) {
                            templateScope[content.controllerAs] = templateCtrl;
                        }
                        $element.children().data('$ngControllerController', templateCtrl);
                    }
                    $compile($element.contents())(templateScope);
                }, function (reason) {
                    // handle promise rejection
                    var msg = 'Could not resolve all promises';
                    if (reason) {
                        msg += ': ' + reason;
                    }
                    $log.warn(msg);
                    $element.html(dashboard.messageTemplate.replace(/{}/g, msg));
                });

                // destroy old scope
                if (currentScope) {
                    currentScope.$destroy();
                }

                return templateScope;
            }

            return {
                replace: true,
                restrict: 'EA',
                transclude: false,
                scope: {
                    model: '=',
                    content: '='
                },
                link: function ($scope, $element) {

                    var currentScope = compileWidget($scope, $element, null);
                    $scope.$on('widgetConfigChanged', function () {
                        currentScope = compileWidget($scope, $element, currentScope);
                    });
                    $scope.$on('widgetReload', function () {
                        currentScope = compileWidget($scope, $element, currentScope);
                    });

                    $log.log('Before binding module ready');
                    $scope.$on(platformParameters.events.MODULE_READY, function () {
                        if ($scope.model.id == null) {
                            return;
                        }
                        $log.log('Will reload module id: ' + $scope.model.id);
                        $scope.$broadcast(platformParameters.events.RELOAD_MODULE, $scope.model.id, $stateParams.mode);
                    });

                    $scope.$on(platformParameters.events.MODULE_FORBIDDEN, function () {

                        $log.log('Will show module is forbidden: ' + $scope.model.id);
                        $scope.$emit(platformParameters.events.SHOW_PERMISSION_DENIED_TO_MODULE);
                    });

                    $scope.$on(platformParameters.events.START_REQUEST, function () {
                        $log.log('Will show loader: ' + $scope.model.id);
                        $scope.$emit(platformParameters.events.SHOW_LOADER);
                    });

                    $scope.$on(platformParameters.events.STOP_REQUEST, function () {
                        $log.log('Will hide loader: ' + $scope.model.id);
                        $scope.$emit(platformParameters.events.HIDE_LOADER);
                    });

                    $scope.$on(platformParameters.events.CONFIGURATION_MODE_READY, function () {
                        $log.log('Will show exit configuration mode icon: ' + $scope.model.id);
                        $scope.$emit(platformParameters.events.SHOW_EXIT_CONFIGURATION_MODE_ICON);
                    });

                    $scope.$on(platformParameters.events.VIEW_MODE_READY, function () {
                        $log.log('Will show configuration mode icon: ' + $scope.model.id);
                        $scope.$emit(platformParameters.events.SHOW_CONFIGURATION_MODE_ICON);
                    });

                    $scope.$on(platformParameters.events.REGISTER_MODULE_CONTROL_ICONS, function (event, icons) {
                        $log.log('Will update module\'s control icons: ', $scope.model.id, icons);
                        $scope.$emit(platformParameters.events.UPDATE_MODULE_CONTROL_ICONS, icons);
                    });

                    $scope.$watch('model.id', function (newId, oldId) {
                        if (oldId == null && newId != null) {
                            $scope.$broadcast(platformParameters.events.RELOAD_MODULE, newId, $stateParams.mode);
                        }
                    }, true);

                }
            };

        }]);