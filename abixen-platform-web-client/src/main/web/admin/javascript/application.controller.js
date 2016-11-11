(function () {

    'use strict';

    angular
        .module('platformAdminApplication')
        .controller('ApplicationController', ApplicationController);

    ApplicationController.$inject = [
        '$scope',
        '$http',
        '$state',
        '$window',
        '$log',
        'applicationNavigationItems'
    ];

    function ApplicationController($scope, $http, $state, $window, $log, applicationNavigationItems) {

        $log.log('ApplicationController');

        var applicationLoginUrl = 'http://localhost:8080/login';
        var applicationDashboardUrl = 'http://localhost:8080';

        $scope.platformUser = null;

        $scope.logout = function () {
            $http.get('/user', {
                headers: {
                    authorization: 'Basic ' + btoa(':')
                }
            }).success(function () {
                window.location = applicationLoginUrl;
            }).error(function (error) {
                $log.error(error);
                window.location = applicationLoginUrl;
            });
        };

        $http.get('/user', {}).success(function (platformUser) {
            $scope.platformUser = platformUser;
            $log.log('platformUser: ', $scope.platformUser);
        });

        var redirectAction = {
            title: 'Dashboard',
            onClick: function () {
                window.location = applicationDashboardUrl;
            }
        };

        applicationNavigationItems.setRedirectAction(redirectAction);
    }
})();