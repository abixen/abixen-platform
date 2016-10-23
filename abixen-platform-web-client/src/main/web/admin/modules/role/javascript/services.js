var roleServices = angular.module('roleServices', ['ngResource']);

roleServices.factory('Role', ['$resource',
    function ($resource) {
        return $resource('/api/admin/roles/:id', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }]);

roleServices.factory('RolePermission', ['$resource',
    function ($resource) {
        return $resource('/api/admin/roles/:id/permissions', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }]);



