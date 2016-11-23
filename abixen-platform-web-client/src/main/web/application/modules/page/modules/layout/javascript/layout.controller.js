(function () {

    'use strict';

    angular
        .module('platformLayoutModule', ['adf.provider'])
        .controller('LayoutController', LayoutController);

    LayoutController.$inject = [
        '$scope',
        '$http',
        '$log',
        'Layout',
        'dashboard'
    ];

    function LayoutController($scope, $http, $log, Layout, dashboard) {

        $scope.query = Layout.query(function () {
        }); //query() returns all layouts

        $scope.read = function () {

            $log.log("query: " + JSON.stringify($scope.query));
            var queryParameters = {
                page: 0,
                size: 20,
                sort: 'id,asc'
            };

            Layout.query(queryParameters, function (data) {
                $scope.layouts = data.content;
                $scope.totalPages = data.totalPages;
                $scope.first = data.first;
                $scope.last = data.last;
                $scope.pageNumber = data.number;

                angular.forEach(data.content, function (item) {
                    dashboard.structure(item.title, item); // ADF needs JSON.parse(item.content) (see adf's provider.js structure function)
                    // but it needed to be handled on the dashboard side to allow layout update
                })
            });
        };

        $scope.read();
    }
})();