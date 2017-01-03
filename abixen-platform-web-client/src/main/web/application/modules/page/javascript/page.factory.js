(function () {

    'use strict';

    angular
        .module('platformPageModule')
        .factory('Page', Page);

    Page.$inject = ['$resource'];

    function Page($resource) {

        return $resource('api/application/pages/:id', {}, {
            query: {method: 'GET', isArray: true},
            delete: {method: 'DELETE'}
        });
    }

})();