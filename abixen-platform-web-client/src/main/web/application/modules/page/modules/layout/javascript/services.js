var structureServices = angular.module('layoutServices', ['ngResource']);

structureServices.factory('Layout', ['$resource',
    function ($resource) {
        return $resource('/api/dashboard/:id', {}, {
            query: {method: 'GET'/*, isArray: false*/},
            update: {method: 'PUT'}
        });
    }]);