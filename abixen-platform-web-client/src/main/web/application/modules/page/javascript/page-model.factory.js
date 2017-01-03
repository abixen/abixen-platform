(function () {

    'use strict';

    angular
        .module('platformPageModule')
        .factory('PageModel', PageModel);

    PageModel.$inject = ['$resource'];

    function PageModel($resource) {

        return $resource('/api/page-configurations/:id', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'},
            configure: {
                method: 'PUT',
                params: {id: '@id'},
                url: '/api/page-configurations/:id/configure'
            }
        });
    }

})();