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
        .controller('PageController', PageController);

    PageController.$inject = [
        '$scope',
        '$log',
        '$state',
        '$stateParams',
        'PageModel',
        'PageModelParser',
        'toaster',
        'applicationNavigationItems'
    ];

    function PageController($scope,
                            $log,
                            $state,
                            $stateParams,
                            PageModel,
                            PageModelParser,
                            toaster,
                            applicationNavigationItems) {
        $log.log('PageController');

        var model = $scope.model;

        if (!model) {
            // set default model to avoid js console errors, initializing with empty layout
            //TODO - do we need it?
            model = {
                title: 'Sample dashboard',
                structure: '1 (100)',
                rows: [{
                    columns: [{
                        styleClass: 'col-md-12',
                        modules: []
                    }]
                }]
            };
        }

        $scope.model = model;
        $scope.collapsible = true;
        $scope.maximizable = true;

        var getPage = function (pageId) {
            var query = {id: pageId};
            PageModel.query(query, function (data) {

                $scope.pageModelDto = {page: data.page, dashboardModuleDtos: data.dashboardModuleDtos};
                $scope.name = $scope.pageModelDto.page.name;
                $scope.model = PageModelParser.createModel($scope.pageModelDto);
                $scope.collapsible = true;

            });
        };

        if ($stateParams.id) {
            if ($state.current.name == 'application.page') {
                getPage($stateParams.id);
            }
        }

        $scope.$on(platformParameters.events.DASHBOARD_DASHBOARD_CHANGED_EVENT, function (event, name, model) {
            var pageModelDto = PageModelParser.createPageModelDto($scope.pageModelDto.page, model);
            configurePage(pageModelDto);
        });

        $scope.$on(platformParameters.events.PAGE_CHANGED_EVENT, function (event, name, model) {
            var pageModelDto = PageModelParser.createPageModelDto($scope.pageModelDto.page, model);
            savePage(pageModelDto);
        });

        $scope.$on(platformParameters.events.DASHBOARD_LAYOUT_CHANGED_EVENT, function (event, structure) {
            $scope.pageModelDto.page.layout = structure;
        });

        var configurePage = function (pageModelDto) {
            $log.log('save page-model...');

            PageModel.configure({id: pageModelDto.page.id}, pageModelDto, function (data) {
                $log.log('page updated');
                $scope.pageModelDto = {page: data.form.page, dashboardModuleDtos: data.form.dashboardModuleDtos};
                $scope.model = PageModelParser.updateModelModulesNullIds($scope.model, data.form.dashboardModuleDtos);
                toaster.pop(platformParameters.statusAlertTypes.SUCCESS, 'Updated', 'The page has been updated successfully.');
            })
        };

        var savePage = function (pageModelDto) {
            $log.log('save page-model...');

            PageModel.update({id: pageModelDto.page.id}, pageModelDto, function (data) {
                $log.log('page updated');
                $scope.pageModelDto = {page: data.form.page, dashboardModuleDtos: data.form.dashboardModuleDtos};
                $scope.model = PageModelParser.updateModelModulesNullIds($scope.model, data.form.dashboardModuleDtos);
                applicationNavigationItems.editSidebarItem($scope.pageModelDto.page.id, $scope.pageModelDto.page.title, $scope.pageModelDto.page.icon);
                toaster.pop(platformParameters.statusAlertTypes.SUCCESS, 'Updated', 'The page has been updated successfully.');
            })
        };
    }
})();