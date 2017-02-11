(function () {

    'use strict';

    angular
        .module('platformLayoutModule', ['adf.provider'])
        .controller('LayoutController', LayoutController);

    LayoutController.$inject = [
        '$scope',
        'Layout',
        'dashboard'
    ];

    function LayoutController($scope, Layout, dashboard) {

        Layout.query()
            .$promise
            .then(onQueryResult);

        function onQueryResult(layouts) {
            $scope.layouts = layouts;

            angular.forEach(layouts, function (item) {
                dashboard.structure(item.title, item);
                // ADF needs JSON.parse(item.content) (see adf's provider.js structure function)
                // but it needed to be handled on the dashboard side to allow layout update
            })
        }
    }
})();