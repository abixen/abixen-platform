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
        .module('platformNavigationModule')
        .provider('applicationNavigationItems', applicationNavigationItems);

    function applicationNavigationItems() {
        var sidebarItems = [];
        var topbarItems = [];
        var topbarDropdownItems = [];
        var redirectAction = {};
        var dropdownStyleClass = 'btn-group';
        return {
            addSidebarItem: function (page) {
                sidebarItems.push(page);
                return this;
            },
            editSidebarItem: function (id, title, icon) {
                for (var i = 0; i < sidebarItems.length; i++) {
                    if (id === sidebarItems[i].id) {
                        sidebarItems[i].title = title;
                        sidebarItems[i].iconClass = icon;
                    }
                }
                return this;
            },
            getSidebarItems: function () {
                return sidebarItems;
            },
            addTopbarItem: function (button) {
                topbarItems.push(button);
                return this;
            },
            setTopbarItem: function (button) {
                topbarItems.length = 0;
                topbarItems.push(button);
                return this;
            },
            clearTopbarItems: function () {
                topbarItems.length = 0;
                return this;
            },
            getTopbarItems: function (button) {
                return topbarItems;
            },
            addTopbarDropdownItem: function (topbarDropdownItem) {
                topbarDropdownItems.push(topbarDropdownItem);
                return this;
            },
            setRedirectAction: function (redirectActionItem) {
                redirectAction = redirectActionItem;
                return this;
            },
            getRedirectAction: function () {
                return redirectAction;
            },
            setDropdownStyleClass: function (styleClass) {
                dropdownStyleClass = styleClass;
                return this;
            },
            getDropdownStyleClass: function () {
                return dropdownStyleClass;
            },
            $get: function () {
                return {
                    sidebarItems: sidebarItems,
                    topbarItems: topbarItems,
                    topbarDropdownItems: topbarDropdownItems,
                    redirectAction: redirectAction,
                    dropdownStyleClass: dropdownStyleClass,
                    addSidebarItem: this.addSidebarItem,
                    editSidebarItem: this.editSidebarItem,
                    getSidebarItems: this.getSidebarItems,
                    addTopbarItem: this.addTopbarItem,
                    setTopbarItem: this.setTopbarItem,
                    getTopbarItems: this.getTopbarItems,
                    clearTopbarItems: this.clearTopbarItems,
                    addTopbarDropdownItem: this.addTopbarDropdownItem,
                    setRedirectAction: this.setRedirectAction,
                    getRedirectAction: this.getRedirectAction,
                    setDropdownStyleClass: this.setDropdownStyleClass,
                    getDropdownStyleClass: this.getDropdownStyleClass,
                };
            }
        };
    }
})();