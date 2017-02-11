(function () {

    'use strict';

    angular
        .module('platformLayoutModule')
        .factory('Layout', Layout);

    Layout.$inject = ['$resource'];

    function Layout($resource) {

        return $resource('/api/application/layouts/:id', {}, {
            query: {method: 'GET', isArray: true},
            update: {method: 'PUT'}
        });
    }

})();