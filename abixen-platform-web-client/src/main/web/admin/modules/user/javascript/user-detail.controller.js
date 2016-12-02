(function () {

    'use strict';

    angular
        .module('platformUserModule')
        .controller('UserDetailController', UserDetailController);

    UserDetailController.$inject = [
        '$scope',
        '$http',
        '$state',
        '$stateParams',
        '$log',
        'User',
        '$parse'
    ];

    function UserDetailController($scope, $http, $state, $stateParams, $log, User, $parse) {
        $log.log('UserDetailController');

        $scope.userGender = ['MALE', 'FEMALE'];
        angular.extend(this, new AbstractCrudDetailController($scope, $http, $state, $stateParams, $log, User, $parse, 'application.users'));

        $scope.get($stateParams.id);

        $scope.today = function () {
            $scope.entity.birthday = new Date();
        };

        $scope.clear = function () {
            $scope.entity.birthday = null;
        };

        $scope.open = function ($event) {
            $scope.status.opened = true;
        };

        $scope.setDate = function (year, month, day) {
            $scope.entity.birthday = new Date(year, month, day);
        };

        $scope.status = {
            opened: false
        };
    }
})();