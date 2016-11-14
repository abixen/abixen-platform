var databaseConnectionControllers = angular.module('databaseConnectionControllers', []);

databaseConnectionControllers.controller('DatabaseConnectionListController', ['$scope', '$http', '$log', 'uiGridConstants', 'DatabaseConnection', 'gridFilter', 'applicationNavigationItems', '$state', function ($scope, $http, $log, uiGridConstants, DatabaseConnection, gridFilter, applicationNavigationItems, $state) {
    $log.log('DataSourceListController');

    angular.extend(this, new AbstractCrudListController($scope, $http, $log, uiGridConstants, DatabaseConnection, gridFilter));

    $scope.entityListGrid.columnDefs = [
        {field: 'id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
        {field: 'name', pinnedLeft: true, width: 200},
        {field: 'databaseType', pinnedLeft: true, width: 200},
        {field: 'databaseHost', width: 200},
        {field: 'databasePort', width: 200},
        {field: 'databaseName', width: 200},
        {field: 'createdBy.username', name: 'Created By', width: 200},
        {field: 'createdDate', width: 200, cellFilter: "date:'" + platformParameters.formats.DATE_TIME_FORMAT + "'"},
        {field: 'lastModifiedBy.username', name: 'Last Modified By', width: 200},
        {
            field: 'lastModifiedDate',
            width: 200,
            cellFilter: "date:'" + platformParameters.formats.DATE_TIME_FORMAT + "'"
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
        title: 'New Database Connection',
        onClick: function () {
            $state.go('application.multiVisualization.modules.databaseConnection.add');
        },
        visible: true,
        disabled: false
    };

    applicationNavigationItems.setTopbarItem(newDataSourceButton);

    $scope.read();
}]);

databaseConnectionControllers.controller('DatabaseConnectionDetailController', ['$scope', '$http', '$state', '$stateParams', '$log', 'DatabaseConnection', '$parse', 'toaster', function ($scope, $http, $state, $stateParams, $log, DatabaseConnection, $parse, toaster) {
    $log.log('DataSourceDetailController');

    angular.extend(this, new AbstractCrudDetailController($scope, $http, $state, $stateParams, $log, DatabaseConnection, $parse, 'application.multiVisualization.modules.databaseConnection'));

    $scope.databaseTypes = ['POSTGRES', 'MYSQL', 'ORACLE', 'H2'];

    $scope.testConnection = function () {
        $log.log('testConnection()');
        if($scope.entityForm.$invalid){
            $log.log('Form is invalid and could not be saved.');
            $scope.$broadcast('show-errors-check-validity');
            return;
        }

        DatabaseConnection.test({}, $scope.entity, function (data) {
            $scope.entityForm.$setPristine();
            $log.log('data.form: ' , data);
            angular.forEach(data.form, function (rejectedValue, fieldName) {
                $log.log('fiel22dName: ' + fieldName + ', ' + rejectedValue);
                if(fieldName !== 'id' && fieldName !== 'moduleId'){
                    $scope.entityForm[fieldName].$setValidity('serverMessage', true);
                }
            });

            if (data.formErrors.length == 0) {
                toaster.pop(platformParameters.statusAlertTypes.SUCCESS, 'Connected', 'The application has been connected to the database successfully.');
                return;
            }

            for (var i = 0; i < data.formErrors.length; i++) {
                var fieldName = data.formErrors[i].field;
                $log.log('fieldName: ' + fieldName);
                var message = data.formErrors[i].message;
                var serverMessage = $parse('entityForm.' + fieldName + '.$error.serverMessage');
                $scope.entityForm[fieldName].$setValidity('serverMessage', false);
                serverMessage.assign($scope, message);
            }

            $scope.$broadcast('show-errors-check-validity');
        });
    };

    $scope.get($stateParams.id);
}]);
