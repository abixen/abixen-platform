(function () {

    'use strict';

    angular
        .module('platformAdminApplication')
        .controller('SearchController', SearchController);

    SearchController.$inject = [
        '$scope', '$stateParams', '$log'
    ];

    function SearchController($scope, $stateParams, $log) {

        $log.log('SearchController');

        $scope.searchTerm = $stateParams.query;
    }
})();