var databaseConnectionServices = angular.module('databaseConnectionServices', ['ngResource']);

databaseConnectionServices.factory('DatabaseConnection', ['$resource',
    function ($resource) {
        return $resource('/admin/modules/abixen/multi-visualization/database-connections/:id', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'},
            test: {method: 'POST', url: '/admin/modules/abixen/multi-visualization/database-connections/test', isArray: false},
            tables: {method: 'GET', url: '/admin/modules/abixen/multi-visualization/database-connections/:id/tables', isArray: true},
            tableColumns: {method: 'GET', url: '/admin/modules/abixen/multi-visualization/database-connections/:id/tables/:tableName/columns', isArray: true}
        });
    }]);



