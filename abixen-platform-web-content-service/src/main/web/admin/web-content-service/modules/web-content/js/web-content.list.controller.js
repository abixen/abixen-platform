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

    WebContentServiceWebContentController.$inject = ['applicationNavigationItems'];

    function WebContentServiceWebContentController(applicationNavigationItems) {
        updateNavigation();

        function updateNavigation() {
            var newTemplateButton = {
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

            applicationNavigationItems.setTopbarItem(newTemplateButton);
        }
    }
})();