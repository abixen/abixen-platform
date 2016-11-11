var fileDataSourceControllers = angular.module('fileDataSourceControllers', []);

fileDataSourceControllers.controller('FileDataSourceListController',
    ['$scope', '$http', '$log', 'uiGridConstants', 'FileDataSource', 'gridFilter', 'applicationNavigationItems', '$state',
        function ($scope, $http, $log, uiGridConstants, FileDataSource, gridFilter, applicationNavigationItems, $state) {
            $log.log('FileDataSourceListController');

            angular.extend(this, new AbstractCrudListController($scope, $http, $log, uiGridConstants, FileDataSource, gridFilter));

            $scope.entityListGrid.columnDefs = [
                {field: 'id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
                {field: 'name', pinnedLeft: true, width: 200},
                {field: 'createdBy.username', name: 'Created By', width: 200},
                {
                    field: 'createdDate',
                    width: 200,
                    cellFilter: 'date:\'' + platformParameters.formats.DATE_TIME_FORMAT + '\''
                },
                {field: 'lastModifiedBy.username', name: 'Last Modified By', width: 200},
                {
                    field: 'lastModifiedDate',
                    width: 200,
                    cellFilter: 'date:\'' + platformParameters.formats.DATE_TIME_FORMAT + '\''
                }
            ];

            $scope.query = {
                and: [
                    {
                        name: 'title',
                        operation: '=',
                        value: 'Page View'
                    }
                ]
            };

            $scope.filterCriteria = {
                page: 0,
                size: 20,
                sort: 'id,asc',
                gridFilterParameters: []
            };

            var newDataSourceButton = {
                id: 1,
                styleClass: 'btn add-new-button',
                faIcon: 'fa fa-plus',
                title: 'New File Data Source',
                onClick: function () {
                    $state.go('application.multiVisualization.modules.fileDataSource.add');
                },
                visible: true,
                disabled: false
            };

            applicationNavigationItems.setTopbarItem(newDataSourceButton);

            $scope.read();
        }]);

fileDataSourceControllers.controller('FileDataSourceDetailController', ['$scope', '$http', '$state', '$stateParams', '$log', 'FileDataSource', '$parse', function ($scope, $http, $state, $stateParams, $log, FileDataSource, $parse) {
    $log.log('FileDataSourceDetailController');

    angular.extend(this, new AbstractCrudDetailController($scope, $http, $state, $stateParams, $log, FileDataSource, $parse, 'application.multiVisualization.modules.fileDataSource'));

    $scope.get($stateParams.id);
}]);