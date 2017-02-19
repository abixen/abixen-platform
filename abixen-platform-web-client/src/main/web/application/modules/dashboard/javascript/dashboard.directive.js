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
        .module('platformDashboardModule')
        .directive('platformDashboard', platformDashboard);

    platformDashboard.$inject = [
        '$rootScope',
        '$log',
        'dashboardData',
        'dashboardUtils',
        '$aside',
        'applicationNavigationItems',
        '$stateParams',
        'Layout',
        '$filter'
    ];

    function platformDashboard($rootScope, $log, dashboardData, dashboardUtils, $aside, applicationNavigationItems, $stateParams, Layout, $filter) {
        return {
            replace: true,
            restrict: 'EA',
            transclude: false,
            scope: {
                name: '@',
                model: '='
            },
            controller: function ($scope) {
                $scope.$on(platformParameters.events.ADF_WIDGET_DELETED_EVENT, function (event) {
                    $scope.$emit(platformParameters.events.ADF_DASHBOARD_CHANGED_EVENT, $scope.name, $scope.model);
                });

                $scope.$on(platformParameters.events.ADF_WIDGET_TITLE_CHANGED_EVENT, function (event) {
                    $scope.$emit(platformParameters.events.ADF_DASHBOARD_CHANGED_EVENT, $scope.name, $scope.model);
                });

                $scope.$on(platformParameters.events.ADF_WIDGET_MOVED_EVENT, function (event) {
                    $scope.$emit(platformParameters.events.ADF_DASHBOARD_CHANGED_EVENT, $scope.name, $scope.model);
                });

                // edit mode
                $scope.editMode = false;
                $scope.editClass = '';

                $scope.toggleEditMode = function () {
                    $scope.editMode = !$scope.editMode;
                    if ($scope.editMode) {
                        $scope.modelCopy = angular.copy($scope.model, {});
                    }
                };

                $scope.$on(platformParameters.events.ADF_TOGGLE_EDIT_MODE_EVENT, function (event) {
                    $scope.toggleEditMode();
                    $scope.$emit(platformParameters.events.ADF_TOGGLE_EDIT_MODE_RESPONSE_EVENT, $scope.editMode);
                });

                // edit dashboard settings
                $scope.editDashboardDialog = function () {
                    var editDashboardScope = $scope.$new();

                    editDashboardScope.pageDetails = {
                        entity: {
                            page: {
                                title: $scope.model.title,
                                description: $scope.model.description
                            }
                        },
                        saveForm: saveForm,
                        changeLayout: changeLayoutOnEditPage,
                        validators: getValidators()
                    };

                    Layout.query()
                        .$promise
                        .then(onQueryResult);

                    function onQueryResult(layouts) {
                        editDashboardScope.pageDetails.layouts = layouts;
                        editDashboardScope.pageDetails.entity.page.layout = $filter('filter')(layouts, {title: $scope.model.structure})[0];
                    }

                    var instance = $aside.open({
                        scope: editDashboardScope,
                        placement: 'left',
                        templateUrl: '/application/modules/page/html/add.html',
                        size: 'md',
                        backdrop: 'static'
                    });

                    function changeLayoutOnEditPage(layout) {
                        $log.info('change layout to ', layout);
                        dashboardUtils.changeLayout($scope.model, layout);
                        $rootScope.$broadcast(platformParameters.events.ADF_STRUCTURE_CHANGED_EVENT, layout);
                    }

                    function saveForm() {

                        //if (!editDashboardScope.copy.title) {
                        //   $log.log('Form is invalid and could not be saved.');
                        //   editDashboardScope.$broadcast('show-errors-check-validity');
                        //  return;
                        //}
                        // copy the new title back to the model
                        $scope.model.title = editDashboardScope.pageDetails.entity.page.title;
                        //FIXME - doesn't work
                        $scope.model.description = editDashboardScope.pageDetails.entity.page.description;

                        // close modal and destroy the scope
                        instance.dismiss();
                        editDashboardScope.$destroy();
                        applicationNavigationItems.editSidebarItem($stateParams.id, $scope.model.title);
                        $scope.$emit(platformParameters.events.PAGE_CHANGED_EVENT, $scope.name, $scope.model);
                    }

                    function getValidators() {
                        var validators = [];

                        validators['title'] =
                            [
                                new NotNull(),
                                new Length(1, 40)
                            ];

                        validators['description'] =
                            [
                                new Length(0, 255)
                            ];

                        return validators;
                    }


                    editDashboardScope.cancelDialog = function () {
                        // close modal and destroy the scope
                        instance.dismiss();
                        editDashboardScope.$destroy();
                    };
                };

                $scope.$on(platformParameters.events.ADF_EDIT_DASHBOARD_EVENT, function (event) {
                    $scope.editDashboardDialog();
                });

                // add module dialog
                $scope.addModuleDialog = function () {
                    var addScope = $scope.$new();
                    var modules = {};

                    angular.forEach(dashboardData.getModules(), function (module, type) {
                        if (module.serviceId != null) {
                            modules[type] = module;
                        }
                    });

                    addScope.modules = modules;
                    var opts = {
                        scope: addScope,
                        placement: 'left',
                        templateUrl: 'application/modules/dashboard/html/module-add.html',
                        size: 'sx',
                        backdrop: 'static'
                    };
                    var instance = $aside.open(opts);
                    addScope.addModule = function (moduleType, module) {
                        var w = {
                            id: module.id,
                            type: moduleType,
                            title: module.title,
                            moduleType: module.moduleType,
                            config: dashboardUtils.createConfiguration(moduleType),
                            wid: dashboardData.generateId()
                        };
                        dashboardUtils.addNewModuleToModel($scope.model, w);
                        $scope.$emit(platformParameters.events.ADF_DASHBOARD_CHANGED_EVENT, $scope.name, $scope.model);
                        // close and destroy
                        instance.dismiss();
                        addScope.$destroy();
                    };
                    addScope.closeDialog = function () {
                        // close and destroy
                        instance.dismiss();
                        addScope.$destroy();
                    };
                };

                $scope.$on(platformParameters.events.ADF_ADD_WIDGET_EVENT, function (event) {
                    $scope.addModuleDialog();
                });
            },
            link: function ($scope, $element, $attr) {

                $scope.$on('FULL_SCREEN_MODE', function (event, moduleId, fullScreenMode) {
                    var dashboardSubContainer = angular.element(document.getElementById('dashboard-sub-container'));

                    if (fullScreenMode) {
                        dashboardSubContainer.addClass('hidden');
                        var dashboardContainer = angular.element(document.getElementById('dashboard-container'));
                        var moduleContent = angular.element(document.getElementById('module-content-' + moduleId));
                        var module = angular.element('<div class="container-fluid modules-wrapper custom-column-container ng-scope ng-isolate-scope" id="platform-dashboard-row-full-screen"><div class="column ng-pristine ng-untouched ng-valid ng-scope ng-isolate-scope col-md-12"><div id="module-full-screen" class="module"></div></div></div>');
                        dashboardContainer.append(module);
                        var moduleFullScreen = angular.element(document.getElementById('module-full-screen'));
                        moduleFullScreen.append(moduleContent);
                    } else {
                        dashboardSubContainer.removeClass('hidden');
                        var moduleContent = angular.element(document.getElementById('module-content-' + moduleId));
                        var module = angular.element(document.getElementById('module-' + moduleId));
                        module.append(moduleContent);
                        var moduleFullScreen = angular.element(document.getElementById('platform-dashboard-row-full-screen'));
                        moduleFullScreen.remove();
                    }
                });
            },
            templateUrl: 'application/modules/dashboard/html/dashboard.html'
        };
    }

})();