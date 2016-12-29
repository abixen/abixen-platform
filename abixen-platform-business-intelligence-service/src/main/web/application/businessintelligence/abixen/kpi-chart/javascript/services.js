var platformKpiChartModuleServices = angular.module('platformKpiChartModuleServices', ['ngResource']);

platformKpiChartModuleServices.factory('KpiChartModuleInit', ['$resource',
    function ($resource) {
        return $resource('/application/businessintelligence/abixen/kpi-chart/init/:id', {}, {
        });
    }]);

platformKpiChartModuleServices.factory('KpiChartModule', ['$resource',
    function ($resource) {
        return $resource('/application/businessintelligence/abixen/kpi-chart/:id', {}, {
        });
    }]);

platformKpiChartModuleServices.factory('KpiChartModuleConfiguration', ['$resource',
    function ($resource) {
        return $resource('/application/businessintelligence/abixen/kpi-chart/configuration/:id', {}, {
            update: {method: 'PUT'}
        });
    }]);