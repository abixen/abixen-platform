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
        .module('webContentConfigurationModule')
        .config(WebContentConfigurationModuleConfig);


    WebContentConfigurationModuleConfig.$inject = ['$translateProvider'];
    function WebContentConfigurationModuleConfig($translateProvider) {

        $translateProvider.useSanitizeValueStrategy('escape');

        $translateProvider.translations('POLISH', {
            'wizard.contentSelectionStep.search.title.label': 'Tytuł',
            'wizard.navigation.button.prev': 'Wstecz',
            'wizard.navigation.button.next': 'Dalej',
            'wizard.navigation.button.save': 'Zapisz',
            'wizard.navigation.tab.contentSelection': 'Wybór zawartości',
            'wizard.navigation.tab.preview': 'Podgląd'
        });

        $translateProvider.translations('ENGLISH', {
            'wizard.contentSelectionStep.search.title.label': 'Title',
            'wizard.navigation.button.prev': 'Prev',
            'wizard.navigation.button.next': 'Next',
            'wizard.navigation.button.save': 'Save',
            'wizard.navigation.tab.contentSelection': 'Content select',
            'wizard.navigation.tab.preview': 'Preview'
        });
    }
})();