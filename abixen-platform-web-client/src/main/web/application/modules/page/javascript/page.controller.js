(function () {

    'use strict';

    angular
        .module('platformPageModule')
        .controller('PageController', PageController);

    PageController.$inject = [
        '$scope',
        '$log',
        '$state',
        '$stateParams',
        'PageModel',
        'PageModelParser',
        'toaster'
    ];

    function PageController($scope, $log, $state, $stateParams, PageModel, PageModelParser, toaster) {
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
            if ($state.current.name == 'application.page') {
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
    }
})();