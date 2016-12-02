(function () {

    'use strict';

    angular
        .module('platformUserModule')
        .factory('User', User);

    User.$inject = ['$resource'];

    function User($resource) {

        return $resource('/api/admin/users/:id', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }

})();