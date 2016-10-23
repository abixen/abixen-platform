var permissionServices = angular.module('permissionServices', ['ngResource']);

permissionServices.factory('Permission', ['$resource',
    function ($resource) {
        return $resource('/api/admin/permissions/:id', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }]);



