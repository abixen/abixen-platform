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
        .module('platformComponent')
        .directive('ckeditor', ckeditor);

    var setImmediate = window && window.setImmediate ? window.setImmediate : function (fn) {
            setTimeout(fn, 0);
        };

    ckeditor.$inject = ['$parse'];
    function ckeditor($parse) {

        return {
            restrict: 'A',
            require: ['ngModel', 'ckeditor'],
            link: link,
            controller: CkeditorController,
            controllerAs: 'ckeditor'
        };

        function link(scope, element, attrs, ctrls) {
            var ngModelController = ctrls[0];
            var controller = ctrls[1]; // our own, see below

            // Initialize the editor content when it is ready.
            controller.ready().then(function initialize() {
                // Sync view on specific events.
                ['dataReady', 'change', 'blur', 'saveSnapshot'].forEach(function (event) {
                    controller.onCKEvent(event, function syncView() {
                        ngModelController.$setViewValue(controller.instance.getData() || '');
                    });
                });

                controller.instance.setReadOnly(!!attrs.readonly);
                attrs.$observe('readonly', function (readonly) {
                    controller.instance.setReadOnly(!!readonly);
                });

                // Defer the ready handler calling to ensure that the editor is
                // completely ready and populated with data.
                setImmediate(function () {
                    $parse(attrs.ready)(scope);
                });
            });

            // Set editor data when view data change.
            ngModelController.$render = function syncEditor() {
                controller.ready().then(function () {
                    // "noSnapshot" prevent recording an undo snapshot
                    controller.instance.setData(ngModelController.$viewValue || '', {
                        noSnapshot: true,
                        callback: function () {
                            // Amends the top of the undo stack with the current DOM changes
                            // ie: merge snapshot with the first empty one
                            // http://docs.ckeditor.com/#!/api/CKEDITOR.editor-event-updateSnapshot
                            controller.instance.fire('updateSnapshot');
                        }
                    });
                });
            };
        }

        function CkeditorController($scope, $element, $attrs, $parse, $q) {
            var ckeditorCtrl = this;

            var config = $parse($attrs.ckeditor)($scope) || {};
            var editorElement = $element[0];
            var instance;
            var readyDeferred = $q.defer(); // a deferred to be resolved when the editor is ready

            // Create editor instance.
            if (editorElement.hasAttribute('contenteditable') &&
                editorElement.getAttribute('contenteditable').toLowerCase() == 'true') {
                instance = ckeditorCtrl.instance = CKEDITOR.inline(editorElement, config);
            }
            else {
                instance = ckeditorCtrl.instance = CKEDITOR.replace(editorElement, config);
            }

            /**
             * Listen on events of a given type.
             * This make all event asynchronous and wrapped in $scope.$apply.
             *
             * @param {String} event
             * @param {Function} listener
             * @returns {Function} Deregistration function for this listener.
             */

            ckeditorCtrl.onCKEvent = function (event, listener) {
                instance.on(event, asyncListener);

                function asyncListener() {
                    var args = arguments;
                    setImmediate(function () {
                        applyListener.apply(null, args);
                    });
                }

                function applyListener() {
                    var args = arguments;
                    $scope.$apply(function () {
                        listener.apply(null, args);
                    });
                }

                // Return the deregistration function
                return function $off() {
                    instance.removeListener(event, applyListener);
                };
            };

            ckeditorCtrl.onCKEvent('instanceReady', function () {
                readyDeferred.resolve(true);
            });

            /**
             * Check if the editor if ready.
             *
             * @returns {Promise}
             */
            ckeditorCtrl.ready = function ready() {
                return readyDeferred.promise;
            };

            // Destroy editor when the scope is destroyed.
            $scope.$on('$destroy', function onDestroy() {
                // do not delete too fast or pending events will throw errors
                readyDeferred.promise.then(function () {
                    instance.destroy(false);
                });
            });
        }

    }


})();
