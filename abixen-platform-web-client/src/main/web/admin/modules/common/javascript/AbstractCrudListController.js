function AbstractCrudListController($scope, $http, $log, uiGridConstants, Entity, gridFilter) {
    $log.log('AbstractCrudListController');

    $scope.editButtonEnabled = false;
    $scope.deleteButtonEnabled = false;
    $scope.selectedEntityId = null;

    $scope.entityListGrid = {
        enableSorting: true,
        exporterMenuCsv: true,
        enableGridMenu: true,
        enableFiltering: true,
        //enableRowSelection: true,
        //enableRowHeaderSelection: false,


        onRegisterApi: function (gridApi) {
            $scope.gridApi = gridApi;

            $scope.gridApi.grid.options.multiSelect = false;

            $scope.gridApi.core.on.sortChanged($scope, function (grid, sortColumns) {
                $log.log('sortColumns: ', sortColumns);

            });

            $scope.gridApi.core.on.filterChanged($scope, function () {
                $scope.read();
            });

            $scope.gridApi.selection.on.rowSelectionChanged($scope, function (row) {
                var msg = 'row selected ' + row.isSelected;
                $log.log(msg, row.entity.id);
                if (row.isSelected) {
                    $scope.editButtonEnabled = true;
                    $scope.deleteButtonEnabled = true;
                    $scope.selectedEntityId = row.entity.id;
                } else {
                    $scope.editButtonEnabled = false;
                    $scope.deleteButtonEnabled = false;
                    $scope.selectedEntityId = null;
                }
            });
        }
    };




    $scope.delete = function () {
        $log.log('delete() - selectedEntityId: ', $scope.selectedEntityId);
        if ($scope.selectedEntityId != null) {
            Entity.delete({id: $scope.selectedEntityId}, null, function () {
                $log.log('Entity has been deleted: ', $scope.selectedEntityId);
                $scope.selectedEntityId = null;
                $scope.editButtonEnabled = false;
                $scope.deleteButtonEnabled = false;
                $scope.read();
            });
        }
    };

    $scope.read = function () {

        gridFilter.getJsonCriteria($scope.entityListGrid, [0, 1, 2, 3]);

        $log.log("query: " + JSON.stringify($scope.query));
        var jsonCriteria = JSON.stringify($scope.query);
        var queryParameters = {
            page: 0,
            size: 30,
            sort: 'id,asc',
            jsonCriteria: jsonCriteria
        }

        Entity.query(queryParameters, function (data) {
            $scope.entityListGrid.data = data.content;
            $scope.totalPages = data.totalPages;
            $scope.first = data.first;
            $scope.last = data.last;
            $scope.pageNumber = data.number;
            $scope.entitiesCount = data.totalElements;
        });
    };
}