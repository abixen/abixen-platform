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
        .module('platformDashboardModule')
        .config(platformDashboardModuleTranslateConfig);


    platformDashboardModuleTranslateConfig.$inject = ['$translateProvider'];
    function platformDashboardModuleTranslateConfig($translateProvider) {

        $translateProvider.useSanitizeValueStrategy('escape');

        $translateProvider.translations('POLISH', {
            'dashboard.label.createNewModule': 'Utwórz nowy moduł',
            'dashboard.module.message.permissionDenied': 'Nie posiadasz uprawnień do tego modułu.',
            'dashboard.module.message.temporarilyUnavailable': 'Moduł jest tymczasowo niedostępny.',
            'dashboard.module.message.notConfigured': 'Moduł nie został jeszcze skonfigurowany.'
        });

        $translateProvider.translations('ENGLISH', {
            'dashboard.label.createNewModule': 'Create new module',
            'dashboard.module.message.permissionDenied': 'You have no permission to this module.',
            'dashboard.module.message.temporarilyUnavailable': 'Module is temporarily unavailable.',
            'dashboard.module.message.notConfigured': 'Module has not yet been configured.'
        });
    }
})();