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
        '$filter',
        'FaSelectionModalWindowServices'
    ];

    function platformDashboard($rootScope,
                               $log,
                               dashboardData,
                               dashboardUtils,
                               $aside,
                               applicationNavigationItems,
                               $stateParams,
                               Layout,
                               $filter,
                               FaSelectionModalWindowServices) {
        return {
            replace: true,
            restrict: 'EA',
            transclude: false,
            scope: {
                name: '@',
                model: '='
            },
            controller: function ($scope) {
                $scope.$on(platformParameters.events.DASHBOARD_MODULE_DELETED_EVENT, function (event) {
                    $scope.$emit(platformParameters.events.DASHBOARD_DASHBOARD_CHANGED_EVENT, $scope.name, $scope.model);
                });

                $scope.$on(platformParameters.events.DASHBOARD_MODULE_TITLE_CHANGED_EVENT, function (event) {
                    $scope.$emit(platformParameters.events.DASHBOARD_DASHBOARD_CHANGED_EVENT, $scope.name, $scope.model);
                });

                $scope.$on(platformParameters.events.DASHBOARD_MODULE_MOVED_EVENT, function (event) {
                    $scope.$emit(platformParameters.events.DASHBOARD_DASHBOARD_CHANGED_EVENT, $scope.name, $scope.model);
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

                $scope.$on(platformParameters.events.DASHBOARD_TOGGLE_EDIT_MODE_EVENT, function (event) {
                    $scope.toggleEditMode();
                    $scope.$emit(platformParameters.events.DASHBOARD_TOGGLE_EDIT_MODE_RESPONSE_EVENT, $scope.editMode);
                });

                // edit dashboard settings
                $scope.editDashboardDialog = function () {
                    var editDashboardScope = $scope.$new();

                    editDashboardScope.pageDetails = {
                        entity: {
                            page: {
                                title: $scope.model.title,
                                description: $scope.model.description,
                                icon: $scope.model.icon
                            }
                        },
                        saveForm: saveForm,
                        cancel: cancelDialog,
                        changeIcon: changeIcon,
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
                        templateUrl: 'application/modules/page/html/edit.html',
                        size: 'md',
                        backdrop: 'static'
                    });

                    function changeLayoutOnEditPage(layout) {
                        $log.info('change layout to ', layout);
                        dashboardUtils.changeLayout($scope.model, layout);
                        $rootScope.$broadcast(platformParameters.events.DASHBOARD_LAYOUT_CHANGED_EVENT, layout);
                    }

                    function saveForm() {
                        $scope.model.title = editDashboardScope.pageDetails.entity.page.title;
                        $scope.model.description = editDashboardScope.pageDetails.entity.page.description;
                        $scope.model.icon = editDashboardScope.pageDetails.entity.page.icon;
                        instance.dismiss();
                        editDashboardScope.$destroy();
                        applicationNavigationItems.editSidebarItem($stateParams.id, $scope.model.title);
                        $scope.$emit(platformParameters.events.PAGE_CHANGED_EVENT, $scope.name, $scope.model);
                    }

                    function changeIcon() {
                        var selectedIcons = new Array();
                        FaSelectionModalWindowServices.openSelectionDialog('Select Icon', selectedIcons, platformParameters.modalSelectionType.SINGLE, 'app-modal-window',
                            function () {
                                editDashboardScope.pageDetails.entity.page.icon = selectedIcons[0];
                            });
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


                    function cancelDialog() {
                        // close modal and destroy the scope
                        instance.dismiss();
                        editDashboardScope.$destroy();
                    }
                };

                $scope.$on(platformParameters.events.DASHBOARD_EDIT_DASHBOARD_EVENT, function (event) {
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
                        $scope.$emit(platformParameters.events.DASHBOARD_DASHBOARD_CHANGED_EVENT, $scope.name, $scope.model);
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

                $scope.$on(platformParameters.events.DASHBOARD_ADD_MODULE_EVENT, function (event) {
                    $scope.addModuleDialog();
                });
            },
            link: function ($scope) {

                $scope.$on('FULL_SCREEN_MODE', function (event, moduleId, fullScreenMode, callback) {
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
                    callback();
                });
            },
            templateUrl: 'application/modules/dashboard/html/dashboard.html'
        };
    }

})();