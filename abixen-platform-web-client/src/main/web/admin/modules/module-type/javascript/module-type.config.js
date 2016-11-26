(function () {

    'use strict';

    angular
        .module('platformModuleTypeModule')
        .config(platformModuleTypeModuleConfig);

    platformModuleTypeModuleConfig.$inject = [
        '$stateProvider'
    ];

    function platformModuleTypeModuleConfig($stateProvider) {

        $stateProvider
            .state('application.moduleTypes', {
                url: '/module-types',
                templateUrl: '/admin/modules/module-type/html/index.html'
            })
            .state('application.moduleTypes.list', {
                url: '/list',
                templateUrl: '/admin/modules/module-type/html/list.html',
                controller: 'ModuleTypeListController'
            });
    }
})();