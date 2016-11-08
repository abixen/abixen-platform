var platformApplication = angular.module('platformApplication', [
    'platformNavigationModule',
    'platformApplicationControllers',
    'platformApplicationServices',
    'platformApplicationDirectives',
    'platformPageModule',
    'ngAnimate',
    'ui.router',
    'ui.bootstrap',
    'ngAside',
    'adf.provider',
    'ui.bootstrap.showErrors',
    'toaster',
    'xeditable',
    'angularFileUpload',
    'ngCookies',
    'platformChartModule',
    'platformMagicNumberModule',
    'platformKpiChartModule',
    'platformUserModule',
    'platformModalModule'
]);

platformApplication.factory('platformHttpInterceptor', ['$q', '$injector', function ($q, $injector) {
    var applicationLoginUrl = 'http://localhost:8080/login';
    var applicationModulesUrlPrefix = '/application/modules';

    return {
        responseError: function (rejection) {
            console.log('rejection: ', rejection);

            if (rejection.data && rejection.data.path && rejection.data.path.indexOf(applicationModulesUrlPrefix) == 0 && rejection.status != 401 && rejection.status != 403 && rejection.status != 500) {
                return $q.reject(rejection);
            }

            if (rejection.status == 401) {
                window.location = applicationLoginUrl;
            } else if (rejection.status == 403) {
                var toaster = $injector.get('toaster');
                toaster.pop(platformParameters.statusAlertTypes.ERROR, rejection.data.error, rejection.data.message);
            } else if (rejection.status == 500) {
                var toaster = $injector.get('toaster');
                toaster.pop(platformParameters.statusAlertTypes.ERROR, rejection.data.error, rejection.data.message);
            }
            return $q.reject(rejection);
        }
    };
}]);

platformApplication.config(
    ['$httpProvider', '$stateProvider', '$urlRouterProvider', 'modalWindowProvider', function ($httpProvider, $stateProvider, $urlRouterProvider, modalWindowProvider) {

        $httpProvider.interceptors.push('platformHttpInterceptor');

        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

        $urlRouterProvider.otherwise('/');
        $stateProvider
            .state('application', {
                abstract: true,
                controller: 'PlatformInitController',
                templateUrl: '/application/html/index.html'
            });

        modalWindowProvider.setOkButtonClass('btn save-button add-module-btton');
        modalWindowProvider.setCancelButtonClass('btn cancel-button');
        modalWindowProvider.setWarningWindowClass('warning-modal');
    }]
);

platformApplication.directive('dynamicName', function ($compile, $parse) {
    return {
        restrict: 'A',
        terminal: true,
        priority: 100000,
        link: function (scope, elem) {
            var name = elem.attr('dynamic-name');
            elem.removeAttr('dynamic-name');
            elem.attr('name', name);
            $compile(elem)(scope);
        }
    };
});

platformApplication.run(function(editableOptions, editableThemes) {
    // set `default` theme
    editableOptions.theme = 'bs3';

    // overwrite submit button template
    editableThemes['bs3'].submitTpl = '<button type="submit" class="btn save-button"><i class="fa fa-floppy-o"></i></button>';
    editableThemes['bs3'].cancelTpl = '<button type="button" class="btn cancel-button" ng-click="$form.$cancel()"><i class="fa fa-times"></i></button>';
});
