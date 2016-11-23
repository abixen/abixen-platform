(function () {

    'use strict';

    angular
        .module('platformLayoutModule')
        .factory('Layout', Layout);

    Layout.$inject = ['$resource'];

    function Layout($resource) {

        return $resource('/api/dashboard/:id', {}, {
            query: {method: 'GET'/*, isArray: false*/},
            update: {method: 'PUT'}
        });
    }

})();