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
        .module('platformApplication')
        .controller('PlatformInitController', PlatformInitController);

    PlatformInitController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$location',
        '$log',
        '$state',
        'applicationNavigationItems',
        '$aside',
        'Page',
        'ModuleType',
        'dashboard',
        'toaster',
        'modalWindow'
    ];

    function PlatformInitController($rootScope,
                                    $scope,
                                    $http,
                                    $location,
                                    $log,
                                    $state,
                                    applicationNavigationItems,
                                    $aside,
                                    Page,
                                    ModuleType,
                                    dashboard,
                                    toaster,
                                    modalWindow) {

        $log.log('PlatformInitController');

        var applicationLoginUrl = '/login';
        var applicationAdminUrl = '/admin';

        $scope.platformUser = null;

        $scope.logout = function () {
            $http.get('/user', {
                headers: {
                    authorization: 'Basic ' + btoa(':')
                }
            }).success(function () {
                window.location = applicationLoginUrl;
            }).error(function (error) {
                $log.error(error);
                window.location = applicationLoginUrl;
            });
        };

        var getPlatformUser = function () {
            $http.get('/user', {}).success(function (platformUser) {
                $scope.platformUser = platformUser;
                $log.log('platformUser: ', $scope.platformUser);
            });
        };

        getPlatformUser();

        $scope.showDropdown = false;

        var newPageButton = {
            id: 1,
            styleClass: 'btn add-new-page-button',
            faIcon: 'fa fa-plus',
            title: 'Add Page',
            onClick: function () {
                $aside.open({
                    placement: 'left',
                    templateUrl: '/application/modules/page/html/add.html',
                    size: 'md',
                    backdrop: false,
                    controller: 'PageDetailsController as pageDetails'
                });
            },
            visible: true,
            disabled: false
        };

        var editModeButton = {
            id: 2,
            styleClass: 'btn edit-mode-button',
            faIcon: 'fa fa-pencil-square-o',
            title: 'Edit Mode',
            onClick: function () {
                $scope.$broadcast(platformParameters.events.ADF_TOGGLE_EDIT_MODE_EVENT);
                editPageButton.visible = !editPageButton.visible;
                $scope.showDropdown = !$scope.showDropdown;
            },
            visible: true,
            disabled: false
        };

        applicationNavigationItems
            .addTopbarItem(newPageButton)
            .addTopbarItem(editModeButton);

        var editPageButton = {
            id: 1,
            title: 'Edit page',
            onClick: function () {
                $scope.$broadcast(platformParameters.events.ADF_EDIT_DASHBOARD_EVENT);
            },
            isSeparator: false
        };

        var deletePageButton = {
            id: 2,
            title: 'Delete Page',
            onClick: function () {
                var deletePage = function () {
                    Page.delete({id: $state.params.id}, function (data) {
                        toaster.pop(platformParameters.statusAlertTypes.SUCCESS, 'Deleted', 'A page has been deleted successfully.');
                        var deletedPageNavigationItem = platformApplicationUtils.findObjectInArray('id', $state.params.id, applicationNavigationItems.sidebarItems);

                        if (deletedPageNavigationItem) {
                            applicationNavigationItems.sidebarItems.splice(applicationNavigationItems.sidebarItems.indexOf(deletedPageNavigationItem), 1);
                        }
                        if (applicationNavigationItems.sidebarItems.length > 0) {
                            $state.go(applicationNavigationItems.sidebarItems[0].state, {id: applicationNavigationItems.sidebarItems[0].id});
                            $scope.$broadcast(platformParameters.events.SIDEBAR_ELEMENT_SELECTED, applicationNavigationItems.sidebarItems[0].id);
                        } else {
                            $state.go('application.welcome');
                        }
                    });
                };

                modalWindow.openConfirmWindow('Delete page?', 'The page will be deleted permanently. Are you sure you want to perform this operation?', 'warning', deletePage);
            },
            isSeparator: false
        };

        var topbarDropdownSeparator = {
            id: 3,
            title: '',
            styleClass: 'divider',
            isSeparator: true

        };

        var newModuleButton = {
            id: 4,
            title: 'Add Module',
            onClick: function () {
                $scope.$broadcast(platformParameters.events.ADF_ADD_WIDGET_EVENT);
            },
            isSeparator: false
        };

        applicationNavigationItems
            .addTopbarDropdownItem(editPageButton)
            .addTopbarDropdownItem(deletePageButton)
            .addTopbarDropdownItem(topbarDropdownSeparator)
            .addTopbarDropdownItem(newModuleButton);

        applicationNavigationItems.setDropdownStyleClass('btn-group action-dropdown');

        var getPages = function () {
            Page.query(function (pages) {
                for (var i = 0; i < pages.length; i++) {
                    applicationNavigationItems.addSidebarItem({
                        id: pages[i].id,
                        title: pages[i].title,
                        state: 'application.page',
                        orderIndex: i,
                        isPage: true,
                        iconClass: 'fa fa-file-text-o'
                    });
                }

                if (pages.length > 0) {
                    var idToChoose;
                    if ($state.params.id) {
                        idToChoose = $state.params.id;
                    } else {
                        idToChoose = pages[0].id;
                    }

                    if ($state.current.name == 'application.page') {
                        $state.go('application.page', {id: idToChoose});
                        $scope.$broadcast(platformParameters.events.SIDEBAR_ELEMENT_SELECTED, idToChoose);
                    }
                } else {
                    editModeButton.visible = false;
                    $state.go('application.welcome');
                }
            })
        };

        var getModuleTypes = function () {
            ModuleType.query(function (data) {

                for (var i = 0; i < data.length; i++) {
                    var moduleType = data[i];

                    dashboard.widget(moduleType.name, {
                        id: null,
                        title: moduleType.title, //'Chart Module',
                        description: moduleType.description,//'Displays a list of links',
                        templateUrl: moduleType.initUrl, //'/application/modules/abixen/chart/html/index.html',
                        //controller: 'ChartModuleController',
                        //controllerAs: 'ChartModuleController',
                        edit: {
                            //templateUrl: '/application/modules/abixen/chart/html/configurationView.html',
                            reload: false
                            //controller: 'linklistEditCtrl'
                        },
                        moduleType: moduleType/*{
                         id: 1,
                         name: 'chart',
                         title: 'Chart visualization',
                         description: 'This is chart visualization module'
                         }*/
                    })
                }

                getPages();
            })
        };

        getModuleTypes();

        $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
            $scope.editMode = false;
            editModeButton.title = 'Edit Mode';
            if (toState.name == 'application.welcome') {
                editModeButton.visible = false;
            } else if (toState.name == 'application.page') {
                editModeButton.visible = applicationNavigationItems.getSidebarItems().length > 0;
            }
            $scope.showDropdown = false;
        });

        var redirectAction = {
            title: 'Admin Page',
            onClick: function () {
                $scope.showDropdown = false;
                window.location = applicationAdminUrl;
            }
        };

        applicationNavigationItems.setRedirectAction(redirectAction);

        $scope.editUser = function () {
            $aside.open({
                placement: 'left',
                templateUrl: '/application/modules/user/html/edit.html',
                size: 'md',
                backdrop: false,
                controller: 'UserDetailsController',
                resolve: {
                    userId: function () {
                        return $scope.platformUser.id;
                    }
                }
            });
        };

        $scope.$on(platformParameters.events.ADF_TOGGLE_EDIT_MODE_RESPONSE_EVENT, function (event, editMode) {
            $log.log('toggle edit mode invoked, editMode: ', editMode);
            $scope.editMode = editMode;
            editModeButton.title = $scope.editMode ? 'View Mode' : 'Edit Mode';
        });
    }
})();