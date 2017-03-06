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
        .module('platformLayoutModule')
        .controller('LayoutListController', LayoutListController);

    LayoutListController.$inject = [
        '$log',
        'Layout',
        'applicationNavigationItems',
        '$state'
    ];

    function LayoutListController($log, Layout, applicationNavigationItems, $state) {
        $log.log('LayoutListController');

        var layoutList = this;

        layoutList.searchFields = createSearchFields();
        layoutList.searchFilter = {};

        angular.extend(layoutList, new AbstractListGridController(Layout,
            {
                getTableColumns: getTableColumns,
                filter: layoutList.searchFilter
            }
        ));

        updateNavigation();


        function getTableColumns() {
            return [
                {field: 'id', name: 'Id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
                {field: 'title', name: 'Title', pinnedLeft: true, width: 200}
            ].concat(getAuditingTableColumns());
        }

        function updateNavigation() {
            var newLayoutButton = {
                id: 1,
                styleClass: 'btn add-new-object-button',
                title: 'New Layout',
                faIcon: 'fa fa-plus',
                onClick: function () {
                    $state.go('application.layouts.add');
                },
                visible: true,
                disabled: false
            };

            applicationNavigationItems.setTopbarItem(newLayoutButton);
        }

        function createSearchFields() {
            return [
                {
                    name: 'title',
                    label: 'module.layout.title.label',
                    type: 'input-text'
                }
            ];
        }
    }
})();