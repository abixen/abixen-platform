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
        .controller('PageListController', PageListController);

    PageListController.$inject = [
        '$log',
        'Page',
        'applicationNavigationItems',
        '$state'
    ];

    function PageListController($log, Page, applicationNavigationItems, $state) {
        $log.log('PageListController');

        var pageList = this;

        pageList.searchFields = createSearchFields();
        pageList.searchFilter = {};

        angular.extend(pageList, new AbstractListGridController(Page,
            {
                getTableColumns: getTableColumns,
                filter: pageList.searchFilter
            }
        ));

        updateNavigation();


        function getTableColumns() {
            return [
                {field: 'id', name: 'Id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
                {field: 'title', name: 'Title', width: 200},
                {field: 'description', name: 'Description', width: 200}
            ].concat(getAuditingTableColumns());
        }

        function updateNavigation() {
            applicationNavigationItems.clearTopbarItems();
        }

        function createSearchFields() {
            return [
                {
                    name: 'name',
                    label: 'module.page.name.label',
                    type: 'input-text'
                },
                {
                    name: 'title',
                    label: 'module.page.title.label',
                    type: 'input-text'
                },
                {
                    name: 'description',
                    label: 'module.page.description.label',
                    type: 'input-text'
                }
            ];
        }
    }
})();