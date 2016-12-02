(function () {

    'use strict';

    angular
        .module('platformUserModule')
        .factory('UserRole', UserRole);

    UserRole.$inject = ['$resource'];

    function UserRole($resource) {

        return $resource('/api/admin/users/:id/roles', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }

})();