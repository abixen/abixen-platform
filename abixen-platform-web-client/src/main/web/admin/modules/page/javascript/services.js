var pageServices = angular.module('pageServices', ['ngResource']);

pageServices.factory('Page', ['$resource',
    function ($resource) {
        return $resource('/api/admin/pages/:id', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }]);

pageServices.factory('AclRolesPermissions', ['$resource',
    function ($resource) {
        return $resource('api/admin/acls', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }]);



