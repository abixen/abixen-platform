(function () {

    'use strict';

    angular
        .module('platformPageModule')
        .controller('PageDetailsController', PageDetailsController);

    PageDetailsController.$inject = [
        '$scope',
        '$rootScope',
        '$http',
        '$state',
        '$parse',
        '$stateParams',
        '$log',
        '$uibModalInstance',
        'Page',
        'Layout',
        'applicationNavigationItems',
        'toaster'
    ];

    function PageDetailsController($scope, $rootScope, $http, $state, $parse, $stateParams, $log, $uibModalInstance, Page, Layout, applicationNavigationItems, toaster) {
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
    }
})();