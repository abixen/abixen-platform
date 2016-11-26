(function () {

    'use strict';

    angular
        .module('platformModuleTypeModule')
        .factory('ModuleType', ModuleType);

    ModuleType.$inject = ['$resource'];

    function ModuleType($resource) {

        return $resource('/api/admin/module-types/:id', {}, {
            query: {method: 'GET', isArray: false},
            reload: {
                method: 'PUT',
                params: {id: '@id'},
                url: '/api/admin/module-types/:id/reload'
            }
        });
    }

})();