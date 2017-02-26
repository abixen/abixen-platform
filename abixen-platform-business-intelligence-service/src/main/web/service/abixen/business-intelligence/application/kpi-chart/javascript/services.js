var platformKpiChartModuleServices = angular.module('platformKpiChartModuleServices', ['ngResource']);

platformKpiChartModuleServices.factory('KpiChartModuleInit', ['$resource',
    function ($resource) {
        return $resource('/api/service/abixen/business-intelligence/application/kpi-chart/init/:id', {}, {
        });
    }]);

platformKpiChartModuleServices.factory('KpiChartModule', ['$resource',
    function ($resource) {
        return $resource('/api/service/abixen/business-intelligence/application/kpi-chart/:id', {}, {
        });
    }]);

platformKpiChartModuleServices.factory('KpiChartModuleConfiguration', ['$resource',
    function ($resource) {
        return $resource('/api/service/abixen/business-intelligence/application/kpi-chart/configuration/:id', {}, {
            update: {method: 'PUT'}
        });
    }]);