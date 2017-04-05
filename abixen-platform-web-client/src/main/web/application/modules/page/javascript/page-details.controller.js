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
        .module('platformPageModule')
        .controller('PageDetailsController', PageDetailsController);

    PageDetailsController.$inject = [
        '$scope',
        '$rootScope',
        '$state',
        '$log',
        '$uibModalInstance',
        'PageModel',
        'Layout',
        'applicationNavigationItems',
        'toaster',
        'responseHandler',
        'FaSelectionModalWindowServices'
    ];

    function PageDetailsController($scope, $rootScope, $state, $log, $uibModalInstance, PageModel, Layout, applicationNavigationItems, toaster, responseHandler, FaSelectionModalWindowServices) {
        $log.log('PageDetailsController');

        var pageDetails = this;

        new AbstractDetailsController(pageDetails, PageModel, responseHandler, $scope,
            {
                entityId: null,
                initEntity: {
                    page: {
                        icon: 'fa fa-file-text-o'
                    },
                    dashboardModuleDtos: []
                },
                entitySubObject: 'page',
                getValidators: getValidators,
                onSuccessSaveForm: onSuccessSaveForm
            }
        );

        pageDetails.mode = 'add';
        pageDetails.layouts = [];
        pageDetails.cancel = cancel;
        pageDetails.changeIcon = changeIcon;
        pageDetails.changeLayout = changeLayout;

        readLayouts();

        function onSuccessSaveForm() {
            $uibModalInstance.dismiss();
            var newSidebarItem = {
                id: pageDetails.entity.page.id,
                title: pageDetails.entity.page.title,
                state: 'application.page',
                orderIndex: applicationNavigationItems.sidebarItems.length + 1,
                isPage: true,
                iconClass: 'fa fa-file-text-o'
            };
            applicationNavigationItems.addSidebarItem(newSidebarItem);
            toaster.pop(platformParameters.statusAlertTypes.SUCCESS, 'Created', 'The page has been created successfully.');
            $state.go('application.page', {id: newSidebarItem.id});
            $rootScope.$broadcast(platformParameters.events.SIDEBAR_ELEMENT_SELECTED, newSidebarItem.id);
        }

        function cancel() {
            $uibModalInstance.dismiss();
        }

        function readLayouts() {
            Layout.query()
                .$promise
                .then(onQueryResult);

            function onQueryResult(layouts) {
                pageDetails.layouts = layouts;
            }
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


        function changeIcon() {

            pageDetails.selectedIcon = new Array();
            FaSelectionModalWindowServices.openSelectionDialog('Select Icon', pageDetails.selectedIcon, platformParameters.modalSelectionType.SINGLE, 'app-modal-window',
                function () {
                    pageDetails.entity.page.icon = pageDetails.selectedIcon[0];
                });
        }

        function changeLayout(layout) {
            pageDetails.entity.page.layout = layout;
        }
    }
})();