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
        .module('platformApplication')
        .config(platformApplicationTranslateConfig);


    platformApplicationTranslateConfig.$inject = ['$translateProvider'];
    function platformApplicationTranslateConfig($translateProvider) {

        $translateProvider.useSanitizeValueStrategy('escape');

        $translateProvider.translations('POLISH', {
            'application.button.add': 'Dodaj',
            'application.button.cancel': 'Anuluj',
            'application.button.addPage': 'Dodaj Stronę',
            'application.button.viewMode': 'Tryb Odczytu',
            'application.button.editMode': 'Tryb Edycji',
            'application.button.action': 'Akcja',
            'application.button.action.editPage': 'Edytuj Stronę',
            'application.button.action.deletePage': 'Usuń Stronę',
            'application.button.action.addModule': 'Dodaj Moduł'
        });

        $translateProvider.translations('ENGLISH', {
            'application.button.add': 'Add',
            'application.button.cancel': 'Cancel',
            'application.button.addPage': 'Add Page',
            'application.button.viewMode': 'View Mode',
            'application.button.editMode': 'Edit Mode',
            'application.button.action': 'Action',
            'application.button.action.editPage': 'Edit Page',
            'application.button.action.deletePage': 'Delete Page',
            'application.button.action.addModule': 'Add Module'
        });

        $translateProvider.translations('RUSSIAN', {
            'application.button.add': 'Добавить',
            'application.button.cancel': 'Отмена',
            'application.button.addPage': 'Добавить Страницу',
            'application.button.viewMode': 'Просмотр',
            'application.button.editMode': 'Редактирование',
            'application.button.action': 'Действие',
            'application.button.action.editPage': 'Редактировать Страницу',
            'application.button.action.deletePage': 'Удалить Страницу',
            'application.button.action.addModule': 'Добавить Модуль'
        });

        $translateProvider.translations('UKRAINIAN', {
            'application.button.add': 'Додати',
            'application.button.cancel': 'Відміна',
            'application.button.addPage': 'Додати Сторінку',
            'application.button.viewMode': 'Перегляд',
            'application.button.editMode': 'Редагування',
            'application.button.action': 'Дія',
            'application.button.action.editPage': 'Редагувати Сторінку',
            'application.button.action.deletePage': 'Видалити Сторінку',
            'application.button.action.addModule': 'Додати Модуль'
        });
    }
})();