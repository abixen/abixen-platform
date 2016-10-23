var moduleServices = angular.module('moduleServices', ['ngResource']);

moduleServices.factory('Module', ['$resource',
    function ($resource) {
        return $resource('/api/admin/modules/:id', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }]);



