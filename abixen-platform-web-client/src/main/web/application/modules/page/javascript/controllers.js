var pageControllers = angular.module('pageControllers', []);

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
