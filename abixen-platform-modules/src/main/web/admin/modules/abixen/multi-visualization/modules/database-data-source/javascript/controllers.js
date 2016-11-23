var databaseDataSourceControllers = angular.module('databaseDataSourceControllers', []);

databaseDataSourceControllers.controller('DatabaseDataSourceListController',
    ['$scope', '$http', '$log', 'uiGridConstants', 'DatabaseDataSource', 'gridFilter', 'applicationNavigationItems', '$state',
        function ($scope, $http, $log, uiGridConstants, DatabaseDataSource, gridFilter, applicationNavigationItems, $state) {
            $log.log('DatabaseDataSourceListController');

            angular.extend(this, new AbstractCrudListController($scope, $http, $log, uiGridConstants, DatabaseDataSource, gridFilter));

            $scope.entityListGrid.columnDefs = [
                {field: 'id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
                {field: 'name', pinnedLeft: true, width: 200},
                {field: 'description', width: 200},
                {field: 'databaseConnection.name', name: 'Connection Name', width: 200},
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

databaseDataSourceControllers.controller('DatabaseDataSourceDetailController',
    ['$scope', '$http', '$state', '$stateParams', '$log', 'DatabaseDataSource', '$parse', 'DatabaseConnection',
        function ($scope, $http, $state, $stateParams, $log, DatabaseDataSource, $parse, DatabaseConnection) {
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
            };

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

        }]);