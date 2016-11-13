(function () {

    'use strict';

    angular
        .module('platformPageModule')
        .factory('PageModel', PageModel);

    PageModel.$inject = ['$resource'];

    function PageModel($resource) {

        return $resource('api/pages/:id/model', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }

})();