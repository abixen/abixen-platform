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
        .module('platformChartModule')
        .config(platformChartModuleTranslateConfig);


    platformChartModuleTranslateConfig.$inject = ['$translateProvider'];
    function platformChartModuleTranslateConfig($translateProvider) {

        $translateProvider.useSanitizeValueStrategy('escape');

        $translateProvider.translations('POLISH', {
            'module.multivisualisation.save': 'Zapisz',
            'module.multivisualisation.next': 'Dalej',
            'module.multivisualisation.prev': 'Wstecz',
            'module.multivisualisation.step.moduleType': 'Typ modułu',
            'module.multivisualisation.step.dataSource': 'Źródło danych',
            'module.multivisualisation.step.configuration': 'Konfiguracja',
            'module.multivisualisation.step.preview': 'Podgląd',
            'module.multivisualisation.configuration.addSeries': 'Dodaj serie',
            'module.multivisualisation.configuration.domainAxis.label': 'Nazwa osi domenowej',
            'module.multivisualisation.configuration.domainAxis.placeholder': 'Wprowadź nazwę osi domenowej',
            'module.multivisualisation.configuration.valuesAxis.label': 'Nazwa osi z wartościami',
            'module.multivisualisation.configuration.valuesAxis.placeholder': 'Wprowadź nazwę osi z wartościami',
            'module.multivisualisation.configuration.domainSelect.label': 'Domena',
            'module.multivisualisation.configuration.seriesSelect.label': 'Nazwa Serie',
            'module.multivisualisation.configuration.preview.label': 'Podgląd danych',
            'module.multivisualisation.configuration.preview.headerX.label': 'Domena',
            'module.multivisualisation.configuration.preview.headerY.label': 'Wartość',
            'module.multivisualisation.configuration.preview.headerKey.label': 'Klucz',
            'module.multivisualisation.configuration.preview.headerValue.label': 'Wartość',
            'module.multivisualisation.configuration.axisYSelect.label': 'Oś Y',
            'module.multivisualisation.configuration.valuesSelect.label': 'Wartości'
        });

        $translateProvider.translations('ENGLISH', {
            'module.multivisualisation.save': 'Save',
            'module.multivisualisation.next': 'Next',
            'module.multivisualisation.prev': 'Prev',
            'module.multivisualisation.step.moduleType': 'Module type',
            'module.multivisualisation.step.dataSource': 'Data source',
            'module.multivisualisation.step.configuration': 'Configuration',
            'module.multivisualisation.step.preview': 'Preview',
            'module.multivisualisation.configuration.addSeries': 'Add Series',
            'module.multivisualisation.configuration.domainAxis.label': 'Domain axis label',
            'module.multivisualisation.configuration.domainAxis.placeholder': 'Put domain Axis display name',
            'module.multivisualisation.configuration.valuesAxis.label': 'Values axis label',
            'module.multivisualisation.configuration.valuesAxis.placeholder': 'Put values Axis display name',
            'module.multivisualisation.configuration.domainSelect.label': 'Domain',
            'module.multivisualisation.configuration.seriesSelect.label': 'Series name',
            'module.multivisualisation.configuration.preview.label': 'Preview data',
            'module.multivisualisation.configuration.preview.headerX.label': 'Domain',
            'module.multivisualisation.configuration.preview.headerY.label': 'Value',
            'module.multivisualisation.configuration.preview.headerKey.label': 'Key',
            'module.multivisualisation.configuration.preview.headerValue.label': 'Value',
            'module.multivisualisation.configuration.axisYSelect.label': 'Axis Y',
            'module.multivisualisation.configuration.valuesSelect.label': 'Values'
        });
    }
})();