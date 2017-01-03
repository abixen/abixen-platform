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
        .module('platformThemeModule')
        .controller('ThemeListController', ThemeListController);

    ThemeListController.$inject = [
        '$log',
        'Theme',
        'applicationNavigationItems',
        '$state'
    ];

    function ThemeListController($log, Theme, applicationNavigationItems, $state) {
        $log.log('ThemeListController');

        var themeList = this;

        angular.extend(themeList, new AbstractListGridController(Theme,
            {
                getTableColumns: getTableColumns
            }
        ));

        updateNavigation();

        function getTableColumns() {
            return [
                {field: 'id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
                {field: 'title', pinnedLeft: true, width: 200},
                {field: 'createdBy.username', name: 'Created By', width: 200},
                {
                    field: 'createdDate',
                    width: 200,
                    cellFilter: "date:'" + platformParameters.formats.DATE_TIME_FORMAT + "'"
                },
                {field: 'lastModifiedBy.username', name: 'Last Modified By', width: 200},
                {
                    field: 'lastModifiedDate',
                    width: 200,
                    cellFilter: "date:'" + platformParameters.formats.DATE_TIME_FORMAT + "'"
                }
            ];
        }

        function updateNavigation() {
            var newThemeButton = {
                id: 1,
                styleClass: 'btn add-new-object-button',
                faIcon: 'fa fa-plus',
                title: 'New Theme',
                onClick: function () {
                    $state.go('application.themes.add');
                },
                visible: true,
                disabled: false
            };

            applicationNavigationItems.setTopbarItem(newThemeButton);
        }
    }
})();