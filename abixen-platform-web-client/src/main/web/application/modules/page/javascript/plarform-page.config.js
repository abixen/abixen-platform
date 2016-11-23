(function () {

    'use strict';

    angular
        .module('platformPageModule')
        .config(platformPageModuleConfig);

    platformPageModuleConfig.$inject = [
        '$stateProvider'
    ];

    function platformPageModuleConfig($stateProvider) {

        $stateProvider
            .state('application.welcome', {
                url: '/welcome',
                templateUrl: '/application/modules/page/html/index.html',
                controller: 'PageController'
            })
            .state('application.page', {
                url: '/:id',
                templateUrl: '/application/modules/page/html/model.html',
                controller: 'PageController'
            });
    }
})();