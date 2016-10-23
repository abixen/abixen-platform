var userServices = angular.module('userServices', ['ngResource']);

userServices.factory('User', ['$resource',
    function ($resource) {
        return $resource('/api/admin/users/:id', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }]);

userServices.factory('UserRole', ['$resource',
    function ($resource) {
        return $resource('/api/admin/users/:id/roles', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }]);



