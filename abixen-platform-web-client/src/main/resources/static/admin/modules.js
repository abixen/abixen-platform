function AbstractCrudDetailController($scope, $http, $state, $stateParams, $log, Entity, $parse, stateParent) {
    $log.log('AbstractCrudDetailController');

    $scope.entity = null;
    $scope.formErrors = [];

    $scope.get = function (id, callback) {
        $log.log('selected entity id:', id);
        if (id) {
            Entity.get({id: id}, function (data) {
                $scope.entity = data;
                $log.log('Entity has been got: ', $scope.entity);
                if (callback) {
                    callback();
                }
            });
        } else {
            $scope.entity = {};
        }
    };

    $scope.update = function () {
        $log.log('update() - entity: ', $scope.entity);
        Entity.update({id: $scope.entity.id}, $scope.entity, function () {
            $log.log('Entity has been updated: ', $scope.entity);
            $state.go(stateParent + '.list')
        });
    };

    $scope.save = function () {
        $log.log('$scope.entityForm.$invalid: ' + $scope.entityForm.$invalid);
        $log.log('$scope.entityForm.$invalid: ', $scope.entityForm);

        if ($scope.entityForm.$invalid) {
            $log.log('Form is invalid and could not be saved.');
            $scope.$broadcast('show-errors-check-validity');
            return;
        }

        $log.log('save() - entity: ', $scope.entity);

        Entity.save($scope.entity, function (data) {
            $scope.entityForm.$setPristine();
            $log.log('data.form: ', data);
            angular.forEach(data.form, function (rejectedValue, fieldName) {
                $log.log('fiel22dName: ' + fieldName + ', ' + rejectedValue);
                if (fieldName !== 'id') {
                    $scope.entityForm[fieldName].$setValidity('serverMessage', true);
                }
            });

            if (data.formErrors.length == 0) {
                $log.log('Entity has been saved: ', $scope.entity);
                $state.go(stateParent + '.list')
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

    $scope.saveForm = function (beforeSaveForm) {
        $log.log('saveForm()');

        if (beforeSaveForm) {
            beforeSaveForm();
        }

        if ($scope.entity.id == null) {
            $scope.save();
        } else {
            $scope.update();
        }
    };

};function AbstractCrudListController($scope, $http, $log, uiGridConstants, Entity, gridFilter) {
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
};function AbstractPermissionsController($scope, $stateParams, $log, $state, AclRolesPermissions, permissionAclClassCategoryName, objectId, stateParent) {
    $log.log('AbstractPermissionsController');

    $scope.aclRolesPermissionsDto;

    $scope.get = function () {
        $log.log('get() - permissionAclClassCategoryName: ', permissionAclClassCategoryName, ', objectId: ', objectId);
        AclRolesPermissions.get({
            objectId: objectId,
            permissionAclClassCategoryName: permissionAclClassCategoryName
        }, function (data) {
            $scope.aclRolesPermissionsDto = data;
            $log.log('AclRolesPermissions has been got: ', $scope.aclRolesPermissionsDto);
        });
    };

    $scope.saveForm = function () {
        $log.log('saveForm() - permissionAclClassCategoryName: ', permissionAclClassCategoryName, ', objectId: ', objectId);
        AclRolesPermissions.update({
            objectId: objectId,
            permissionAclClassCategoryName: permissionAclClassCategoryName
        }, $scope.aclRolesPermissionsDto, function (data) {
            //$scope.aclRóolesPermissionsDto = data;
            $log.log('AclRolesPermissions has been updated: ', $scope.aclRolesPermissionsDto);
            $scope.goBack();
        });
    };

    $scope.goBack = function(){
        $state.go(stateParent + '.list')
    }

};var GridFilter = function () {

    this.availableOperators = {eq: '=', not: '!', like: '~', and: '&', or: '|'};
    this.globalOperator = this.availableOperators.and;


    this.getJsonCriteria = function (grid) {
        console.log('getJsonCriteria grid: ', grid);

        var stringExpression = '';

        var filterSize = 0;
        for (var i = 0; i < grid.columnDefs.length; i++) {
            if (grid.columnDefs[i].filter) {
                console.log('filter [' + i + ']: ', grid.columnDefs[i].filter);

                console.log(grid.columnDefs[i].field + ', ' + grid.columnDefs[i].filter.term);

                if(filterSize === 0){
                    stringExpression += '(';
                }else{
                    stringExpression += this.globalOperator + '(';
                }
                stringExpression += grid.columnDefs[i].filter.term;
                stringExpression += ')'

                filterSize++;
            }

        }

        console.log('stringExpression:', stringExpression);
    };
};

GridFilter.prototype.$get = function () {
    return this;
};;var platformCommonModule = angular.module('platformCommonModule', []);

platformCommonModule.provider('gridFilter', GridFilter);;var layoutControllers = angular.module('layoutControllers', []);

layoutControllers.controller('LayoutListController', ['$scope', '$http', '$log', 'uiGridConstants', 'Layout', 'gridFilter', 'applicationNavigationItems', '$state', function ($scope, $http, $log, uiGridConstants, Layout, gridFilter, applicationNavigationItems, $state) {
    $log.log('LayoutListController');

    angular.extend(this, new AbstractCrudListController($scope, $http, $log, uiGridConstants, Layout, gridFilter));

    $scope.entityListGrid.columnDefs = [
        {field: 'id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
        {field: 'title', pinnedLeft: true, width: 200},
        {field: 'createdBy.layoutname', name: 'Created By', width: 200},
        {field: 'createdDate', width: 200, cellFilter: "date:'" + platformParameters.formats.DATE_TIME_FORMAT + "'"},
        {field: 'lastModifiedBy.layoutname', name: 'Last Modified By', width: 200},
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
                value: 'Layout View'
            }

        ]
    };


    $scope.filterCriteria = {
        layout: 0,
        size: 20,
        sort: 'id,asc',
        gridFilterParameters: []
    };

    var newLayoutButton = {
        id: 1,
        styleClass: 'btn add-new-object-button',
        title: 'New Layout',
        faIcon: 'fa fa-plus',
        onClick: function () {
            $state.go('application.layouts.add');
        },
        visible: true,
        disabled: false
    };

    applicationNavigationItems.setTopbarItem(newLayoutButton);

    $scope.read();
}]);

layoutControllers.controller('LayoutDetailController', ['$scope', '$http', '$state', '$stateParams', '$log', 'Layout', function ($scope, $http, $state, $stateParams, $log, Layout) {
    $log.log('LayoutDetailController');

    angular.extend(this, new AbstractCrudDetailController($scope, $http, $state, $stateParams, $log, Layout, 'application.layouts'));

    $scope.get($stateParams.id);
}]);

layoutControllers.controller('LayoutPermissionsController', ['$scope', '$stateParams', '$log', '$state', 'AclRolesPermissions', function ($scope, $stateParams, $log, $state, AclRolesPermissions) {
    $log.log('LayoutPermissionsController');

    angular.extend(this, new AbstractPermissionsController($scope, $stateParams, $log, $state, AclRolesPermissions, 'layout', $stateParams.id, 'application.layouts'));

    $scope.get();
}]);
;var platformLayoutModule = angular.module('platformLayoutModule', ['layoutControllers', 'layoutServices', 'ui.router']);

platformLayoutModule.config(['$stateProvider',
    function ($stateProvider) {
        $stateProvider
            .state('application.layouts', {
                url: '/layouts',
                templateUrl: '/admin/modules/layout/html/index.html'
            })
            .state('application.layouts.list', {
                url: '/list',
                templateUrl: '/admin/modules/layout/html/list.html',
                controller: 'LayoutListController'
            })
            .state('application.layouts.add', {
                url: '/add',
                templateUrl: '/admin/modules/layout/html/edit.html',
                controller: 'LayoutDetailController'
            })
            .state('application.layouts.edit', {
                url: '/edit/:id',
                templateUrl: '/admin/modules/layout/html/edit.html',
                controller: 'LayoutDetailController'
            })
            .state('application.layouts.permissions', {
                url: '/permissions/:id',
                templateUrl: '/admin/modules/common/html/permissions.html',
                controller: 'LayoutPermissionsController'
            });
    }
]);;var layoutServices = angular.module('layoutServices', ['ngResource']);

layoutServices.factory('Layout', ['$resource',
    function ($resource) {
        return $resource('/api/admin/layouts/:id', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }]);
;var moduleControllers = angular.module('moduleControllers', []);

moduleControllers.controller('ModuleListController', ['$scope', '$http', '$log', 'uiGridConstants', 'Module', 'gridFilter', 'applicationNavigationItems', '$state', function ($scope, $http, $log, uiGridConstants, Module, gridFilter, applicationNavigationItems, $state) {
    $log.log('ModuleListController');

    angular.extend(this, new AbstractCrudListController($scope, $http, $log, uiGridConstants, Module, gridFilter));

    $scope.entityListGrid.columnDefs = [
        {field: 'id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
        {field: 'title', pinnedLeft: true, width: 200},
        {field: 'description', pinnedLeft: true, width: 200},
        {field: 'moduleType.title', pinnedLeft: true, width: 200},
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
                value: 'Module View'
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

moduleControllers.controller('ModuleDetailController', ['$scope', '$http', '$state', '$parse', '$stateParams', '$log', 'Module', 'Layout', function ($scope, $http, $state, $parse, $stateParams, $log, Module, Layout) {
    $log.log('ModuleDetailController');

    angular.extend(this, new AbstractCrudDetailController($scope, $http, $state, $stateParams, $log, Module, $parse, 'application.modules'));

    $scope.layouts = [];

    var readLayouts = function () {
        var queryParameters = {
            page: 0,
            size: 20,
            sort: 'id,asc'
        };

        Layout.query(queryParameters, function (data) {
            $scope.layouts = data.content;
        });
    };

    readLayouts();

    $scope.get($stateParams.id);
}]);

moduleControllers.controller('ModulePermissionsController', ['$scope', '$stateParams', '$log', '$state', 'AclRolesPermissions', function ($scope, $stateParams, $log, $state, AclRolesPermissions) {
    $log.log('ModulePermissionsController');

    angular.extend(this, new AbstractPermissionsController($scope, $stateParams, $log, $state, AclRolesPermissions, 'module', $stateParams.id, 'application.modules'));

    $scope.get();
}]);
;var platformModuleModule = angular.module('platformModuleModule', ['moduleControllers', 'moduleServices', 'ui.router']);

platformModuleModule.config(['$stateProvider',
    function ($stateProvider) {
        $stateProvider
            .state('application.modules', {
                url: '/modules',
                templateUrl: '/admin/modules/module/html/index.html'
            })
            .state('application.modules.list', {
                url: '/list',
                templateUrl: '/admin/modules/module/html/list.html',
                controller: 'ModuleListController'
            })
            .state('application.modules.add', {
                url: '/add',
                templateUrl: '/admin/modules/module/html/edit.html',
                controller: 'ModuleDetailController'
            })
            .state('application.modules.edit', {
                url: '/edit/:id',
                templateUrl: '/admin/modules/module/html/edit.html',
                controller: 'ModuleDetailController'
            })
            .state('application.modules.permissions', {
                url: '/permissions/:id',
                templateUrl: '/admin/modules/common/html/permissions.html',
                controller: 'ModulePermissionsController'
            });
    }
]);
;var moduleServices = angular.module('moduleServices', ['ngResource']);

moduleServices.factory('Module', ['$resource',
    function ($resource) {
        return $resource('/api/admin/modules/:id', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }]);



;var pageControllers = angular.module('pageControllers', []);

pageControllers.controller('PageListController', ['$scope', '$http', '$log', 'uiGridConstants', 'Page', 'gridFilter', 'applicationNavigationItems', '$state', function ($scope, $http, $log, uiGridConstants, Page, gridFilter, applicationNavigationItems, $state) {
    $log.log('PageListController');

    angular.extend(this, new AbstractCrudListController($scope, $http, $log, uiGridConstants, Page, gridFilter));

    $scope.entityListGrid.columnDefs = [
        {field: 'id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
        {field: 'name', pinnedLeft: true, width: 200},
        {field: 'title', pinnedLeft: true, width: 200},
        {field: 'description', pinnedLeft: true, width: 200},
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

    var addPageButton = {
        id: 1,
        styleClass: 'btn add-new-object-button',
        faIcon: 'fa fa-plus',
        title: 'Add Page',
        onClick: function () {
            $state.go('application.pages.add');
        },
        visible: true,
        disabled: false
    };

    applicationNavigationItems.setTopbarItem(addPageButton);

    $scope.read();
}]);

pageControllers.controller('PageDetailController', ['$scope', '$http', '$state', '$parse', '$stateParams', '$log', 'Page', 'Layout', function ($scope, $http, $state, $parse, $stateParams, $log, Page, Layout) {
    $log.log('PageDetailController');

    angular.extend(this, new AbstractCrudDetailController($scope, $http, $state, $stateParams, $log, Page, $parse, 'application.pages'));

    $scope.layouts = [];

    var readLayouts = function () {
        var queryParameters = {
            page: 0,
            size: 20,
            sort: 'id,asc'
        };

        Layout.query(queryParameters, function (data) {
            $scope.layouts = data.content;
        });
    };

    readLayouts();

    $scope.get($stateParams.id);
}]);

pageControllers.controller('PagePermissionsController', ['$scope', '$stateParams', '$log', '$state', 'AclRolesPermissions', function ($scope, $stateParams, $log, $state, AclRolesPermissions) {
    $log.log('PagePermissionsController');

    angular.extend(this, new AbstractPermissionsController($scope, $stateParams, $log, $state, AclRolesPermissions, 'page', $stateParams.id, 'application.pages'));

    $scope.get();
}]);
;var platformPageModule = angular.module('platformPageModule', ['pageControllers', 'pageServices', 'ui.router']);

platformPageModule.config(['$stateProvider',
    function ($stateProvider) {
        $stateProvider
            .state('application.pages', {
                url: '/pages',
                templateUrl: '/admin/modules/page/html/index.html'
            })
            .state('application.pages.list', {
                url: '/list',
                templateUrl: '/admin/modules/page/html/list.html',
                controller: 'PageListController'
            })
            .state('application.pages.add', {
                url: '/add',
                templateUrl: '/admin/modules/page/html/edit.html',
                controller: 'PageDetailController'
            })
            .state('application.pages.edit', {
                url: '/edit/:id',
                templateUrl: '/admin/modules/page/html/edit.html',
                controller: 'PageDetailController'
            })
            .state('application.pages.permissions', {
                url: '/permissions/:id',
                templateUrl: '/admin/modules/common/html/permissions.html',
                controller: 'PagePermissionsController'
            });
    }
]);
;var pageServices = angular.module('pageServices', ['ngResource']);

pageServices.factory('Page', ['$resource',
    function ($resource) {
        return $resource('/api/admin/pages/:id', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }]);

pageServices.factory('AclRolesPermissions', ['$resource',
    function ($resource) {
        return $resource('api/admin/acls', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }]);



;var permissionControllers = angular.module('permissionControllers', []);

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
;var platformPermissionModule = angular.module('platformPermissionModule', ['permissionControllers', 'permissionServices', 'ui.router']);

platformPermissionModule.config(['$stateProvider',
    function ($stateProvider) {
        $stateProvider
            .state('application.permissions', {
                url: '/permissions',
                templateUrl: '/admin/modules/permission/html/index.html'
            })
            .state('application.permissions.list', {
                url: '/list',
                templateUrl: '/admin/modules/permission/html/list.html',
                controller: 'PermissionListController'
            });
    }
]);;var permissionServices = angular.module('permissionServices', ['ngResource']);

permissionServices.factory('Permission', ['$resource',
    function ($resource) {
        return $resource('/api/admin/permissions/:id', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }]);



;var roleControllers = angular.module('roleControllers', []);

roleControllers.controller('RoleListController', ['$scope', '$http', '$log', 'uiGridConstants', 'Role', 'gridFilter', 'applicationNavigationItems', '$state', function ($scope, $http, $log, uiGridConstants, Role, gridFilter, applicationNavigationItems, $state) {
    $log.log('RoleListController');

    angular.extend(this, new AbstractCrudListController($scope, $http, $log, uiGridConstants, Role, gridFilter));

    $scope.entityListGrid.columnDefs = [
        {field: 'id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
        {field: 'name', pinnedLeft: true, width: 200},
        {field: 'roleType', pinnedLeft: true, width: 200},
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
            },
            {
                name: 'description',
                operation: '=',
                value: 'Allows to view a page.'
            },
            {
                name: 'permissionCategory',
                operation: '=',
                value: 'PAGE'
            }
        ]
    };


    $scope.filterCriteria = {
        page: 0,
        size: 20,
        sort: 'id,asc',
        gridFilterParameters: [
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
    };

    var newRoleButton = {
        id: 1,
        styleClass: 'btn add-new-object-button',
        faIcon: 'fa fa-plus',
        title: 'New Role',
        onClick: function () {
            $state.go('application.roles.add');
        },
        visible: true,
        disabled: false
    };

    applicationNavigationItems.setTopbarItem(newRoleButton);

    $scope.read();
}]);

roleControllers.controller('RoleDetailController', ['$scope', '$http', '$state', '$stateParams', '$log', '$parse', 'Role', function ($scope, $http, $state, $stateParams, $log, $parse, Role) {
    $log.log('RoleDetailController');

    angular.extend(this, new AbstractCrudDetailController($scope, $http, $state, $stateParams, $log, Role, $parse, 'application.roles'));

    $scope.get($stateParams.id);
}]);

roleControllers.controller('RoleDefinePermissionsController', ['$scope', '$http', '$state', '$stateParams', '$log', 'RolePermission', function ($scope, $http, $state, $stateParams, $log, RolePermission) {
    $log.log('RoleDetailController');

    $scope.rolePermission = null;
    $scope.selectedPermissionCategory = null;
    $scope.uniquePermissionCategories = [];

    $scope.get = function (id) {
        $log.log('selected role id:', id);
        if (!id) {
            throw 'Role id is required';
        }
        RolePermission.get({id: id}, function (data) {
            $scope.rolePermission = data;
            $log.log('RolePermission has been got: ', $scope.rolePermission);
            getUniquePermissionCategories($scope.rolePermission.rolePermissions);
        });
    };

    $scope.saveForm = function () {
        $log.log('update() - rolePermission: ', $scope.rolePermission);
        RolePermission.update({id: $stateParams.id}, $scope.rolePermission, function () {
            $log.log('RolePermission has been updated: ', $scope.rolePermission);
            $state.go('application.roles.list')
        });
    };

    $scope.setSelectedPermissionCategory = function (permissionCategory) {
        $scope.selectedPermissionCategory = permissionCategory;
    }

    var getUniquePermissionCategories = function (rolePermissions) {
        var uniquePermissionCategories = ['ALL'];
        for (var i = 0; i < rolePermissions.length; i++) {
            if (uniquePermissionCategories.indexOf(rolePermissions[i].permission.permissionAclClassCategory.title) == -1) {
                uniquePermissionCategories.push(rolePermissions[i].permission.permissionAclClassCategory.title);
            }
        }
        $scope.uniquePermissionCategories = uniquePermissionCategories;
        $log.log('$scope.uniquePermissionCategories: ', $scope.uniquePermissionCategories);

        $scope.selectedPermissionCategory = $scope.uniquePermissionCategories[0];
    }

    $scope.get($stateParams.id);
}]);
;var platformRoleModule = angular.module('platformRoleModule', ['roleControllers', 'roleServices', 'ui.router']);

platformRoleModule.config(['$stateProvider',
    function ($stateProvider) {
        $stateProvider
            .state('application.roles', {
                url: '/roles',
                templateUrl: '/admin/modules/role/html/index.html'
            })
            .state('application.roles.list', {
                url: '/list',
                templateUrl: '/admin/modules/role/html/list.html',
                controller: 'RoleListController'
            })
            .state('application.roles.add', {
                url: '/add',
                templateUrl: '/admin/modules/role/html/edit.html',
                controller: 'RoleDetailController'
            })
            .state('application.roles.edit', {
                url: '/edit/:id',
                templateUrl: '/admin/modules/role/html/edit.html',
                controller: 'RoleDetailController'
            })
            .state('application.roles.permissions', {
                url: '/permissions/:id',
                templateUrl: '/admin/modules/role/html/permissions.html',
                controller: 'RoleDefinePermissionsController'
            });
    }
]);;var roleServices = angular.module('roleServices', ['ngResource']);

roleServices.factory('Role', ['$resource',
    function ($resource) {
        return $resource('/api/admin/roles/:id', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }]);

roleServices.factory('RolePermission', ['$resource',
    function ($resource) {
        return $resource('/api/admin/roles/:id/permissions', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }]);



;var userControllers = angular.module('userControllers', []);

userControllers.controller('UserListController', ['$scope', '$http', '$log', 'uiGridConstants', 'User', 'gridFilter', 'applicationNavigationItems', '$state', function ($scope, $http, $log, uiGridConstants, User, gridFilter, applicationNavigationItems, $state) {
    $log.log('UserListController');

    angular.extend(this, new AbstractCrudListController($scope, $http, $log, uiGridConstants, User, gridFilter));

    $scope.entityListGrid.columnDefs = [
        {field: 'id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
        {field: 'username', pinnedLeft: true, width: 200},
        {field: 'firstName', pinnedLeft: true, width: 200},
        {field: 'lastName', pinnedLeft: true, width: 200},
        {field: 'registrationIp', pinnedLeft: true, width: 200},
        {field: 'state', pinnedLeft: true, width: 200},
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
                value: 'User View'
            }

        ]
    };


    $scope.filterCriteria = {
        user: 0,
        size: 20,
        sort: 'id,asc',
        gridFilterParameters: []
    };

    var newUserButton = {
        id: 1,
        styleClass: 'btn add-new-object-button',
        faIcon: 'fa fa-plus',
        title: 'New User',
        onClick: function () {
            $state.go('application.users.add');
        },
        visible: true,
        disabled: false
    };

    applicationNavigationItems.setTopbarItem(newUserButton);

    $scope.read();
}]);

userControllers.controller('UserDetailController', ['$scope', '$http', '$state', '$stateParams', '$log', 'User', '$parse', function ($scope, $http, $state, $stateParams, $log, User, $parse) {
    $log.log('UserDetailController');

    $scope.userGender = ['MALE', 'FEMALE'];
    angular.extend(this, new AbstractCrudDetailController($scope, $http, $state, $stateParams, $log, User, $parse, 'application.users'));

    $scope.get($stateParams.id);

    $scope.today = function() {
        $scope.entity.birthday = new Date();
    };

    $scope.clear = function () {
        $scope.entity.birthday = null;
    };

    $scope.open = function($event) {
        $scope.status.opened = true;
    };

    $scope.setDate = function(year, month, day) {
        $scope.entity.birthday = new Date(year, month, day);
    };

    $scope.status = {
        opened: false
    };
}]);

userControllers.controller('UserAssignRolesController', ['$scope', '$http', '$state', '$stateParams', '$log', 'UserRole', function ($scope, $http, $state, $stateParams, $log, UserRole) {
    $log.log('UserAssignRolesController');

    $scope.userRole = null;
    $scope.selectedRoleType = null;
    $scope.uniqueRoleTypes = [];

    $scope.get = function (id) {
        $log.log('selected user id:', id);
        if (!id) {
            throw 'User id is required';
        }
        UserRole.get({id: id}, function (data) {
            $scope.userRole = data;
            $log.log('UserRole has been got: ', $scope.userRole);
            getUniqueRoleTypes($scope.userRole.userRoles);
        });
    };

    $scope.saveForm = function () {
        $log.log('update() - userRole: ', $scope.userRole);
        UserRole.update({id: $stateParams.id}, $scope.userRole, function () {
            $log.log('uUserRole has been updated: ', $scope.userRole);
            //$state.go(stateParent + '.list')
        });
    };

    $scope.setSelectedRoleType = function (roleType) {
        $scope.selectedRoleType = roleType;
    };

    var getUniqueRoleTypes = function (userRoles) {
        var uniqueRoleTypes = ['ALL'];
        for (var i = 0; i < userRoles.length; i++) {
            if (uniqueRoleTypes.indexOf(userRoles[i].role.roleType) == -1) {
                uniqueRoleTypes.push(userRoles[i].role.roleType);
            }
        }
        $scope.uniqueRoleTypes = uniqueRoleTypes;
        $log.log('$scope.uniqueRoleTypes: ', $scope.uniqueRoleTypes);

        $scope.selectedRoleType = $scope.uniqueRoleTypes[0];
    };

    $scope.get($stateParams.id);
}]);
;var platformUserModule = angular.module('platformUserModule', ['userControllers', 'userServices', 'ui.router']);

platformUserModule.config(['$stateProvider',
    function ($stateProvider) {
        $stateProvider
            .state('application.users', {
                url: '/users',
                templateUrl: '/admin/modules/user/html/index.html'
            })
            .state('application.users.list', {
                url: '/list',
                templateUrl: '/admin/modules/user/html/list.html',
                controller: 'UserListController'
            })
            .state('application.users.add', {
                url: '/add',
                templateUrl: '/admin/modules/user/html/edit.html',
                controller: 'UserDetailController'
            })
            .state('application.users.edit', {
                url: '/edit/:id',
                templateUrl: '/admin/modules/user/html/edit.html',
                controller: 'UserDetailController'
            })
            .state('application.users.roles', {
                url: '/roles/:id',
                templateUrl: '/admin/modules/user/html/roles.html',
                controller: 'UserAssignRolesController'
            });
    }
]);;var userServices = angular.module('userServices', ['ngResource']);

userServices.factory('User', ['$resource',
    function ($resource) {
        return $resource('/api/admin/users/:id', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }]);

userServices.factory('UserRole', ['$resource',
    function ($resource) {
        return $resource('/api/admin/users/:id/roles', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }]);



