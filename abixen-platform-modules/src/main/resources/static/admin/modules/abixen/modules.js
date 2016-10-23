var multiVisualizationControllers = angular.module('multiVisualizationControllers', []);

multiVisualizationControllers.controller('MultiVisualizationController', ['$scope', '$log', '$state', function ($scope, $log, $state) {

    $log.log('$state.$current.name: ' + $state.$current.name);

    if ($state.$current.name === 'application.multiVisualization.modules.databaseDataSource.list' || $state.$current.name === 'application.multiVisualization.modules.databaseDataSource.edit' || $state.$current.name === 'application.multiVisualization.modules.databaseDataSource.add') {
        $scope.selectedModule = 'databaseDataSource';
    } else if ($state.$current.name === 'application.multiVisualization.modules.databaseConnection.list' || $state.$current.name === 'application.multiVisualization.modules.databaseConnection.edit' || $state.$current.name === 'application.multiVisualization.modules.databaseConnection.add') {
        $scope.selectedModule = 'databaseConnection';
    } else if ($state.$current.name === 'application.multiVisualization.modules.fileConnection.list' || $state.$current.name === 'application.multiVisualization.modules.fileConnection.edit' || $state.$current.name === 'application.multiVisualization.modules.fileConnection.add') {
        $scope.selectedModule = 'fileDataSource';
    }
    $scope.selectDatabaseDataSources = function () {
        $scope.selectedModule = 'databaseDataSource'
    };
    $scope.selectDatabaseConnections = function () {
        $scope.selectedModule = 'databaseConnection';
    };
    $scope.selectFileDataSources = function () {
        $scope.selectedModule = 'fileDataSource'
    };

}]);;var multiVisualizationDirectives = angular.module('multiVisualizationDirectives', []);

multiVisualizationDirectives.directive('queryBuilder', ['$compile', function ($compile) {
    return {
        restrict: 'E',
        scope: {
            group: '=',
            fields: '='
        },
        templateUrl: '/admin/modules/abixen/multi-visualization/html/query-builder.html',
        compile: function (element, attrs) {
            var content, directive;
            content = element.contents().remove();
            return function (scope, element, attrs) {
                scope.operators = [
                    { name: 'AND' },
                    { name: 'OR' }
                ];

                /*scope.fields = [
                    { name: 'Firstname' },
                    { name: 'Lastname' },
                    { name: 'Birthdate' },
                    { name: 'City' },
                    { name: 'Country' }
                ];*/

                scope.conditions = [
                    { name: '=' },
                    { name: '<>' },
                    { name: '<' },
                    { name: '<=' },
                    { name: '>' },
                    { name: '>=' }
                ];

                scope.addCondition = function () {
                    scope.group.rules.push({
                        condition: '=',
                        field: 'Firstname',
                        data: ''
                    });
                };

                scope.removeCondition = function (index) {
                    scope.group.rules.splice(index, 1);
                };

                scope.addGroup = function () {
                    scope.group.rules.push({
                        group: {
                            operator: 'AND',
                            rules: []
                        }
                    });
                };

                scope.removeGroup = function () {
                    "group" in scope.$parent && scope.$parent.group.rules.splice(scope.$parent.$index, 1);
                };

                directive || (directive = $compile(content));

                element.append(directive(scope, function ($compile) {
                    return $compile;
                }));
            }
        }
    }
}]);;var multiVisualizationModule = angular.module('multiVisualizationModule', ['multiVisualizationControllers', 'multiVisualizationDirectives', 'databaseDataSourceModule', 'databaseConnectionModule', 'fileDataSourceModule']);

multiVisualizationModule.config(['$stateProvider',
    function ($stateProvider) {
        $stateProvider
            .state('application.multiVisualization', {
                url: '/multi-visualization',
                templateUrl: '/admin/modules/abixen/multi-visualization/html/index.html',
                controller: 'MultiVisualizationController'
            })
            .state('application.multiVisualization.modules', {
                url: '/modules',
                templateUrl: '/admin/modules/abixen/multi-visualization/modules/html/index.html'
            });
    }
]);;var databaseConnectionControllers = angular.module('databaseConnectionControllers', []);

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

    $scope.databaseTypes = ['POSTGRES', 'MYSQL', 'ORACLE'];

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
;var databaseConnectionModule = angular.module('databaseConnectionModule', ['databaseConnectionControllers', 'databaseConnectionServices']);

databaseConnectionModule.config(['$stateProvider',
    function ($stateProvider) {
        $stateProvider
            .state('application.multiVisualization.modules.databaseConnection', {
                url: '/database-connection',
                templateUrl: '/admin/modules/abixen/multi-visualization/modules/database-connection/html/index.html'
            })
            .state('application.multiVisualization.modules.databaseConnection.list', {
                url: '/list',
                templateUrl: '/admin/modules/abixen/multi-visualization/modules/database-connection/html/list.html',
                controller: 'DatabaseConnectionListController'
            })
            .state('application.multiVisualization.modules.databaseConnection.add', {
                url: '/add',
                templateUrl: '/admin/modules/abixen/multi-visualization/modules/database-connection/html/edit.html',
                controller: 'DatabaseConnectionDetailController'
            })
            .state('application.multiVisualization.modules.databaseConnection.edit', {
                url: '/edit/:id',
                templateUrl: '/admin/modules/abixen/multi-visualization/modules/database-connection/html/edit.html',
                controller: 'DatabaseConnectionDetailController'
            });
    }
]);;var databaseConnectionServices = angular.module('databaseConnectionServices', ['ngResource']);

databaseConnectionServices.factory('DatabaseConnection', ['$resource',
    function ($resource) {
        return $resource('/admin/modules/abixen/multi-visualization/database-connections/:id', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'},
            test: {method: 'POST', url: '/admin/modules/abixen/multi-visualization/database-connections/test', isArray: false},
            tables: {method: 'GET', url: '/admin/modules/abixen/multi-visualization/database-connections/:id/tables', isArray: true},
            tableColumns: {method: 'GET', url: '/admin/modules/abixen/multi-visualization/database-connections/:id/tables/:tableName/columns', isArray: true}
        });
    }]);



;var databaseDataSourceControllers = angular.module('databaseDataSourceControllers', []);

databaseDataSourceControllers.controller('DatabaseDataSourceListController', ['$scope', '$http', '$log', 'uiGridConstants', 'DatabaseDataSource', 'gridFilter', 'applicationNavigationItems', '$state', function ($scope, $http, $log, uiGridConstants, DatabaseDataSource, gridFilter, applicationNavigationItems, $state) {
    $log.log('DatabaseDataSourceListController');

    angular.extend(this, new AbstractCrudListController($scope, $http, $log, uiGridConstants, DatabaseDataSource, gridFilter));

    $scope.entityListGrid.columnDefs = [
        {field: 'id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
        {field: 'name', pinnedLeft: true, width: 200},
        {field: 'description', width: 200},
        {field: 'databaseConnection.name', name: 'Connection Name', width: 200},
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
        title: 'New Database Data Source',
        onClick: function () {
            $state.go('application.multiVisualization.modules.databaseDataSource.add');
        },
        visible: true,
        disabled: false
    };

    applicationNavigationItems.setTopbarItem(newDataSourceButton);

    $scope.read();
}]);

databaseDataSourceControllers.controller('DatabaseDataSourceDetailController', ['$scope', '$http', '$state', '$stateParams', '$log', 'DatabaseDataSource', '$parse', 'DatabaseConnection', function ($scope, $http, $state, $stateParams, $log, DatabaseDataSource, $parse, DatabaseConnection) {
    $log.log('DatabaseDataSourceDetailController');

    angular.extend(this, new AbstractCrudDetailController($scope, $http, $state, $stateParams, $log, DatabaseDataSource, $parse, 'application.multiVisualization.modules.databaseDataSource'));

    $scope.databaseConnections = [];

    $scope.databaseTables = [];

    $scope.databaseTableColumns = [];

    $scope.fields = [];

    DatabaseConnection.query({}, function (data) {
        $scope.databaseConnections = data.content;
        $log.log('$scope.databaseConnections: ', $scope.databaseConnections);
    });

    $scope.getDatabaseTables = function (databaseConnection) {
        DatabaseConnection.tables({id: databaseConnection.id}, function (data) {
            $scope.databaseTables = data;
            $log.log('$scope.databaseTables: ', $scope.databaseTables);
        });
    };

    $scope.getDatabaseTableColumns = function (databaseConnection, tableName) {
        DatabaseConnection.tableColumns({id: databaseConnection.id, tableName: tableName}, function (data) {
            $log.log('databaseTableColumns data: ', data);

            var tmpColumns = [];

            for (var i = 0; i < data.length; i++) {
                $log.log('iteracja: ' + $scope.entity.columns);
                var selected = false;

                if ($scope.entity.columns != null) {
                    for (var j = 0; j < $scope.entity.columns.length; j++) {
                        if ($scope.entity.columns[j].name == data[i]) {
                            selected = true;
                        }
                    }
                }

                tmpColumns.push({name: data[i], selected: selected});
            }

            $scope.fields = tmpColumns;
            $scope.databaseTableColumns = tmpColumns;

        });
    }


    var data = '{"group": {"operator": "AND","rules": []}}';
    //var data = '{ "group": { "operator": "AND", "rules": [ { "condition": "=", "field": "Firstname", "data": "c1", "$$hashKey": "object:95" }, { "condition": "=", "field": "Firstname", "data": "c2", "$$hashKey": "object:119" } ] } }';

    function htmlEntities(str) {
        return String(str).replace(/</g, '&lt;').replace(/>/g, '&gt;');
    }

    /*function computed(group) {
     if (!group) return "";
     for (var str = "(", i = 0; i < group.rules.length; i++) {
     i > 0 && (str += " <strong>" + group.operator + "</strong> ");
     str += group.rules[i].group ?
     computed(group.rules[i].group) :
     group.rules[i].field + " " + htmlEntities(group.rules[i].condition) + " " + group.rules[i].data;
     }

     return str + ")";
     }*/

    $scope.json = null;

    $scope.filter = JSON.parse(data);

    $scope.$watch('filter', function (newValue) {
        if ($scope.entity) {
            $scope.json = JSON.stringify(newValue, null, 2);
            $scope.entity.filter = $scope.json;
        }
        //$scope.output = computed(newValue.group);
    }, true);

    var afterGetDatabaseDataSource = function () {
        $log.log('afterGetDatabaseDataSource', $scope.entity);
        $scope.json = JSON.stringify($scope.entity.filter, null, 2);
        $scope.filter = JSON.parse($scope.entity.filter);

        //$scope.output = computed(newValue.group);

        $scope.getDatabaseTables($scope.entity.databaseConnection);
        $scope.getDatabaseTableColumns($scope.entity.databaseConnection, $scope.entity.table);

    }

    $scope.beforeSaveForm = function () {
        $log.log('beforeSaveForm', $scope.entity);

        $scope.entity.columns = [];

        var columnPosition = 1;

        for (var i = 0; i < $scope.fields.length; i++) {
            if ($scope.fields[i].selected) {
                $scope.entity.columns.push({
                    name: $scope.fields[i].name,
                    position: columnPosition++
                });
            }
        }
    }

    $scope.get($stateParams.id, afterGetDatabaseDataSource);

}])
;
;var databaseDataSourceModule = angular.module('databaseDataSourceModule', ['databaseDataSourceControllers', 'databaseDataSourceServices', 'databaseConnectionModule']);

databaseDataSourceModule.config(['$stateProvider',
    function ($stateProvider) {
        $stateProvider
            .state('application.multiVisualization.modules.databaseDataSource', {
                url: '/data-source',
                templateUrl: '/admin/modules/abixen/multi-visualization/modules/database-data-source/html/index.html'
            })
            .state('application.multiVisualization.modules.databaseDataSource.list', {
                url: '/list',
                templateUrl: '/admin/modules/abixen/multi-visualization/modules/database-data-source/html/list.html',
                controller: 'DatabaseDataSourceListController'
            })
            .state('application.multiVisualization.modules.databaseDataSource.add', {
                url: '/add',
                templateUrl: '/admin/modules/abixen/multi-visualization/modules/database-data-source/html/edit.html',
                controller: 'DatabaseDataSourceDetailController'
            })
            .state('application.multiVisualization.modules.databaseDataSource.edit', {
                url: '/edit/:id',
                templateUrl: '/admin/modules/abixen/multi-visualization/modules/database-data-source/html/edit.html',
                controller: 'DatabaseDataSourceDetailController'
            });
    }
]);;var databaseDataSourceServices = angular.module('databaseDataSourceServices', ['ngResource']);

databaseDataSourceServices.factory('DatabaseDataSource', ['$resource',
    function ($resource) {
        return $resource('/admin/modules/abixen/multi-visualization/database-data-sources/:id', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }]);



;var fileDataSourceControllers = angular.module('fileDataSourceControllers', []);

fileDataSourceControllers.controller('FileDataSourceListController', ['$scope', '$http', '$log', 'uiGridConstants', 'FileDataSource', 'gridFilter', 'applicationNavigationItems', '$state', function ($scope, $http, $log, uiGridConstants, FileDataSource, gridFilter, applicationNavigationItems, $state) {
    $log.log('FileDataSourceListController');

    angular.extend(this, new AbstractCrudListController($scope, $http, $log, uiGridConstants, FileDataSource, gridFilter));

    $scope.entityListGrid.columnDefs = [
        {field: 'id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
        {field: 'name', pinnedLeft: true, width: 200},
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
;var fileDataSourceModule = angular.module('fileDataSourceModule', ['fileDataSourceControllers', 'fileDataSourceServices']);

fileDataSourceModule.config(['$stateProvider',
    function ($stateProvider) {
        $stateProvider
            .state('application.multiVisualization.modules.fileDataSource', {
                url: '/data-source',
                templateUrl: '/admin/modules/abixen/multi-visualization/modules/file-data-source/html/index.html'
            })
            .state('application.multiVisualization.modules.fileDataSource.list', {
                url: '/list',
                templateUrl: '/admin/modules/abixen/multi-visualization/modules/file-data-source/html/list.html',
                controller: 'FileDataSourceListController'
            })
            .state('application.multiVisualization.modules.fileDataSource.add', {
                url: '/add',
                templateUrl: '/admin/modules/abixen/multi-visualization/modules/file-data-source/html/edit.html',
                controller: 'FileDataSourceDetailController'
            })
            .state('application.multiVisualization.modules.fileDataSource.edit', {
                url: '/edit/:id',
                templateUrl: '/admin/modules/abixen/multi-visualization/modules/file-data-source/html/edit.html',
                controller: 'FileDataSourceDetailController'
            });
    }
]);;var fileDataSourceServices = angular.module('fileDataSourceServices', ['ngResource']);

fileDataSourceServices.factory('FileDataSource', ['$resource',
    function ($resource) {
        return $resource('/admin/modules/abixen/multi-visualization/file-data-sources/:id', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }]);



