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
        .module('platformUserModule')
        .config(platformUserModuleTranslateConfig);


    platformUserModuleTranslateConfig.$inject = ['$translateProvider'];
    function platformUserModuleTranslateConfig($translateProvider) {

        $translateProvider.useSanitizeValueStrategy('escape');

        $translateProvider.translations('POLISH', {
            'module.user.screenName.label': 'Nazwa wyświetlana',
            'module.user.screenName.placeholder': 'Np. Janek',
            'module.user.username.label': 'E-mail',
            'module.user.username.placeholder': 'Np. joe.doe@abixen.co',
            'module.user.firstName.label': 'Imię',
            'module.user.firstName.placeholder': 'Np. Jan',
            'module.user.middleName.label': 'Drugie imię',
            'module.user.middleName.placeholder': 'Np. Piotr',
            'module.user.lastName.label': 'Nazwisko',
            'module.user.lastName.placeholder': 'Np. Kowalski',
            'module.user.jobTitle.label': 'Stanowisko',
            'module.user.jobTitle.placeholder': 'Np. Administrator',
            'module.user.birthday.label': 'Data urodzin',
            'module.user.gender.label': 'Płeć',
            'module.user.gender.select': 'Wybierz płeć',
            'module.user.language.label': 'Język',
            'module.user.language.select': 'Wybierz język'
        });

        $translateProvider.translations('ENGLISH', {
            'module.user.screenName.label': 'Screen name',
            'module.user.screenName.placeholder': 'E.g. Joe',
            'module.user.username.label': 'E-mail',
            'module.user.username.placeholder': 'E.g. joe.doe@abixen.co',
            'module.user.firstName.label': 'First name',
            'module.user.firstName.placeholder': 'E.g. Joe',
            'module.user.middleName.label': 'Middle name',
            'module.user.middleName.placeholder': 'E.g. Michael',
            'module.user.lastName.label': 'Last name',
            'module.user.lastName.placeholder': 'E.g. Doe',
            'module.user.jobTitle.label': 'Job title',
            'module.user.jobTitle.placeholder': 'E.g. Administrator',
            'module.user.birthday.label': 'Birthday',
            'module.user.gender.label': 'Gender',
            'module.user.gender.select': 'Select gender',
            'module.user.language.label': 'Language',
            'module.user.language.select': 'Select language'
        });
    }
})();