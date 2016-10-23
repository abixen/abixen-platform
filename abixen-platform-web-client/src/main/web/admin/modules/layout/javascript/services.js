var layoutServices = angular.module('layoutServices', ['ngResource']);

layoutServices.factory('Layout', ['$resource',
    function ($resource) {
        return $resource('/api/admin/layouts/:id', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }]);
