var platformLoginApp = angular.module('platformLoginApp', ['platformLoginControllers', 'ngRoute', 'ui.router', 'toaster','webClientTemplatecache']);

platformLoginApp.config(
    ['$httpProvider', '$stateProvider', '$urlRouterProvider', function ($httpProvider, $stateProvider, $urlRouterProvider) {
        $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

        $urlRouterProvider.otherwise('/');

        $stateProvider
            .state('application', {
                abstract: true,
                templateUrl: 'login/html/application.html',
                url: '/'
            })
            .state('application.login', {
                url: '?activation-key',
                controller: 'PlatformAuthenticateController',
                templateUrl: 'login/html/login.html'
            });
    }]
);
