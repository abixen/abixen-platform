var permissionControllers = angular.module('permissionControllers', []);

permissionControllers.controller('PermissionListController', ['$scope', '$http', '$log', 'uiGridConstants', 'Permission', 'gridFilter', 'applicationNavigationItems', '$state', function ($scope, $http, $log, uiGridConstants, Permission, gridFilter, applicationNavigationItems, $state) {
    $log.log('PermissionListController');

    angular.extend(this, new AbstractCrudListController($scope, $http, $log, uiGridConstants, Permission, gridFilter));

    $scope.entityListGrid.columnDefs = [
        {field: 'id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
        {field: 'title', pinnedLeft: true, width: 200 /*filter: {term: 'cat|dog&!pink'}*/},
        {field: 'description', enableSorting: false, width: 200/*, filter: {term: '(x1|x2)&(z1|z2)'}*/},
        {field: 'permissionName', width: 200},
        {field: 'permissionCategory', width: 200},
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

    applicationNavigationItems.clearTopbarItems();

    $scope.read();
}]);

/*permissionControllers.controller('PermissionListController', ['$scope', '$http', 'uiGridConstants', 'Permission', 'gridFilter', function ($scope, $http, uiGridConstants, Permission, gridFilter) {
 $scope.permissionListGrid = {
 enableSorting: true,
 exporterMenuCsv: true,
 enableGridMenu: true,
 enableFiltering: true,

 columnDefs: [
 {field: 'id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
 {field: 'title', pinnedLeft: true, width: 200, filter: {term: 'cat|dog&!pink'}},
 {field: 'description', enableSorting: false, width: 200, filter: {term: '(x1|x2)&(z1|z2)'}},
 {field: 'permissionName', width: 200},
 {field: 'permissionCategory', width: 200},
 {field: 'createdBy', width: 200},
 //{field: 'createdDate', cellFilter: 'date',headerCellTemplate: '<input type="text" class="form-control" datepicker-popup="{{format}}" ng-model="dt" is-open="opened" min-date="minDate" max-date="\'2015-06-22\'" datepicker-options="dateOptions" date-disabled="disabled(date, mode)" ng-required="true" close-text="Close" />', width:200},
 {field: 'createdDate', width: 200},
 {field: 'lastModifiedBy', width: 200},
 {field: 'lastModifiedDate', width: 200}
 ],
 onRegisterApi: function (gridApi) {
 $scope.gridApi = gridApi;*/

/**
 $scope.gridOptions.columnDefs = [
 {
   field: 'field1', filters: [
     {
       term: 'aa',
       condition: uiGridConstants.filter.STARTS_WITH,
       placeholder: 'starts with...',
       flags: { caseSensitive: false },
       type: uiGridConstants.filter.SELECT,
       selectOptions: [ { value: 1, label: 'male' }, { value: 2, label: 'female' } ]
     },
     {
       condition: uiGridConstants.filter.ENDS_WITH,
       placeholder: 'ends with...'
     }
   ]
 }
 */

// $scope.gridApi.core.on.sortChanged($scope, function (grid, sortColumns) {
//   console.log('sortColumns: ', sortColumns);
/*if (sortColumns.length === 0 || sortColumns[0].name !== $scope.gridOptions.columnDefs[0].name) {
 $http.get('/data/100.json')
 .success(function (data) {
 $scope.gridOptions.data = data;
 });
 } else {
 switch (sortColumns[0].sort.direction) {
 case uiGridConstants.ASC:
 $http.get('/data/100_ASC.json')
 .success(function (data) {
 $scope.gridOptions.data = data;
 });
 break;
 case uiGridConstants.DESC:
 $http.get('/data/100_DESC.json')
 .success(function (data) {
 $scope.gridOptions.data = data;
 });
 break;
 case undefined:
 $http.get('/data/100.json')
 .success(function (data) {
 $scope.gridOptions.data = data;
 });
 break;
 }
 }*/
/* });

 $scope.gridApi.core.on.filterChanged( $scope, function() {
 $scope.read();
 });
 }
 };*/

/*$scope.query=
 {
 and: [
 {
 name: 'permissionName',
 operation: '=',
 value: 'PAGE_VIEW'
 },
 {
 name: 'permissionCategory',
 operation: '=',
 value: null
 },
 {
 or: [
 {
 name: 'title',
 operation: '=',
 value: 'aaa'
 },
 {
 name: 'description',
 operation: '=',
 value: null
 }
 ]
 },
 ]
 }*/



/*$scope.read = function () {

 gridFilter.getJsonCriteria($scope.permissionListGrid, [0,1,2,3]);

 //var gridFilterParameters = JSON.stringify($scope.filterCriteria.gridFilterParameters);
 // console.log("gridFilterParameters: " + gridFilterParameters);

 console.log("query: " + JSON.stringify($scope.query));
 var jsonCriteria = JSON.stringify($scope.query);
 var queryParameters = {
 page: 0,
 size: 20,
 sort: 'id,asc',
 jsonCriteria: jsonCriteria
 }

 Permission.query(queryParameters, function (data) {
 $scope.permissionListGrid.data = data.content;
 $scope.totalPages = data.totalPages;
 $scope.first = data.first;
 $scope.last = data.last;
 $scope.pageNumber = data.number;
 $scope.permissionsCount = data.totalElements;
 });
 };

 $scope.read();*/

/*$http.get('/abixen-platform/api/admin/permissions?size=100')
 .success(function (data) {
 console.log('data.content', data.content);
 $scope.permissionListGrid.data = data.content;
 });*/
//}]);
