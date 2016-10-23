var platformParameters = {
    events: {
        ADF_TOGGLE_EDIT_MODE_EVENT: 'ADF_TOGGLE_EDIT_MODE',
        ADF_TOGGLE_EDIT_MODE_RESPONSE_EVENT: 'ADF_TOGGLE_EDIT_MODE_INVOKED',
        ADF_ADD_WIDGET_EVENT: 'ADF_ADD_WIDGET',
        ADF_EDIT_DASHBOARD_EVENT: 'ADF_EDIT_DASHBOARD',
        ADF_WIDGET_DELETED_EVENT: 'ADF_WIDGET_DELETED',
        ADF_STRUCTURE_CHANGED_EVENT: 'ADF_STRUCTURE_CHANGED',
        ADF_DASHBOARD_CHANGED_EVENT: 'ADF_DASHBOARD_CHANGED',
        MODULE_READY: 'MODULE_READY',
        RELOAD_MODULE: 'RELOAD_MODULE',
        MODULE_FORBIDDEN: 'MODULE_FORBIDDEN',
        START_REQUEST: 'START_REQUEST',
        STOP_REQUEST: 'STOP_REQUEST',
        SHOW_LOADER: 'SHOW_LOADER',
        HIDE_LOADER: 'HIDE_LOADER',
        SHOW_PERMISSION_DENIED_TO_MODULE: 'SHOW_PERMISSION_DENIED_TO_MODULE',
        SIDEBAR_ELEMENT_SELECTED: 'SIDEBAR_ELEMENT_SELECTED'
    },
    statusAlertTypes: {
        SUCCESS: 'success',
        ERROR: 'error',
        WARNING: 'warning',
        NOTE: 'note',
        WAIT: 'wait'
    }
};;var platformApplication = angular.module('platformApplication', [
    'platformNavigationModule',
    'platformApplicationControllers',
    'platformApplicationServices',
    'platformApplicationDirectives',
    'platformPageModule',
    'ngAnimate',
    'ui.router',
    'ui.bootstrap',
    'ngAside',
    'adf.provider',
    'ui.bootstrap.showErrors',
    'toaster',
    'xeditable',
    'platformChartModule',
    'platformMagicNumberModule',
    'platformKpiChartModule',
    'platformUserModule',
    'platformModalModule'
]);

platformApplication.factory('platformHttpInterceptor', ['$q', '$injector', function ($q, $injector) {
    var applicationLoginUrl = 'http://localhost:8080/login';
    var applicationModulesUrlPrefix = '/application/modules';

    return {
        responseError: function (rejection) {
            console.log('rejection: ', rejection);

            if (rejection.data && rejection.data.path && rejection.data.path.indexOf(applicationModulesUrlPrefix) == 0 && rejection.status != 401 && rejection.status != 403 && rejection.status != 500) {
                return $q.reject(rejection);
            }

            if (rejection.status == 401) {
                window.location = applicationLoginUrl;
            } else if (rejection.status == 403) {
                var toaster = $injector.get('toaster');
                toaster.pop(platformParameters.statusAlertTypes.ERROR, rejection.data.error, rejection.data.message);
            } else if (rejection.status == 500) {
                var toaster = $injector.get('toaster');
                toaster.pop(platformParameters.statusAlertTypes.ERROR, rejection.data.error, rejection.data.message);
            }
            return $q.reject(rejection);
        }
    };
}]);

platformApplication.config(
    ['$httpProvider', '$stateProvider', '$urlRouterProvider', 'modalWindowProvider', function ($httpProvider, $stateProvider, $urlRouterProvider, modalWindowProvider) {

        $httpProvider.interceptors.push('platformHttpInterceptor');

        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

        $urlRouterProvider.otherwise('/');
        $stateProvider
            .state('application', {
                abstract: true,
                controller: 'PlatformInitController',
                templateUrl: '/application/html/index.html'
            });

        modalWindowProvider.setOkButtonClass('btn save-button add-module-btton');
        modalWindowProvider.setCancelButtonClass('btn cancel-button');
        modalWindowProvider.setWarningWindowClass('warning-modal');
    }]
);

platformApplication.directive('dynamicName', function ($compile, $parse) {
    return {
        restrict: 'A',
        terminal: true,
        priority: 100000,
        link: function (scope, elem) {
            var name = elem.attr('dynamic-name');
            elem.removeAttr('dynamic-name');
            elem.attr('name', name);
            $compile(elem)(scope);
        }
    };
});

platformApplication.run(function(editableOptions, editableThemes) {
    // set `default` theme
    editableOptions.theme = 'bs3';

    // overwrite submit button template
    editableThemes['bs3'].submitTpl = '<button type="submit" class="btn save-button"><i class="fa fa-floppy-o"></i></button>';
    editableThemes['bs3'].cancelTpl = '<button type="button" class="btn cancel-button" ng-click="$form.$cancel()"><i class="fa fa-times"></i></button>';
});
;var platformControllers = angular.module('platformApplicationControllers', []);

platformControllers.controller('PlatformInitController', ['$rootScope', '$scope', '$http', '$location', '$log', '$state', 'applicationNavigationItems', '$aside', 'Page', 'ModuleType', 'dashboard', 'toaster', 'modalWindow',
    function ($rootScope, $scope, $http, $location, $log, $state, applicationNavigationItems, $aside, Page, ModuleType, dashboard, toaster, modalWindow) {
        $log.log('PlatformInitController');

        var applicationLoginUrl = 'http://localhost:8080/login';
        var applicationAdminUrl = 'http://localhost:8080/admin';

        $scope.platformUser = null;

        $scope.logout = function () {
            $http.get('/user', {
                headers: {
                    authorization: 'Basic ' + btoa(':')
                }
            }).success(function () {
                window.location = applicationLoginUrl;
            }).error(function (error) {
                $log.error(error);
                window.location = applicationLoginUrl;
            });
        };

        var getPlatformUser = function () {
            $http.get('/user', {}).success(function (platformUser) {
                $scope.platformUser = platformUser;
                $log.log('platformUser: ', $scope.platformUser);
            });
        };

        getPlatformUser();

        $scope.showDropdown = false;

        var newPageButton = {
            id: 1,
            styleClass: 'btn add-new-page-button',
            faIcon: 'fa fa-plus',
            title: 'Add Page',
            onClick: function () {
                $aside.open({
                    placement: 'left',
                    templateUrl: '/application/modules/page/html/add.html',
                    size: 'md',
                    backdrop: false,
                    controller: 'PageDetailsController'
                });
            },
            visible: true,
            disabled: false
        };

        var editModeButton = {
            id: 2,
            styleClass: 'btn edit-mode-button',
            faIcon: 'fa fa-pencil-square-o',
            title: 'Edit Mode',
            onClick: function () {
                $scope.$broadcast(platformParameters.events.ADF_TOGGLE_EDIT_MODE_EVENT);
                editPageButton.visible = !editPageButton.visible;
                $scope.showDropdown = !$scope.showDropdown;
            },
            visible: true,
            disabled: false
        };

        applicationNavigationItems
            .addTopbarItem(newPageButton)
            .addTopbarItem(editModeButton);

        var editPageButton = {
            id: 1,
            title: 'Edit page',
            onClick: function () {
                $scope.$broadcast(platformParameters.events.ADF_EDIT_DASHBOARD_EVENT);
            },
            isSeparator: false
        };

        var deletePageButton = {
            id: 2,
            title: 'Delete Page',
            onClick: function () {
                var deletePage = function () {
                    Page.delete({id: $state.params.id}, function (data) {
                        toaster.pop(platformParameters.statusAlertTypes.SUCCESS, 'Deleted', 'A page has been deleted successfully.');
                        var deletedPageNavigationItem = platformApplicationUtils.findObjectInArray('id', $state.params.id, applicationNavigationItems.sidebarItems);

                        if (deletedPageNavigationItem) {
                            applicationNavigationItems.sidebarItems.splice(applicationNavigationItems.sidebarItems.indexOf(deletedPageNavigationItem), 1);
                        }
                        if (applicationNavigationItems.sidebarItems.length > 0) {
                            $state.go(applicationNavigationItems.sidebarItems[0].state, {id: applicationNavigationItems.sidebarItems[0].id});
                            $scope.$broadcast(platformParameters.events.SIDEBAR_ELEMENT_SELECTED, applicationNavigationItems.sidebarItems[0].id);
                        } else {
                            $state.go('application.welcome');
                        }
                    });
                };

                modalWindow.openConfirmWindow('Delete page?', 'The page will be deleted permanently. Are you sure you want to perform this operation?', 'warning', deletePage);
            },
            isSeparator: false
        };

        var topbarDropdownSeparator = {
            id: 3,
            title: '',
            styleClass: 'divider',
            isSeparator: true

        };

        var newModuleButton = {
            id: 4,
            title: 'Add Module',
            onClick: function () {
                $scope.$broadcast(platformParameters.events.ADF_ADD_WIDGET_EVENT);
            },
            isSeparator: false
        };

        applicationNavigationItems
            .addTopbarDropdownItem(editPageButton)
            .addTopbarDropdownItem(deletePageButton)
            .addTopbarDropdownItem(topbarDropdownSeparator)
            .addTopbarDropdownItem(newModuleButton);

        applicationNavigationItems.setDropdownStyleClass('btn-group action-dropdown');

        var getPages = function () {
            Page.query(function (data) {
                var pageList = data.content;
                for (var i = 0; i < pageList.length; i++) {
                    applicationNavigationItems.addSidebarItem({
                        id: pageList[i].id,
                        title: pageList[i].title,
                        state: 'application.page',
                        orderIndex: i,
                        isPage: true,
                        iconClass: 'fa fa-file-text-o'
                    });
                }

                if (pageList.length > 0) {
                    var idToChoose;
                    if ($state.params.id) {
                        idToChoose = $state.params.id;
                    } else {
                        idToChoose = pageList[0].id;
                    }

                    if ($state.current.name == 'application.page') {
                        $state.go('application.page', {id: idToChoose});
                        $scope.$broadcast(platformParameters.events.SIDEBAR_ELEMENT_SELECTED, idToChoose);
                    }
                } else {
                    editModeButton.visible = false;
                    $state.go('application.welcome');
                }
            })
        };

        var getModuleTypes = function () {
            ModuleType.query(function (data) {

                for (var i = 0; i < data.length; i++) {
                    var moduleType = data[i];

                    dashboard.widget(moduleType.name, {
                        id: null,
                        title: moduleType.title, //'Chart Module',
                        description: moduleType.description,//'Displays a list of links',
                        templateUrl: moduleType.initUrl, //'/application/modules/abixen/chart/html/index.html',
                        //controller: 'ChartModuleController',
                        //controllerAs: 'ChartModuleController',
                        edit: {
                            //templateUrl: '/application/modules/abixen/chart/html/configurationView.html',
                            reload: false
                            //controller: 'linklistEditCtrl'
                        },
                        moduleType: moduleType/*{
                         id: 1,
                         name: 'chart',
                         title: 'Chart visualization',
                         description: 'This is chart visualization module'
                         }*/
                    })
                }

                getPages();
            })
        };

        getModuleTypes();

        $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
            $scope.editMode = false;
            editModeButton.title = 'Edit Mode';
            if (toState.name == 'application.welcome') {
                editModeButton.visible = false;
            } else if (toState.name == 'application.page') {
                editModeButton.visible = applicationNavigationItems.getSidebarItems().length > 0;
            }
            $scope.showDropdown = false;
        });

        var redirectAction = {
            title: 'Admin Page',
            onClick: function () {
                $scope.showDropdown = false;
                window.location = applicationAdminUrl;
            }
        };

        applicationNavigationItems.setRedirectAction(redirectAction);

        $scope.editUser = function () {
            $aside.open({
                placement: 'left',
                templateUrl: '/application/modules/user/html/edit.html',
                size: 'md',
                backdrop: false,
                controller: 'UserDetailsController',
                resolve: {
                    userId: function () {
                        return $scope.platformUser.id;
                    }
                }
            });
        };

        $scope.$on(platformParameters.events.ADF_TOGGLE_EDIT_MODE_RESPONSE_EVENT, function (event, editMode) {
            $log.log('toggle edit mode invoked, editMode: ', editMode);
            $scope.editMode = editMode;
            editModeButton.title = $scope.editMode ? 'View Mode' : 'Edit Mode';
        });


    }]);


;var platformApplicationDirectives = angular.module('platformApplicationDirectives', []);

var CONTENT_HEIGHT_OFFSET = 175; // modal-header + modal-footer + a little of spare whitespace extra

platformApplicationDirectives.directive('windowResize', ['$window', '$timeout', function ($window, $timeout) {

    return {
        link: link,
        scope: {
            header: '@header',
            body: '@body',
            footer: '@footer'
        }
    };

    function link(scope, element, attrs){

        scope.height = $window.innerHeight;

        scope.redraw = function ($event) {
            var size = {
                width: window.innerWidth || document.body.clientWidth,
                height: window.innerHeight || document.body.clientHeight
            };
            var documentWrapper = angular.element(document);
            var wrapperHeight = angular.element(documentWrapper.find('form-container'));

            var offsetHeight = 0;
            for (var i = 0; i < wrapperHeight.length; i++) {
                offsetHeight += wrapperHeight[i].offsetHeight;
            }
            var contentHeightComputed = (size.height - offsetHeight - CONTENT_HEIGHT_OFFSET);
            angular.element(element[0]).css('height', contentHeightComputed + 'px');
        };
        scope.redraw();

        $timeout(function () {
            scope.redraw();
        }, 500);

        angular.element($window).bind('resize', function () {
            scope.redraw();
        });
    }
}]);;var platformServices = angular.module('platformApplicationServices', ['ngResource', 'adf.provider']);

platformServices.factory('ModuleType', ['$resource',
    function ($resource) {
        return $resource('/api/user/pages/module-types', {}, {
            query: {method: 'GET', isArray: true}
        });
    }]);


platformServices.factory('User', ['$resource',
    function ($resource) {
        return $resource('/api/application/users/:id', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }]);

platformServices.factory('UserRole', ['$resource',
    function ($resource) {
        return $resource('/api/application/users/:id/roles', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }]);

platformServices.factory('UserPassword', ['$resource',
    function ($resource) {
        return $resource('/api/application/users/:id/password', {}, {
            update: {method: 'POST'}
        });
    }]);;var platformApplicationUtils = {

    findObjectInArray: function (property, value, array) {
        for (var i = 0; i < array.length; i++) {
            if (array[i][property] == value) {
                return array[i];
            }
        }
    }

};;/*
 * The MIT License
 *
 * Copyright (c) 2015, Sebastian Sdorra
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

'use strict';

angular.module('adf', ['adf.provider', 'ui.bootstrap'])
    .value('adfTemplatePath', '/application/modules/dashboard/html/')
    .value('rowTemplate', '<adf-dashboard-row row="row" adf-model="adfModel" options="options" edit-mode="editMode" ng-repeat="row in column.rows" />')
    .value('columnTemplate', '<adf-dashboard-column column="column" adf-model="adfModel" options="options" edit-mode="editMode" ng-repeat="column in row.columns" />')
    .value('adfVersion', '<<adfVersion>>');
;/*
 * The MIT License
 *
 * Copyright (c) 2015, Sebastian Sdorra
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/* global angular */
angular.module('adf')
    .directive('adfDashboardColumn', ['$log', '$compile', 'adfTemplatePath', 'rowTemplate', 'dashboard', function ($log, $compile, adfTemplatePath, rowTemplate, dashboard) {
        'use strict';

        /**
         * moves a widget in between a column
         */
        function moveWidgetInColumn($scope, column, evt){
            var widgets = column.widgets;
            // move widget and apply to scope
            $scope.$apply(function(){
                widgets.splice(evt.newIndex, 0, widgets.splice(evt.oldIndex, 1)[0]);
            });
        }

        /**
         * finds a widget by its id in the column
         */
        function findWidget(column, index){
            var widget = null;
            for (var i=0; i<column.widgets.length; i++){
                var w = column.widgets[i];
                if (w.wid === index){
                    widget = w;
                    break;
                }
            }
            return widget;
        }

        /**
         * finds a column by its id in the model
         */
        function findColumn(model, index){
            var column = null;

            for (var i=0; i<model.rows.length; i++){
                var r = model.rows[i];
                for (var j=0; j<r.columns.length; j++){
                    var c = r.columns[j];
                    if ( c.cid === index ){
                        column = c;
                        break;
                    } else if (c.rows){
                        column = findColumn(c, index);
                    }
                }
                if (column){
                    break;
                }
            }
            return column;
        }

        /**
         * get the adf id from an html element
         */
        function getId(el){
            var id = el.getAttribute('adf-id');
            return id ? parseInt(id) : -1;
        }

        /**
         * adds a widget to a column
         */
        function addWidgetToColumn($scope, model, targetColumn, evt){
            // find source column
            var cid = getId(evt.from);
            var sourceColumn = findColumn(model, cid);

            if (sourceColumn){
                // find moved widget
                var wid = getId(evt.item);
                var widget = findWidget(sourceColumn, wid);

                if (widget){
                    // add new item and apply to scope
                    $scope.$apply(function(){
                        if (!targetColumn.widgets) {
                            targetColumn.widgets = [];
                        }

                        targetColumn.widgets.splice(evt.newIndex, 0, widget);
                    });
                } else {
                    $log.warn('could not find widget with id ' + wid);
                }
            } else {
                $log.warn('could not find column with id ' + cid);
            }
        }

        /**
         * removes a widget from a column
         */
        function removeWidgetFromColumn($scope, column, evt){
            // remove old item and apply to scope
            $scope.$apply(function(){
                column.widgets.splice(evt.oldIndex, 1);
            });
        }

        /**
         * enable sortable
         */
        function applySortable($scope, $element, model, column){
            // enable drag and drop
            var el = $element[0].childNodes[0];
            var sortable = Sortable.create(el, {
                group: 'widgets',
                handle: '.adf-move',
                ghostClass: 'placeholder',
                animation: 150,
                onAdd: function(evt){
                    addWidgetToColumn($scope, model, column, evt);
                },
                onRemove: function(evt){
                    removeWidgetFromColumn($scope, column, evt);
                },
                onUpdate: function(evt){
                    moveWidgetInColumn($scope, column, evt);
                }
            });

            // destroy sortable on column destroy event
            $element.on('$destroy', function () {
                sortable.destroy();
            });
        }

        return {
            restrict: 'E',
            replace: true,
            scope: {
                column: '=',
                editMode: '=',
                adfModel: '=',
                options: '='
            },
            templateUrl: adfTemplatePath + 'dashboard-column.html',
            link: function ($scope, $element) {
                // set id
                var col = $scope.column;
                if (!col.cid){
                    col.cid = dashboard.id();
                }

                if (angular.isDefined(col.rows) && angular.isArray(col.rows)) {
                    // be sure to tell Angular about the injected directive and push the new row directive to the column
                    $compile(rowTemplate)($scope, function(cloned) {
                        $element.append(cloned);
                    });
                } else {
                    // enable drag and drop for widget only columns
                    applySortable($scope, $element, $scope.adfModel, col);
                }
            }
        };
    }]);
;/*
 * The MIT License
 *
 * Copyright (c) 2015, Sebastian Sdorra
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/**
 * @ngdoc directive
 * @name adf.directive:adfDashboard
 * @element div
 * @restrict EA
 * @scope
 * @description
 *
 * `adfDashboard` is a directive which renders the dashboard with all its
 * components. The directive requires a name attribute. The name of the
 * dashboard can be used to store the model.
 *
 * @param {string} name name of the dashboard. This attribute is required.
 * @param {boolean=} editable false to disable the editmode of the dashboard.
 * @param {boolean=} collapsible true to make widgets collapsible on the dashboard.
 * @param {boolean=} maximizable true to add a button for open widgets in a large modal panel.
 * @param {boolean=} enableConfirmDelete true to ask before remove an widget from the dashboard.
 * @param {string=} structure the default structure of the dashboard.
 * @param {object=} adfModel model object of the dashboard.
 * @param {function=} adfWidgetFilter function to filter widgets on the add dialog.
 * @param (string) description description of model page
 */

angular.module('adf')
    .directive('adfDashboard', ['$rootScope', '$log', 'dashboard', 'adfTemplatePath', '$aside', 'applicationNavigationItems', '$stateParams',
        function ($rootScope, $log, dashboard, adfTemplatePath, $aside, applicationNavigationItems, $stateParams) {
            'use strict';

            $log.log('DASHBOARD');

            function stringToBoolean(string) {
                switch (angular.isDefined(string) ? string.toLowerCase() : null) {
                    case 'true':
                    case 'yes':
                    case '1':
                        return true;
                    case 'false':
                    case 'no':
                    case '0':
                    case null:
                        return false;
                    default:
                        return Boolean(string);
                }
            }

            function copyWidgets(source, target) {
                if (source.widgets && source.widgets.length > 0) {
                    var w = source.widgets.shift();
                    while (w) {
                        target.widgets.push(w);
                        w = source.widgets.shift();
                    }
                }
            }

            /**
             * Copy widget from old columns to the new model
             * @param object root the model
             * @param array of columns
             * @param counter
             */
            function fillStructure(root, columns, counter) {
                counter = counter || 0;

                if (angular.isDefined(root.rows)) {
                    angular.forEach(root.rows, function (row) {
                        angular.forEach(row.columns, function (column) {
                            // if the widgets prop doesn't exist, create a new array for it.
                            // this allows ui.sortable to do it's thing without error
                            if (!column.widgets) {
                                column.widgets = [];
                            }

                            // if a column exist at the counter index, copy over the column
                            if (angular.isDefined(columns[counter])) {
                                // do not add widgets to a column, which uses nested rows
                                if (!angular.isDefined(column.rows)) {
                                    copyWidgets(columns[counter], column);
                                    counter++;
                                }
                            }

                            // run fillStructure again for any sub rows/columns
                            counter = fillStructure(column, columns, counter);
                        });
                    });
                }
                return counter;
            }

            /**
             * Read Columns: recursively searches an object for the 'columns' property
             * @param object model
             * @param array  an array of existing columns; used when recursion happens
             */
            function readColumns(root, columns) {
                columns = columns || [];

                if (angular.isDefined(root.rows)) {
                    angular.forEach(root.rows, function (row) {
                        angular.forEach(row.columns, function (col) {
                            columns.push(col);
                            // keep reading columns until we can't any more
                            readColumns(col, columns);
                        });
                    });
                }

                return columns;
            }

            function changeStructure(model, structure) {
                var columns = readColumns(model);
                var counter = 0;

                model.rows = angular.copy(JSON.parse(structure.content).rows);  // the parameter is JSON.parse(structure.content).rows
                                                                                // because of the structure passed from DB and to allow updating layout in DB
                model.structure = structure.title;                              // assign new structure name

                while (counter < columns.length) {
                    counter = fillStructure(model, columns, counter);
                }
            }

            function createConfiguration(type) {
                var cfg = {};
                var config = dashboard.widgets[type].config;
                if (config) {
                    cfg = angular.copy(config);
                }
                return cfg;
            }

            /**
             * Find first widget column in model.
             *
             * @param dashboard model
             */
            function findFirstWidgetColumn(model) {
                var column = null;
                if (!angular.isArray(model.rows)) {
                    $log.error('model does not have any rows');
                    return null;
                }
                for (var i = 0; i < model.rows.length; i++) {
                    var row = model.rows[i];
                    if (angular.isArray(row.columns)) {
                        for (var j = 0; j < row.columns.length; j++) {
                            var col = row.columns[j];
                            if (!col.rows) {
                                column = col;
                                break;
                            }
                        }
                    }
                    if (column) {
                        break;
                    }
                }
                return column;
            }

            /**
             * Adds the widget to first column of the model.
             *
             * @param dashboard model
             * @param widget to add to model
             */
            function addNewWidgetToModel(model, widget) {
                if (model) {
                    var column = findFirstWidgetColumn(model);
                    if (column) {
                        if (!column.widgets) {
                            column.widgets = [];
                        }
                        column.widgets.unshift(widget);
                    } else {
                        $log.error('could not find first widget column');
                    }
                } else {
                    $log.error('model is undefined');
                }
            }

            return {
                replace: true,
                restrict: 'EA',
                transclude: false,
                scope: {
                    structure: '@',
                    name: '@',
                    collapsible: '@',
                    editable: '@',
                    maximizable: '@',
                    adfModel: '=',
                    adfWidgetFilter: '='
                },
                controller: function ($scope) {
                    var model = {};
                    var structure = {};
                    var widgetFilter = null;
                    var structureName = {};
                    var name = $scope.name;

                    $scope.$on(platformParameters.events.ADF_WIDGET_DELETED_EVENT, function (event) {
                        $scope.$emit(platformParameters.events.ADF_DASHBOARD_CHANGED_EVENT, $scope.name, $scope.adfModel);
                    })

                    // Watching for changes on adfModel
                    $scope.$watch('adfModel', function (oldVal, newVal) {

                        $log.log('CHANGED!!!!!!!!!!!!!!!!!!!!!');

                        // has model changed or is the model attribute not set
                        if (newVal !== null || (oldVal === null && newVal === null)) {
                            model = $scope.adfModel;
                            widgetFilter = $scope.adfWidgetFilter;
                            if (!model || !model.rows) {
                                structureName = $scope.structure;
                                structure = dashboard.structures[structureName];
                                if (structure) {
                                    if (model) {
                                        model.rows = angular.copy(structure).rows;
                                    } else {
                                        model = angular.copy(structure);
                                    }
                                    model.structure = structureName;
                                } else {
                                    $log.error('could not find structure ' + structureName);
                                }
                            }

                            if (model) {
                                if (!model.title) {
                                    model.title = 'Dashboard';
                                }
                                if (!model.description) {
                                    model.description = '';
                                }
                                $scope.model = model;
                            } else {
                                $log.error('could not find or create model');
                            }
                        }
                    }, true);

                    // edit mode
                    $scope.editMode = false;
                    $scope.editClass = '';

                    $scope.toggleEditMode = function (preventSave) {
                        $scope.editMode = !$scope.editMode;
                        if ($scope.editMode) {
                            $scope.modelCopy = angular.copy($scope.adfModel, {});
                        }

                        if (!$scope.editMode && !preventSave) {
                            $scope.$emit(platformParameters.events.ADF_DASHBOARD_CHANGED_EVENT, name, model);
                        }
                    };

                    $scope.$on(platformParameters.events.ADF_TOGGLE_EDIT_MODE_EVENT, function (event, preventSave) {
                        $scope.toggleEditMode(preventSave);
                        $scope.$emit(platformParameters.events.ADF_TOGGLE_EDIT_MODE_RESPONSE_EVENT, $scope.editMode);
                    });

                    $scope.collapseAll = function (collapseExpandStatus) {
                        $rootScope.$broadcast('adfDashboardCollapseExapand', {collapseExpandStatus: collapseExpandStatus});
                    };

                    /*$scope.cancelEditMode = function(){
                     $scope.editMode = false;
                     $scope.modelCopy = angular.copy($scope.modelCopy, $scope.adfModel);
                     $rootScope.$broadcast('adfDashboardEditsCancelled');
                     };*/

                    // edit dashboard settings
                    $scope.editDashboardDialog = function () {
                        var editDashboardScope = $scope.$new();
                        // create a copy of the title, to avoid changing the title to
                        // "dashboard" if the field is empty
                        editDashboardScope.copy = {
                            title: model.title,
                            description: model.description
                        };
                        editDashboardScope.structures = dashboard.structures;
                        var instance = $aside.open({
                            scope: editDashboardScope,
                            placement: 'left',
                            templateUrl: adfTemplatePath + 'dashboard-edit.html',
                            size: 'md',
                            backdrop: 'static'
                        });
                        $scope.changeStructure = function (name, structure) {
                            $log.info('change structure to ' + name);
                            changeStructure(model, structure);
                            $rootScope.$broadcast(platformParameters.events.ADF_STRUCTURE_CHANGED_EVENT, structure);
                        };
                        editDashboardScope.closeDialog = function () {

                            if (!editDashboardScope.copy.title) {
                                $log.log('Form is invalid and could not be saved.');
                                editDashboardScope.$broadcast('show-errors-check-validity');
                                return;
                            }
                            // copy the new title back to the model
                            model.title = editDashboardScope.copy.title;
                            // close modal and destroy the scope
                            instance.dismiss();
                            editDashboardScope.$destroy();
                            applicationNavigationItems.editSidebarItem($stateParams.id, model.title);
                            $scope.$emit(platformParameters.events.ADF_DASHBOARD_CHANGED_EVENT, name, model);
                        };
                        editDashboardScope.cancelDialog = function () {
                            // close modal and destroy the scope
                            instance.dismiss();
                            editDashboardScope.$destroy();
                        };
                    };

                    $scope.$on(platformParameters.events.ADF_EDIT_DASHBOARD_EVENT, function (event) {
                        $scope.editDashboardDialog();
                    });

                    // add widget dialog
                    $scope.addWidgetDialog = function () {
                        var addScope = $scope.$new();
                        var model = $scope.model;
                        var widgets;
                        if (angular.isFunction(widgetFilter)) {
                            widgets = {};
                            angular.forEach(dashboard.widgets, function (widget, type) {
                                if (widgetFilter(widget, type, model)) {
                                    widgets[type] = widget;
                                }
                            });
                        } else {
                            widgets = dashboard.widgets;
                        }
                        addScope.widgets = widgets;
                        var opts = {
                            scope: addScope,
                            placement: 'left',
                            templateUrl: adfTemplatePath + 'widget-add.html',
                            size: 'sx',
                            backdrop: 'static'
                        };
                        var instance = $aside.open(opts);
                        addScope.addWidget = function (widgetType, widget) {
                            var w = {
                                id: widget.id,
                                type: widgetType,
                                title: widget.title,
                                moduleType: widget.moduleType,
                                config: createConfiguration(widgetType),
                                wid: dashboard.id()
                            };
                            addNewWidgetToModel(model, w);
                            $scope.$emit(platformParameters.events.ADF_DASHBOARD_CHANGED_EVENT, name, model);
                            // close and destroy
                            instance.dismiss();
                            addScope.$destroy();
                        };
                        addScope.closeDialog = function () {
                            // close and destroy
                            instance.dismiss();
                            addScope.$destroy();
                        };
                    };

                    $scope.$on(platformParameters.events.ADF_ADD_WIDGET_EVENT, function (event) {
                        $scope.addWidgetDialog();
                    });
                },
                link: function ($scope, $element, $attr) {

                    $scope.$on('FULL_SCREEN_MODE', function (event, moduleId, fullScreenMode) {
                        var dashboardSubContainer = angular.element(document.getElementById('dashboard-sub-container'));

                        if (fullScreenMode) {
                            dashboardSubContainer.addClass('hidden');
                            var dashboardContainer = angular.element(document.getElementById('dashboard-container'));
                            var moduleContent = angular.element(document.getElementById('module-content-' + moduleId));
                            var module = angular.element('<div class="container-fluid modules-wrapper custom-column-container ng-scope ng-isolate-scope" id="adf-dashboard-row-full-screen"><div class="column ng-pristine ng-untouched ng-valid ng-scope ng-isolate-scope col-md-12"><div id="module-full-screen" class="module"></div></div></div>');
                            dashboardContainer.append(module);
                            var moduleFullScreen = angular.element(document.getElementById('module-full-screen'));
                            moduleFullScreen.append(moduleContent);
                        } else {
                            dashboardSubContainer.removeClass('hidden');
                            var moduleContent = angular.element(document.getElementById('module-content-' + moduleId));
                            var module = angular.element(document.getElementById('module-' + moduleId));
                            module.append(moduleContent);
                            var moduleFullScreen = angular.element(document.getElementById('adf-dashboard-row-full-screen'));
                            moduleFullScreen.remove();
                        }
                    });

                    // pass options to scope
                    var options = {
                        name: $attr.name,
                        editable: true,
                        enableConfirmDelete: stringToBoolean($attr.enableconfirmdelete),
                        maximizable: stringToBoolean($attr.maximizable),
                        collapsible: stringToBoolean($attr.collapsible)
                    };
                    if (angular.isDefined($attr.editable)) {
                        options.editable = stringToBoolean($attr.editable);
                    }
                    $scope.options = options;
                },
                templateUrl: adfTemplatePath + 'dashboard.html'
            };
        }]);;/*
 * The MIT License
 *
 * Copyright (c) 2015, Sebastian Sdorra
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

'use strict';

/**
 * @ngdoc object
 * @name adf.dashboardProvider
 * @description
 *
 * The dashboardProvider can be used to register structures and widgets.
 */
angular.module('adf.provider', [])
    .provider('dashboard', function(){

        var widgets = {};
        var widgetsPath = '';
        var structures = {};
        var messageTemplate = '<div class="alert alert-danger">{}</div>';
        var loadingTemplate = '\
      <div class="progress progress-striped active">\n\
        <div class="progress-bar" role="progressbar" style="width: 100%">\n\
          <span class="sr-only">loading ...</span>\n\
        </div>\n\
      </div>';

        /**
         * @ngdoc method
         * @name adf.dashboardProvider#widget
         * @methodOf adf.dashboardProvider
         * @description
         *
         * Registers a new widget.
         *
         * @param {string} name of the widget
         * @param {object} widget to be registered.
         *
         *   Object properties:
         *
         *   - `title` - `{string=}` - The title of the widget.
         *   - `description` - `{string=}` - Description of the widget.
         *   - `config` - `{object}` - Predefined widget configuration.
         *   - `controller` - `{string=|function()=}` - Controller fn that should be
         *      associated with newly created scope of the widget or the name of a
         *      {@link http://docs.angularjs.org/api/angular.Module#controller registered controller}
         *      if passed as a string.
         *   - `controllerAs` - `{string=}` - A controller alias name. If present the controller will be
         *      published to scope under the `controllerAs` name.
         *   - `template` - `{string=|function()=}` - html template as a string.
         *   - `templateUrl` - `{string=}` - path to an html template.
         *   - `reload` - `{boolean=}` - true if the widget could be reloaded. The default is false.
         *   - `resolve` - `{Object.<string, function>=}` - An optional map of dependencies which should
         *      be injected into the controller. If any of these dependencies are promises, the widget
         *      will wait for them all to be resolved or one to be rejected before the controller is
         *      instantiated.
         *      If all the promises are resolved successfully, the values of the resolved promises are
         *      injected.
         *
         *      The map object is:
         *      - `key` – `{string}`: a name of a dependency to be injected into the controller.
         *      - `factory` - `{string|function}`: If `string` then it is an alias for a service.
         *        Otherwise if function, then it is {@link http://docs.angularjs.org/api/AUTO.$injector#invoke injected}
         *        and the return value is treated as the dependency. If the result is a promise, it is
         *        resolved before its value is injected into the controller.
         *   - `edit` - `{object}` - Edit modus of the widget.
         *      - `controller` - `{string=|function()=}` - Same as above, but for the edit mode of the widget.
         *      - `template` - `{string=|function()=}` - Same as above, but for the edit mode of the widget.
         *      - `templateUrl` - `{string=}` - Same as above, but for the edit mode of the widget.
         *      - `resolve` - `{Object.<string, function>=}` - Same as above, but for the edit mode of the widget.
         *      - `reload` - {boolean} - true if the widget should be reloaded, after the edit mode is closed.
         *        Default is true.
         *
         * @returns {Object} self
         */
        this.widget = function(name, widget){
            var w = angular.extend({reload: false}, widget);
            if ( w.edit ){
                var edit = {reload: true};
                angular.extend(edit, w.edit);
                w.edit = edit;
            }
            widgets[name] = w;
            return this;
        };

        /**
         * @ngdoc method
         * @name adf.dashboardProvider#widgetsPath
         * @methodOf adf.dashboardProvider
         * @description
         *
         * Sets the path to the directory which contains the widgets. The widgets
         * path is used for widgets with a templateUrl which contains the
         * placeholder {widgetsPath}. The placeholder is replaced with the
         * configured value, before the template is loaded, but the template is
         * cached with the unmodified templateUrl (e.g.: {widgetPath}/src/widgets).
         * The default value of widgetPaths is ''.
         *
         *
         * @param {string} path to the directory which contains the widgets
         *
         * @returns {Object} self
         */
        this.widgetsPath = function(path){
            widgetsPath = path;
            return this;
        };

        /**
         * @ngdoc method
         * @name adf.dashboardProvider#structure
         * @methodOf adf.dashboardProvider
         * @description
         *
         * Registers a new structure.
         *
         * @param {string} name of the structure
         * @param {object} structure to be registered.
         *
         *   Object properties:
         *
         *   - `rows` - `{Array.<Object>}` - Rows of the dashboard structure.
         *     - `styleClass` - `{string}` - CSS Class of the row.
         *     - `columns` - `{Array.<Object>}` - Columns of the row.
         *       - `styleClass` - `{string}` - CSS Class of the column.
         *
         * @returns {Object} self
         */
        this.structure = function(name, structure){
            structures[name] = structure;
            return this;
        };

        /**
         * @ngdoc method
         * @name adf.dashboardProvider#messageTemplate
         * @methodOf adf.dashboardProvider
         * @description
         *
         * Changes the template for messages.
         *
         * @param {string} template for messages.
         *
         * @returns {Object} self
         */
        this.messageTemplate = function(template){
            messageTemplate = template;
            return this;
        };

        /**
         * @ngdoc method
         * @name adf.dashboardProvider#loadingTemplate
         * @methodOf adf.dashboardProvider
         * @description
         *
         * Changes the template which is displayed as
         * long as the widget resources are not resolved.
         *
         * @param {string} loading template
         *
         * @returns {Object} self
         */
        this.loadingTemplate = function(template){
            loadingTemplate = template;
            return this;
        };

        /**
         * @ngdoc service
         * @name adf.dashboard
         * @description
         *
         * The dashboard holds all options, structures and widgets.
         *
         * @property {Array.<Object>} widgets Array of registered widgets.
         * @property {string} widgetsPath Default path for widgets.
         * @property {Array.<Object>} structures Array of registered structures.
         * @property {string} messageTemplate Template for messages.
         * @property {string} loadingTemplate Template for widget loading.
         *
         * @returns {Object} self
         */
        this.$get = function(){
            var cid = 0;

            return {
                widgets: widgets,
                widgetsPath: widgetsPath,
                structures: structures,
                messageTemplate: messageTemplate,
                loadingTemplate: loadingTemplate,
                structure: this.structure,
                widget: this.widget,

                /**
                 * @ngdoc method
                 * @name adf.dashboard#id
                 * @methodOf adf.dashboard
                 * @description
                 *
                 * Creates an ongoing numeric id. The method is used to create ids for
                 * columns and widgets in the dashboard.
                 */
                id: function(){
                    return ++cid;
                }
            };
        };

    });
;/*
 * The MIT License
 *
 * Copyright (c) 2015, Sebastian Sdorra
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/* global angular */
angular.module('adf')
    .directive('adfDashboardRow', ['$compile', 'adfTemplatePath', 'columnTemplate', function ($compile, adfTemplatePath, columnTemplate) {
        'use strict';

        return {
            restrict: 'E',
            replace: true,
            scope: {
                row: '=',
                adfModel: '=',
                editMode: '=',
                options: '='
            },
            templateUrl: adfTemplatePath + 'dashboard-row.html',
            link: function ($scope, $element) {
                if (angular.isDefined($scope.row.columns) && angular.isArray($scope.row.columns)) {
                    $compile(columnTemplate)($scope, function(cloned) {
                        $element.append(cloned);
                    });
                }
            }
        };
    }]);
;/*
 * The MIT License
 *
 * Copyright (c) 2015, Sebastian Sdorra
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

'use strict';

angular.module('adf')
    .directive('adfWidgetContent', ['$log', '$q', '$sce', '$http', '$templateCache', '$compile', '$controller', '$injector', '$rootScope', 'dashboard', '$timeout', '$stateParams',
        function ($log, $q, $sce, $http, $templateCache, $compile, $controller, $injector, $rootScope, dashboard, $timeout, $stateParams) {

            $log.log('adf adfWidgetContent +++ !!!!!!!!!');

            function parseUrl(url) {
                var parsedUrl = url;
                if (url.indexOf('{widgetsPath}') >= 0) {
                    parsedUrl = url.replace('{widgetsPath}', dashboard.widgetsPath)
                        .replace('//', '/');
                    if (parsedUrl.indexOf('/') === 0) {
                        parsedUrl = parsedUrl.substring(1);
                    }
                }
                return parsedUrl;
            }

            function getTemplate(widget) {
                var deferred = $q.defer();
                if (widget.template) {
                    deferred.resolve(widget.template);
                } else if (widget.templateUrl) {
                    // try to fetch template from cache
                    var tpl = $templateCache.get(widget.templateUrl);
                    if (tpl) {
                        deferred.resolve(tpl);
                    } else {
                        var url = $sce.getTrustedResourceUrl(parseUrl(widget.templateUrl));
                        $http.get(url)
                            .success(function (response) {
                                // put response to cache, with unmodified url as key
                                $templateCache.put(widget.templateUrl, response);
                                deferred.resolve(response);
                            })
                            .error(function () {
                                deferred.reject('could not load template');
                            });
                    }
                }

                return deferred.promise;
            }

            function compileWidget($scope, $element, currentScope) {
                var model = $scope.model;
                var content = $scope.content;

                // display loading template
                $element.html(dashboard.loadingTemplate);

                // create new scope
                var templateScope = $scope.$new();

                // pass config object to scope
                if (!model.config) {
                    model.config = {};
                }

                templateScope.config = model.config;

                // local injections
                var base = {
                    $scope: templateScope,
                    widget: model,
                    config: model.config
                };

                // get resolve promises from content object
                var resolvers = {};
                resolvers.$tpl = getTemplate(content);
                if (content.resolve) {
                    angular.forEach(content.resolve, function (promise, key) {
                        if (angular.isString(promise)) {
                            resolvers[key] = $injector.get(promise);
                        } else {
                            resolvers[key] = $injector.invoke(promise, promise, base);
                        }
                    });
                }

                // resolve all resolvers
                $q.all(resolvers).then(function (locals) {
                    angular.extend(locals, base);

                    // compile & render template
                    var template = locals.$tpl;
                    $element.html(template);
                    if (content.controller) {
                        var templateCtrl = $controller(content.controller, locals);
                        if (content.controllerAs) {
                            templateScope[content.controllerAs] = templateCtrl;
                        }
                        $element.children().data('$ngControllerController', templateCtrl);
                    }
                    $compile($element.contents())(templateScope);
                }, function (reason) {
                    // handle promise rejection
                    var msg = 'Could not resolve all promises';
                    if (reason) {
                        msg += ': ' + reason;
                    }
                    $log.warn(msg);
                    $element.html(dashboard.messageTemplate.replace(/{}/g, msg));
                });

                // destroy old scope
                if (currentScope) {
                    currentScope.$destroy();
                }

                return templateScope;
            }

            return {
                replace: true,
                restrict: 'EA',
                transclude: false,
                scope: {
                    model: '=',
                    content: '='
                },
                link: function ($scope, $element) {

                    var currentScope = compileWidget($scope, $element, null);
                    $scope.$on('widgetConfigChanged', function () {
                        currentScope = compileWidget($scope, $element, currentScope);
                    });
                    $scope.$on('widgetReload', function () {
                        currentScope = compileWidget($scope, $element, currentScope);
                    });

                    $log.log('Before binding module ready');
                    $scope.$on(platformParameters.events.MODULE_READY, function () {
                        if ($scope.model.id == null) {
                            return;
                        }
                        $log.log('Will reload module id: ' + $scope.model.id);
                        $scope.$broadcast(platformParameters.events.RELOAD_MODULE, $scope.model.id, $stateParams.mode);
                    });

                    $scope.$on(platformParameters.events.MODULE_FORBIDDEN, function () {

                        $log.log('Will show module is forbidden: ' + $scope.model.id);
                        $scope.$emit(platformParameters.events.SHOW_PERMISSION_DENIED_TO_MODULE);
                    });

                    $scope.$on(platformParameters.events.START_REQUEST, function () {
                        $log.log('Will show loader: ' + $scope.model.id);
                        $scope.$emit(platformParameters.events.SHOW_LOADER);
                    });
                    $scope.$on(platformParameters.events.STOP_REQUEST, function () {
                        $log.log('Will hide loader: ' + $scope.model.id);
                        $scope.$emit(platformParameters.events.HIDE_LOADER);
                    });

                    $scope.$watch('model.id', function (newId, oldId) {
                        if(oldId == null && newId != null){
                            $scope.$broadcast(platformParameters.events.RELOAD_MODULE, newId, $stateParams.mode);
                        }
                    }, true);

                }
            };

        }]);
;/*
 * The MIT License
 *
 * Copyright (c) 2015, Sebastian Sdorra
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

'use strict';

angular.module('adf')
    .directive('adfWidget', ['$log', '$uibModal', '$rootScope', 'dashboard', 'adfTemplatePath', 'modalWindow', function ($log, $uibModal, $rootScope, dashboard, adfTemplatePath, modalWindow) {

        $log.log('adf widget !!!!!!!!!');


        function preLink($scope) {
            var definition = $scope.definition;

            $log.log('preLink adf widget !!!!!!!!!', definition);

            if (definition) {
                var w = dashboard.widgets[definition.type];
                if (w) {
                    // pass title
                    if (!definition.title) {
                        definition.title = w.title;
                    }

                    if (!definition.titleTemplateUrl) {
                        definition.titleTemplateUrl = adfTemplatePath + 'widget-title.html';
                    }

                    // set id for sortable
                    if (!definition.wid) {
                        definition.wid = dashboard.id();
                    }

                    // pass copy of widget to scope
                    $scope.widget = angular.copy(w);

                    // create config object
                    var config = definition.config;
                    if (config) {
                        if (angular.isString(config)) {
                            config = angular.fromJson(config);
                        }
                    } else {
                        config = {};
                    }

                    // pass config to scope
                    $scope.config = config;

                    // collapse exposed $scope.widgetState property
                    if (!$scope.widgetState) {
                        $scope.widgetState = {};
                        $scope.widgetState.isCollapsed = false;
                        $scope.widgetState.isLoading = false;
                        $scope.widgetState.permissionDenied = false;
                    }

                } else {
                    $log.warn('could not find widget ' + definition.type);
                }
            } else {
                $log.debug('definition not specified, widget was probably removed');
            }
        }

        function postLink($scope, $element) {
            if ($scope.definition) {

                var removeWidget = function () {
                    var column = $scope.col;
                    if (column) {
                        var index = column.widgets.indexOf($scope.definition);
                        if (index >= 0) {
                            column.widgets.splice(index, 1);
                        }
                    }
                    $element.remove();
                    $scope.$emit(platformParameters.events.ADF_WIDGET_DELETED_EVENT);
                };

                $scope.remove = function () {
                    modalWindow.openConfirmWindow('Delete module?', 'The module will be deleted permanently. Are you sure you want to perform this operation?', 'warning', removeWidget);
                };

                $scope.reload = function () {
                    $scope.$broadcast('widgetReload');
                };

                $scope.edit = function () {
                    $scope.$broadcast('CONFIGURATION_MODE', $scope.definition.id);
                };
            } else {
                $log.debug('widget not found');
            }
        }

        return {
            replace: true,
            restrict: 'EA',
            transclude: false,
            templateUrl: adfTemplatePath + 'widget.html',
            scope: {
                definition: '=',
                col: '=column',
                editMode: '=',
                options: '=',
                widgetState: '='
            },

            controller: function ($scope) {
                var loaderCounter = 0;

                $scope.$on("adfDashboardCollapseExapand", function (event, args) {
                    $scope.widgetState.isCollapsed = args.collapseExpandStatus;
                });

                $scope.$on(platformParameters.events.SHOW_LOADER, function () {
                    $log.log('SHOW_LOADER');
                    loaderCounter++;
                    $scope.widgetState.isLoading = true;
                });
                $scope.$on(platformParameters.events.HIDE_LOADER, function () {
                    $log.log('HIDE_LOADER');
                    if (--loaderCounter === 0) {
                        $scope.widgetState.isLoading = false;
                        $log.log('HIDE');
                    }
                });
                $scope.$on(platformParameters.events.SHOW_PERMISSION_DENIED_TO_MODULE, function () {
                    $log.log('SHOW_PERMISSION_DENIED_TO_MODULE');
                    $scope.widgetState.permissionDenied = true;
                });

                $scope.fullScreenMode = false;

                $scope.toggleFullScreenMode = function () {
                    $scope.fullScreenMode = !$scope.fullScreenMode;
                    $scope.$emit('FULL_SCREEN_MODE', $scope.definition.wid, $scope.fullScreenMode);
                };
            },

            compile: function compile() {

                /**
                 * use pre link, because link of widget-content
                 * is executed before post link widget
                 */
                return {
                    pre: preLink,
                    post: postLink
                };
            }
        };

    }]);
;var pageControllers = angular.module('pageControllers', []);

pageControllers.controller('PageController', ['$scope', '$log', '$state','$stateParams', 'PageModel', 'PageModelParser', 'toaster',
    function ($scope, $log, $state, $stateParams, PageModel, PageModelParser, toaster) {

        $log.log('PageController');

        var model = $scope.model;

        if (!model) {
            // set default model to avoid js console errors, initializing with empty layout
            model = {
                title: "Sample dashboard",
                structure: "1 (100)",
                rows: [{
                    columns: [{
                        styleClass: "col-md-12",
                        widgets: []
                    }]
                }]
            };
        }

        $scope.model = model;
        $scope.collapsible = true;
        $scope.maximizable = true;

        var getPage = function (pageId) {
            var query = {id: pageId};
            PageModel.query(query, function (data) {

                $scope.pageModelDto = {page: data.page, dashboardModuleDtos: data.dashboardModuleDtos};
                $scope.name = $scope.pageModelDto.page.name;
                $scope.model = PageModelParser.createModel($scope.pageModelDto);
                $scope.collapsible = true;

            });
        };

        if ($stateParams.id) {
            if($state.current.name == 'application.page') {
                getPage($stateParams.id);
            }
        }

        $scope.$on(platformParameters.events.ADF_DASHBOARD_CHANGED_EVENT, function (event, name, model) {
            var pageModelDto = PageModelParser.createPageModelDto($scope.pageModelDto.page, model);
            savePage(pageModelDto);
        });

        $scope.$on(platformParameters.events.ADF_STRUCTURE_CHANGED_EVENT, function (event, structure) {
            $scope.pageModelDto.page.layout = structure;
        });

        var savePage = function (pageModelDto) {
            $log.log('save page-model...');

            PageModel.update({id: pageModelDto.page.id}, pageModelDto, function (data) {
                $log.log('page updated');
                $scope.pageModelDto = {page: data.page, dashboardModuleDtos: data.dashboardModuleDtos};
                $scope.model = PageModelParser.updateModelModulesNullIds($scope.model, data.dashboardModuleDtos);
                toaster.pop(platformParameters.statusAlertTypes.SUCCESS, 'Updated', 'The page has been updated successfully.');
            })
        };

    }]);

pageControllers.controller('PageDetailsController', ['$scope', '$rootScope', '$http', '$state', '$parse', '$stateParams', '$log', '$uibModalInstance', 'Page', 'Layout', 'applicationNavigationItems', 'toaster',
    function ($scope, $rootScope, $http, $state, $parse, $stateParams, $log, $uibModalInstance, Page, Layout, applicationNavigationItems, toaster) {
        $log.log('PageDetailsController');

        $scope.formErrors = [];
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

        $scope.entity = {};
        $scope.save = function () {
            $log.log('$scope.entityForm.$invalid: ' + $scope.entityForm.$invalid);
            $log.log('$scope.entityForm.$invalid: ', $scope.entityForm);

            if ($scope.entityForm.$invalid) {
                $log.log('Form is invalid and could not be saved.');
                $scope.$broadcast('show-errors-check-validity');
                return;
            }

            $log.log('save() - entity: ', $scope.entity);

            Page.save($scope.entity, function (data) {
                $scope.entityForm.$setPristine();

                $log.log('data.form: ', data);
                angular.forEach(data.form, function (rejectedValue, fieldName) {
                    $log.log('fieldName: ' + fieldName + ', ' + rejectedValue);
                    if (fieldName !== 'id') {
                        $scope.entityForm[fieldName].$setValidity('serverMessage', true);
                    }
                });

                if (data.formErrors.length == 0) {
                    $log.log('Entity has been saved: ', $scope.entity);
                    $uibModalInstance.dismiss();
                    var newSidebarItem = {
                        id: data.form.id,
                        title: data.form.title,
                        state: 'application.page',
                        orderIndex: applicationNavigationItems.sidebarItems.length + 1,
                        isPage: true,
                        iconClass: 'fa fa-file-text-o'
                    };
                    applicationNavigationItems.addSidebarItem(newSidebarItem);
                    toaster.pop(platformParameters.statusAlertTypes.SUCCESS, 'Created', 'The page has been created successfully.');
                    $state.go('application.page', {id: newSidebarItem.id});
                    $rootScope.$broadcast(platformParameters.events.SIDEBAR_ELEMENT_SELECTED, newSidebarItem.id);
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

        $scope.saveForm = function () {
            $log.log('saveForm()');
            $scope.save();
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss();
        };

    }]);
;var platformPageModule = angular.module('platformPageModule', ['ui.router', 'pageControllers', 'pageServices', 'adf',
    'platformLayoutModule']);

platformPageModule.config(['$stateProvider',
    function ($stateProvider) {
        $stateProvider
            .state('application.welcome', {
                url: '/welcome',
                templateUrl: '/application/modules/page/html/index.html',
                controller: 'PageController'
            })
            .state('application.page', {
                url: '/:id',
                templateUrl: '/application/modules/page/html/model.html',
                controller: 'PageController'
            });
    }
]);
;var pageServices = angular.module('pageServices', ['ngResource']);

pageServices.factory('Page', ['$resource',
    function ($resource) {
        return $resource('api/application/pages/:id', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'},
            delete: {method: 'DELETE'}
        });
    }]);

pageServices.factory('PageModel', ['$resource',
    function ($resource) {
        return $resource('api/pages/:id/model', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }]);

pageServices.service('PageModelParser', function () {

    this.createModel = function (pageModelDto) {
        var model = JSON.parse(pageModelDto.page.layout.content);
        model.description = pageModelDto.page.description;
        model.title = pageModelDto.page.title;
        model.structure = pageModelDto.page.layout.title;

        //initialize widgets
        for (var i = 0; i < model.rows.length; i++) {
            var row = model.rows[i];
            for (var j = 0; j < row.columns.length; j++) {
                var column = row.columns[j];
                column.widgets = [];
            }
        }

        //fill widgets
        for (var l = 0; l < pageModelDto.dashboardModuleDtos.length; l++) {
            var dashboardModuleDto = pageModelDto.dashboardModuleDtos[l];
            dashboardModuleDto.config = {};
            model.rows[dashboardModuleDto.rowIndex].columns[dashboardModuleDto.columnIndex].widgets[dashboardModuleDto.orderIndex] = dashboardModuleDto;
        }

        return model;
    };

    this.createPageModelDto = function (page, model) {
        var rowIndex = 0;
        var columnIndex = 0;
        var orderIndex = 0;
        var dashboardModuleDtos = [];

        for (var i = 0; i < model.rows.length; i++) {

            var row = model.rows[i];
            columnIndex = 0;

            for (var j = 0; j < row.columns.length; j++) {

                var column = row.columns[j];
                orderIndex = 0;

                for (var k = 0; k < column.widgets.length; k++) {

                    var module = column.widgets[k];
                    module.rowIndex = rowIndex;
                    module.columnIndex = columnIndex;
                    module.orderIndex = orderIndex;
                    module.frontendId = module.wid;
                    dashboardModuleDtos.push(module);
                    orderIndex++;
                }
                columnIndex++;
            }
            rowIndex++;
        }

        var pageModelDto = {page: page, dashboardModuleDtos: dashboardModuleDtos};
        pageModelDto.page.title = model.title;

        return pageModelDto;
    };

    this.findModuleId = function (frontendId, dashboardModuleDtos) {
        for (var i = 0; i < dashboardModuleDtos.length; i++) {
            if (dashboardModuleDtos[i].frontendId == frontendId) {
                return dashboardModuleDtos[i].id;
            }
        }
        return null;
    };

    this.updateModelModulesNullIds = function (model, dashboardModuleDtos) {

        for (var r = 0; r < model.rows.length; r++) {
            for (var c = 0; c < model.rows[r].columns.length; c++) {
                for (var w = 0; w < model.rows[r].columns[c].widgets.length; w++) {
                    if (model.rows[r].columns[c].widgets[w].id == null) {
                        model.rows[r].columns[c].widgets[w].id = this.findModuleId(model.rows[r].columns[c].widgets[w].wid, dashboardModuleDtos);
                    }
                }
            }
        }

        return model;
    };

});
;var layoutControllers = angular.module('layoutControllers', ['adf.provider']);

layoutControllers.controller('LayoutController',['$scope', '$http', '$log', 'Layout', 'dashboard', function($scope, $http, $log, Layout, dashboard) {

    $scope.query = Layout.query(function() {
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

            angular.forEach(data.content, function(item) {
                dashboard.structure(item.title, item); // ADF needs JSON.parse(item.content) (see adf's provider.js structure function)
                                                        // but it needed to be handled on the dashboard side to allow layout update
            })
        });
    };

    $scope.read();

}]);;var platformLayoutModule = angular.module('platformLayoutModule', ['layoutControllers', 'layoutServices']);;var structureServices = angular.module('layoutServices', ['ngResource']);

structureServices.factory('Layout', ['$resource',
    function ($resource) {
        return $resource('/api/dashboard/:id', {}, {
            query: {method: 'GET'/*, isArray: false*/},
            update: {method: 'PUT'}
        });
    }]);;var userControllers = angular.module('userControllers', []);

userControllers.controller('UserDetailsController', ['$scope', '$http', '$state', '$stateParams', '$log', 'User', 'UserPassword', '$parse', '$uibModalInstance', 'userId', 'toaster',
    function ($scope, $http, $state, $stateParams, $log, User, UserPassword, $parse, $uibModalInstance, userId, toaster) {
        $log.log('UserDetailsController');

        $scope.user = {};
        $scope.userGender = ['MALE', 'FEMALE'];
        $scope.password = {
          currentPassword: null, newPassword: null, retypeNewPassword: null
        };
        $scope.formErrors = [];

        $scope.today = function() {
            $scope.user.birthday = new Date();
        };
        $scope.today();

        $scope.clear = function () {
            $scope.user.birthday = null;
        };

        $scope.open = function($event) {
            $scope.status.opened = true;
        };

        $scope.setDate = function(year, month, day) {
            $scope.user.birthday = new Date(year, month, day);
        };

        $scope.status = {
            opened: false
        };

        $scope.user.gender = null;

        $scope.cancel = function () {
            $uibModalInstance.dismiss();
        };

        $scope.setFormScope = function(scope){
            $scope.formScope = scope;
        };

        var getUser = function (id) {
            $log.log('selected user id:', id);
            if (id) {
                User.get({id: id}, function (data) {
                    $scope.user = data;
                    $log.log('User has been got: ', $scope.user);
                });
            } else {
                $scope.user = {};
            }
        };

        getUser(userId);

        var updateUser = function () {
            $scope.userForm = $scope.formScope.userForm;
            $scope.selectedForm = $scope.userForm;
            $log.log('$scope.userForm.$invalid: ' + $scope.userForm.$invalid);
            $log.log('$scope.userForm.$invalid: ', $scope.userForm);

            if($scope.userForm.$invalid){
                $log.log('Form is invalid and could not be saved.');
                $scope.$broadcast('show-errors-check-validity');
                return;
            }

            $log.log('save() - user: ', $scope.user);

            User.update({id: $scope.user.id}, $scope.user, function (data) {
                $scope.userForm.$setPristine();
                $log.log('data.form: ' , data);
                angular.forEach(data.form, function (rejectedValue, fieldName) {
                    $log.log('fieldName: ' + fieldName + ', ' + rejectedValue);
                    if(fieldName !== 'id'){
                        $scope.userForm[fieldName].$setValidity('serverMessage', true);
                    }
                });

                if (data.formErrors.length == 0) {
                    $log.log('Entity has been saved: ', $scope.user);
                    $uibModalInstance.dismiss();
                    toaster.pop(platformParameters.statusAlertTypes.SUCCESS, 'Updated', 'The account has been updated successfully.');
                    return;
                }

                for (var i = 0; i < data.formErrors.length; i++) {
                    var fieldName = data.formErrors[i].field;
                    $log.log('fieldName: ' + fieldName);
                    var message = data.formErrors[i].message;
                    var serverMessage = $parse('userForm.' + fieldName + '.$error.serverMessage');
                    $scope.userForm[fieldName].$setValidity('serverMessage', false);
                    serverMessage.assign($scope, message);
                }

                $scope.$broadcast('show-errors-check-validity');
            });
        };

        var changeUserPassword = function () {
            $scope.userChangePasswordForm = $scope.formScope.userChangePasswordForm;
            $scope.selectedForm = $scope.userChangePasswordForm;
            $log.log('$scope.userChangePasswordForm.$invalid: ' + $scope.userChangePasswordForm.$invalid);
            $log.log('$scope.userChangePasswordForm.$invalid: ', $scope.userChangePasswordForm);

            if($scope.userChangePasswordForm.$invalid){
                $log.log('Form is invalid and could not be saved.');
                $scope.$broadcast('show-errors-check-validity');
                return;
            }
            $log.log('changeUserPassword() - password:', $scope.password.currentPassword, $scope.password.newPassword, $scope.password.retypeNewPassword);

            UserPassword.update({id: $scope.user.id}, $scope.password, function (data) {
                $scope.userChangePasswordForm.$setPristine();
                $log.log('data.form: ' , data);
                angular.forEach(data.form, function (rejectedValue, fieldName) {
                    $log.log('fieldName: ' + fieldName + ', ' + rejectedValue);
                    if(fieldName !== 'id'){
                        $scope.userChangePasswordForm[fieldName].$setValidity('serverMessage', true);
                    }
                });

                if (data.formErrors.length == 0) {
                    $log.log('Entity has been saved: ', $scope.user);
                    $uibModalInstance.dismiss();
                    toaster.pop(platformParameters.statusAlertTypes.SUCCESS, 'Updated', 'The password has been changed successfully.');
                    return;
                }

                for (var i = 0; i < data.formErrors.length; i++) {
                    var fieldName = data.formErrors[i].field;
                    $log.log('fieldName: ' + fieldName);
                    var message = data.formErrors[i].message;
                    var serverMessage = $parse('userChangePasswordForm.' + fieldName + '.$error.serverMessage');
                    $scope.userChangePasswordForm[fieldName].$setValidity('serverMessage', false);
                    serverMessage.assign($scope, message);
                }

                $scope.$broadcast('show-errors-check-validity');
            })
        };

        $scope.saveForm = function () {
            if ($scope.user.id != null) {

                if(!$scope.selectedForm) {
                    $scope.selectedForm = $scope.formScope.userForm;
                }

                if ($scope.selectedForm.$name == 'userForm') {
                    updateUser();
                } else if ($scope.selectedForm.$name == 'userChangePasswordForm') {
                    changeUserPassword();
                } else if ($scope.selectedForm.$name == 'userRolesForm') {

                }
            }
        };

        $scope.selectForm = function (form) {
            $scope.selectedForm = form;
        }

    }]);;var userDirectives = angular.module('userDirectives', []);

userDirectives.directive("passwordVerify", function() {
    return {
        require: "ngModel",
        scope: {
            passwordVerify: '='
        },
        link: function(scope, element, attrs, ctrl) {
            scope.$watch(function() {
                var combined;

                if (scope.passwordVerify || ctrl.$viewValue) {
                    combined = scope.passwordVerify + '_' + ctrl.$viewValue;
                }
                return combined;
            }, function(value) {
                if (value) {
                    ctrl.$parsers.unshift(function(viewValue) {
                        var origin = scope.passwordVerify;
                        if (origin !== viewValue) {
                            ctrl.$setValidity("passwordVerify", false);
                            return undefined;
                        } else {
                            ctrl.$setValidity("passwordVerify", true);
                            return viewValue;
                        }
                    });
                }
            });
        }
    };
});;var platformUserModule = angular.module('platformUserModule', ['userControllers', 'userDirectives']);