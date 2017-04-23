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
        .module('webContentServiceWebContentModule')
        .controller('WebContentServiceWebContentController', WebContentServiceWebContentController);

    WebContentServiceWebContentController.$inject = ['applicationNavigationItems', 'WebContent','$state'];

    function WebContentServiceWebContentController(applicationNavigationItems, WebContent,$state) {

        var webContentList = this;

        angular.extend(webContentList, new AbstractListGridController(WebContent,
            {
                getTableColumns: getTableColumns
            }
        ));


        updateNavigation();

        function updateNavigation() {
            var newWebContentButton = {
                id: 1,
                styleClass: 'btn add-new-object-button',
                faIcon: 'fa fa-plus',
                title: 'New Web Content',
                onClick: function () {
                    $state.go('application.webContentService.webContent.add');
                },
                visible: true,
                disabled: false
            };

            applicationNavigationItems.setTopbarItem(newWebContentButton);
        }

        function getTableColumns() {
            return [
                {
                    field: 'id',
                    name: 'Id',
                    pinnedLeft: true,
                    enableColumnResizing: false,
                    enableFiltering: false,
                    width: 50
                },
                {field: 'title', name: 'Title', pinnedLeft: true, width: 200},
                {field: 'type', name: 'Type', pinnedLeft: true, width: 200}
            ].concat(getAuditingTableColumns());
        }
    }
})();
