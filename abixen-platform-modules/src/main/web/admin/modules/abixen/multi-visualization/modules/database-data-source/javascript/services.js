var databaseDataSourceServices = angular.module('databaseDataSourceServices', ['ngResource']);

databaseDataSourceServices.factory('DatabaseDataSource', ['$resource',
    function ($resource) {
        return $resource('/admin/modules/abixen/multi-visualization/database-data-sources/:id', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }]);



