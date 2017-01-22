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
        .controller('PageDetailController', PageDetailController);

    PageDetailController.$inject = [
        '$scope',
        '$http',
        '$state',
        '$parse',
        '$stateParams',
        '$log',
        'Page',
        'Layout',
        'responseHandler'
    ];

    function PageDetailController($scope, $http, $state, $parse, $stateParams, $log, Page, Layout, responseHandler) {
        $log.log('PageDetailController');

        var pageDetails = this;

        new AbstractDetailsController(pageDetails, Page, responseHandler, $scope,
            {
                getValidators: getValidators,
                onSuccessSaveForm: onSuccessSaveForm
            }
        );
         $scope.layouts = [];

        var readLayouts = function () {
            var queryParameters = {
                page: 0,
                size: 20,
                sort: 'id,asc'
            };

            Layout.query(queryParameters, function (data) {
                $scope.layouts = data.content;
            });
        };

        readLayouts();

        function getValidators() {
            var validators = [];

            validators['title'] =
                [
                    new NotNull(),
                    new Length(6, 40)
                ];

            validators['description'] =
                [
                    new Length(0, 40)
                ];

            return validators;
        }

        function onSuccessSaveForm() {
            $state.go('application.pages.list');
        }
    }
})();