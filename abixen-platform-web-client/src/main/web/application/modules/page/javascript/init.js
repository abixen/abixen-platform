var platformPageModule = angular.module('platformPageModule', ['ui.router', 'pageControllers', 'pageServices', 'adf',
    'platformLayoutModule']);

platformPageModule.config(['$stateProvider',
    function ($stateProvider) {
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
]);
