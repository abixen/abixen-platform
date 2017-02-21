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
            'module.multivisualisation.refreshPreviewData': 'Odśwież dane',
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
            'module.multivisualisation.configuration.headerKey.label': 'Klucz',
            'module.multivisualisation.configuration.preview.label': 'Podgląd danych',
            'module.multivisualisation.configuration.preview.headerX.label': 'Domena',
            'module.multivisualisation.configuration.preview.headerY.label': 'Wartość',
            'module.multivisualisation.configuration.preview.headerKey.label': 'Klucz',
            'module.multivisualisation.configuration.preview.headerValue.label': 'Wartość',
            'module.multivisualisation.configuration.axisYSelect.label': 'Oś Y',
            'module.multivisualisation.configuration.valuesSelect.label': 'Wartości',
            'module.multivisualisation.configuration.chartType.table.name': 'Tabela',
            'module.multivisualisation.configuration.chartType.table.description': 'Tylko tabelaryczny widok. Ten widok służy do prezentacji danych w formie tabeli bez wykresu',
            'module.multivisualisation.configuration.chartType.lineChart.name': 'Wykres liniowy',
            'module.multivisualisation.configuration.chartType.lineChart.description': 'Tylko wykres liniowy. Ten widok służy do prezentacji danych w formie wykresu liniowego.',
            'module.multivisualisation.configuration.chartType.lineChartWithTable.name': 'Wykres liniowy z tabelą',
            'module.multivisualisation.configuration.chartType.lineChartWithTable.description': 'Wykres liniowy z tabelą. Ten widok służy do prezentacji danych w formie  wykresu liniowego i tabeli.',
            'module.multivisualisation.configuration.chartType.cumalativeLineChart.name': 'Skumulowany wykres liniowy',
            'module.multivisualisation.configuration.chartType.cumalativeLineChart.description': 'Skumulowany wykres liniowy. Ten widok służy do prezentacji danych w formie skumulowanego wykresu liniwego.',
            'module.multivisualisation.configuration.chartType.cumalativeLineChartWithTable.name': 'Skumulowany wykres liniowy z tabelą',
            'module.multivisualisation.configuration.chartType.cumalativeLineChartWithTable.description': 'Skumulowany wykres liniowy z tabela. Ten widok służy do prezentacji w danych formie skumulowanego wykresu liniwego i tabeli.',
            'module.multivisualisation.configuration.chartType.stackedAreaChart.name': 'Skumulowany wykres obszarowy',
            'module.multivisualisation.configuration.chartType.stackedAreaChart.description': 'Skumulowany wykres obszarowy. Ten widok służy do prezentacji danych w formie skumulowanego wykresu obszarowego.',
            'module.multivisualisation.configuration.chartType.stackedAreaChartWithTable.name': 'Skumulowany wykres obszarowy z tabelą',
            'module.multivisualisation.configuration.chartType.stackedAreaChartWithTable.description': 'Skumulowany wykres obszarowy z tabelą.  Ten widok służy do prezentacji danych w formie skumulowanego wykresu obszarowego i tabeli.',
            'module.multivisualisation.configuration.chartType.multiColumnChart.name': 'Wykres wielokolumnowy',
            'module.multivisualisation.configuration.chartType.multiColumnChart.description': 'Wykres wielokolumnowy.  Ten widok służy do prezentacji danych w formie wielokolumnowego wykresu.',
            'module.multivisualisation.configuration.chartType.multiColumnChartWithTable.name': 'Wykres wielokolumnowy z tabelą',
            'module.multivisualisation.configuration.chartType.multiColumnChartWithTable.description': 'Wykres wielokolumnowy z tabelą.  Ten widok służy do prezentacji danych w formie wielokolumnowego wykresu i tabeli.',
            'module.multivisualisation.configuration.chartType.discreteColumnChart.name': 'Dyskretny wykres kolumnowy',
            'module.multivisualisation.configuration.chartType.discreteColumnChart.description': 'Dyskretny wykres kolumnowy. Ten widok służy do prezentacji danych w formie dyskretnego wykresu kolumnowego.',
            'module.multivisualisation.configuration.chartType.discreteColumnChartWithTable.name': 'Dyskretny wykres kolumnowy z tabelą',
            'module.multivisualisation.configuration.chartType.discreteColumnChartWithTable.description': 'Dyskretny wykres kolumnowy. Ten widok służy do prezentacji danych w formie dyskretnego wykresu kolumnowego i tabeli.',
            'module.multivisualisation.configuration.chartType.historicalColumnChart.name': 'Historyczny wykres kolumnowy',
            'module.multivisualisation.configuration.chartType.historicalColumnChart.description': 'Historyczny wykres kolumnowy. Ten widok służy do prezentacji danych w formie historycznego wykresu kolumnowego.',
            'module.multivisualisation.configuration.chartType.historicalColumnChartWithTable.name': 'Historyczny wykres kolumnowy z tabelą',
            'module.multivisualisation.configuration.chartType.historicalColumnChartWithTable.description': 'Historyczny wykres kolumnowy z tabelą. Ten widok służy do prezentacji danych w formie historycznego wykresu kolumnowego i tabeli.',
            'module.multivisualisation.configuration.chartType.multiBarChart.name': 'Wykres wielosłupkowy',
            'module.multivisualisation.configuration.chartType.multiBarChart.description': 'Wykres wielosłupkowy. Ten widok służy do prezentacji danych w formie wykresu wielosłupkowego.',
            'module.multivisualisation.configuration.chartType.multiBarChartWithTable.name': 'Wykres wielosłupkowy z tabelą',
            'module.multivisualisation.configuration.chartType.multiBarChartWithTable.description': 'Wykres wielosłupkowy z tabelą. Ten widok służy do prezentacji danych w formie wykresu wielosłupkowego i tabeli.',
            'module.multivisualisation.configuration.chartType.pieChart.name': 'Wykres kołowy',
            'module.multivisualisation.configuration.chartType.pieChart.description': 'Wykres kołowy. Ten widok służy do prezentacji danych w formie wykresu kołowego.',
            'module.multivisualisation.configuration.chartType.pieChartWithTable.name': 'Wykres kołowy z tabelą',
            'module.multivisualisation.configuration.chartType.pieChartWithTable.description': 'Wykres kołowy z tabelą. Ten widok służy do prezentacji danych w formie wykresu kołowego i tabeli.',
            'module.multivisualisation.configuration.chartType.scatterChart.name': 'Wykres punktowy',
            'module.multivisualisation.configuration.chartType.scatterChart.description': 'Wykres punktowy.  Ten widok służy do prezentacji danych w formie wykresu punktowego.',
            'module.multivisualisation.configuration.chartType.scatterChartWithTable.name': 'Wykres punktowy z tabelą',
            'module.multivisualisation.configuration.chartType.scatterChartWithTable.description': 'Wykres punktowy z tabelą. Ten widok służy do prezentacji danych w formie wykresu pumktowego i tabeli.',
            'module.multivisualisation.configuration.chartType.donutChart.name': 'Wykres pierścieniowy',
            'module.multivisualisation.configuration.chartType.donutChart.description': 'Wykres pierścieniowy. Ten widok służy do prezentacji danych w formie wykresu pierścieniowego.',
            'module.multivisualisation.configuration.chartType.donutChartWithTable.name': 'Wykres pierścieniowy z tabelą',
            'module.multivisualisation.configuration.chartType.donutChartWithTable.description': 'Wykres pierścieniowy z tabelą. Ten widok służy do prezentacji danych w formie wykresu pierścieniowego i tabeli.',
            'module.multivisualisation.configuration.chartType.lineChartWithFocus.name': 'Wykres liniowy z możliwością powiększania',
            'module.multivisualisation.configuration.chartType.lineChartWithFocus.description': 'Wykres liniowy z możliwością powiększania. Ten widok służy do prezentacji danych w formie wykresu liniowego z możliwościa powiększania wybranego obszaru.',
            'module.multivisualisation.configuration.chartType.lineChartWithFocusAndTable.name': 'Wykres liniowy z możliwością powiększania i tabelą',
            'module.multivisualisation.configuration.chartType.lineChartWithFocusAndTable.description': 'Wykres liniowy z możliwością powiększania i tabelą. Ten widok służy do prezentacji danych w formie wykresu liniowego z możliwościa powiększania wybranego obszaru i tabeli.',
            'module.multivisualisation.configuration.chartType.lineAndBarChartWithFocus.name': 'Wykres liniowo słupkowy możliwością powiększania',
            'module.multivisualisation.configuration.chartType.lineAndBarChartWithFocus.description': 'Wykres liniowo słupkowy możliwością powiększania. Ten widok służy do prezentacji danych w formie wykresu liniowo słupkowego z możliwościa powiększania wybranego obszaru.',
            'module.multivisualisation.configuration.chartType.llineAndBarChartWithFocusAndTable.name': 'Wykres liniowo słupkowy możliwością powiększania i tabelą',
            'module.multivisualisation.configuration.chartType.llineAndBarChartWithFocusAndTable.description': 'Wykres liniowo słupkowy możliwością powiększania i tabelą. Ten widok służy do prezentacji danych w formie wykresu liniowo słupkowego z możliwościa powiększania wybranego obszaru i tabeli.'

        });

        $translateProvider.translations('ENGLISH', {
            'module.multivisualisation.save': 'Save',
            'module.multivisualisation.next': 'Next',
            'module.multivisualisation.prev': 'Prev',
            'module.multivisualisation.refreshPreviewData': 'Refresh preview data',
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
            'module.multivisualisation.configuration.headerKey.label': 'Key',
            'module.multivisualisation.configuration.preview.label': 'Preview data',
            'module.multivisualisation.configuration.preview.headerX.label': 'Domain',
            'module.multivisualisation.configuration.preview.headerY.label': 'Value',
            'module.multivisualisation.configuration.preview.headerKey.label': 'Key',
            'module.multivisualisation.configuration.preview.headerValue.label': 'Value',
            'module.multivisualisation.configuration.axisYSelect.label': 'Axis Y',
            'module.multivisualisation.configuration.valuesSelect.label': 'Values',
            'module.multivisualisation.configuration.chartType.table.name': 'Table view',
            'module.multivisualisation.configuration.chartType.table.description': 'Table view only. Use this view to presents table data without chart visualisation.',
            'module.multivisualisation.configuration.chartType.lineChart.name': 'Line chart view',
            'module.multivisualisation.configuration.chartType.lineChart.description': 'Line chart only. Use this view to present data as a line chart.',
            'module.multivisualisation.configuration.chartType.lineChartWithTable.name': 'Line chart with table view',
            'module.multivisualisation.configuration.chartType.lineChartWithTable.description': 'Line chart with table view. Use this view to present data as a line chart and table.',
            'module.multivisualisation.configuration.chartType.cumalativeLineChart.name': 'Cumulative line chart view',
            'module.multivisualisation.configuration.chartType.cumalativeLineChart.description': 'Cumulative line chart view. Use this view to present data as a cumulative line chart.',
            'module.multivisualisation.configuration.chartType.cumalativeLineChartWithTable.name': 'Cumulative line chart with table view',
            'module.multivisualisation.configuration.chartType.cumalativeLineChartWithTable.description': 'Cumulative line chart with table view. Use this view to present data as a cumulative line chart and table.',
            'module.multivisualisation.configuration.chartType.stackedAreaChart.name': 'Stacked area chart view',
            'module.multivisualisation.configuration.chartType.stackedAreaChart.description': 'Stacked area chart view. Use this view to present data as a stacked area chart.',
            'module.multivisualisation.configuration.chartType.stackedAreaChartWithTable.name': 'Stacked area chart with table view',
            'module.multivisualisation.configuration.chartType.stackedAreaChartWithTable.description': 'Stacked area chart with table view. Use this view to present data as a stacked area chart and table.',
            'module.multivisualisation.configuration.chartType.multiColumnChart.name': 'Multi column chart view',
            'module.multivisualisation.configuration.chartType.multiColumnChart.description': 'Multi column chart view. Use this view to present data as a multi column chart.',
            'module.multivisualisation.configuration.chartType.multiColumnChartWithTable.name': 'Multi column chart with table view',
            'module.multivisualisation.configuration.chartType.multiColumnChartWithTable.description': 'Multi column chart with table view. Use this view to present data as a multi column chart and table.',
            'module.multivisualisation.configuration.chartType.discreteColumnChart.name': 'Discrete column chart view',
            'module.multivisualisation.configuration.chartType.discreteColumnChart.description': 'Discrete column chart view. Use this view to present data as a discrete column chart.',
            'module.multivisualisation.configuration.chartType.discreteColumnChartWithTable.name': 'Discrete column chart with table view',
            'module.multivisualisation.configuration.chartType.discreteColumnChartWithTable.description': 'Discrete column chart with table view. Use this view to present data as a discrete column chart amd table.',
            'module.multivisualisation.configuration.chartType.historicalColumnChart.name': 'Historical column chart view',
            'module.multivisualisation.configuration.chartType.historicalColumnChart.description': 'Historical column chart view. Use this view to present data as a historical column chart.',
            'module.multivisualisation.configuration.chartType.historicalColumnChartWithTable.name': 'Historical column chart with table view',
            'module.multivisualisation.configuration.chartType.historicalColumnChartWithTable.description': 'Historical column chart with table view. Use this view to present data as a historical column chart amd table.',
            'module.multivisualisation.configuration.chartType.multiBarChart.name': 'Multi bar chart view',
            'module.multivisualisation.configuration.chartType.multiBarChart.description': 'Multi bar chart view. Use this view to present data as a multi bar chart.',
            'module.multivisualisation.configuration.chartType.multiBarChartWithTable.name': 'Multi bar chart with table view',
            'module.multivisualisation.configuration.chartType.multiBarChartWithTable.description': 'Multi bar chart with table view. Use this view to present data as a multi bar chart and table.',
            'module.multivisualisation.configuration.chartType.pieChart.name': 'Pie chart view',
            'module.multivisualisation.configuration.chartType.pieChart.description': 'Pie chart view. Use this view to present data as a pie chart.',
            'module.multivisualisation.configuration.chartType.pieChartWithTable.name': 'Pie chart with table view',
            'module.multivisualisation.configuration.chartType.pieChartWithTable.description': 'Pie chart with table view. Use this view to present data as a pie chart and table.',
            'module.multivisualisation.configuration.chartType.scatterChart.name': 'Scatter chart view',
            'module.multivisualisation.configuration.chartType.scatterChart.description': 'Scatter chart view. Use this view to present data as a scatter chart.',
            'module.multivisualisation.configuration.chartType.scatterChartWithTable.name': 'Scatter chart with table view',
            'module.multivisualisation.configuration.chartType.scatterChartWithTable.description': 'Scatter chart with table view. Use this view to present data as a scatter chart and table.',
            'module.multivisualisation.configuration.chartType.donutChart.name': 'Donut chart view',
            'module.multivisualisation.configuration.chartType.donutChart.description': 'Donut chart view. Use this view to present data as a donut chart.',
            'module.multivisualisation.configuration.chartType.donutChartWithTable.name': 'Donut chart with table view',
            'module.multivisualisation.configuration.chartType.donutChartWithTable.description': 'Donut chart with table view. Use this view to present data as a donut chart and table.',
            'module.multivisualisation.configuration.chartType.lineChartWithFocus.name': 'Line Chart with focus mode',
            'module.multivisualisation.configuration.chartType.lineChartWithFocus.description': 'Line Chart with focus mode. Use this view to present data as a donut line Chart with focus mode.',
            'module.multivisualisation.configuration.chartType.lineChartWithFocusAndTable.name': 'Line Chart with focus mode with table view',
            'module.multivisualisation.configuration.chartType.lineChartWithFocusAndTable.description': 'Line Chart with focus mode with table view. Use this view to present data as a line Chart with focus mode and table.',
            'module.multivisualisation.configuration.chartType.lineAndBarChartWithFocus.name': 'Line and bar Chart with focus mode',
            'module.multivisualisation.configuration.chartType.lineAndBarChartWithFocus.description': 'Line and bar Chart with focus mode. Use this view to present data as a line and bar Chart with focus mode.',
            'module.multivisualisation.configuration.chartType.llineAndBarChartWithFocusAndTable.name': 'Line and bar Chart with focus mode with table view',
            'module.multivisualisation.configuration.chartType.llineAndBarChartWithFocusAndTable.description': 'Line and bar Chart with focus mode with table view. Use this view to present data as a line and bar Chart with focus mode and table.'
        });
    }
})();