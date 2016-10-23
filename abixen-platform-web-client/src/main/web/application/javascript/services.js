var platformServices = angular.module('platformApplicationServices', ['ngResource', 'adf.provider']);

platformServices.factory('ModuleType', ['$resource',
    function ($resource) {
        return $resource('/api/user/pages/module-types', {}, {
            query: {method: 'GET', isArray: true}
        });
    }]);


platformServices.factory('User', ['$resource',
    function ($resource) {
        return $resource('/api/application/users/:id', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }]);

platformServices.factory('UserRole', ['$resource',
    function ($resource) {
        return $resource('/api/application/users/:id/roles', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }]);

platformServices.factory('UserPassword', ['$resource',
    function ($resource) {
        return $resource('/api/application/users/:id/password', {}, {
            update: {method: 'POST'}
        });
    }]);