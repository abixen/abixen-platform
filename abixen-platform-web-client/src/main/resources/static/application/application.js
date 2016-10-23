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

};