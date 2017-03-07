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
        '$state',
        '$stateParams',
        '$log',
        'Page',
        'Layout',
        'responseHandler',
        'applicationNavigationItems'
    ];

    function PageDetailsController($scope, $state, $stateParams, $log, Page, Layout, responseHandler, applicationNavigationItems) {
        $log.log('PageDetailsController');

        var pageDetails = this;

        new AbstractDetailsController(pageDetails, Page, responseHandler, $scope,
            {
                entityId: $stateParams.id,
                getValidators: getValidators,
                onSuccessSaveForm: onSuccessSaveForm
            }
        );

        pageDetails.layouts = [];

        findLayouts();
        updateNavigation();

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

        function findLayouts() {
            var queryParameters = {
                page: 0,
                size: 1000,
                sort: 'id,asc'
            };

            Layout.query(queryParameters)
                .$promise
                .then(onFindLayouts);

            function onFindLayouts(data) {
                pageDetails.layouts = data.content;
            }
        }

        function updateNavigation() {
            applicationNavigationItems.setTopbarItem([]);
        }

        function onSuccessSaveForm() {
            $state.go('application.pages.list');
        }
    }
})();