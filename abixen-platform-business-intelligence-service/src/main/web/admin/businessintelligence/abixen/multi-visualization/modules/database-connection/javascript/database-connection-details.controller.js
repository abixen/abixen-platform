/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

(function () {

    'use strict';

    angular
        .module('platformDatabaseConnectionModule')
        .controller('DatabaseConnectionDetailsController', DatabaseConnectionDetailsController);

    DatabaseConnectionDetailsController.$inject = [
        '$scope',
        'DatabaseConnection',
        '$state',
        '$stateParams',
        '$log',
        'responseHandler',
        'toaster'
    ];

    function DatabaseConnectionDetailsController($scope, DatabaseConnection, $state, $stateParams, $log, responseHandler, toaster) {
        $log.log('DatabaseConnectionDetailsController');
        var databaseConnectionDetails = this;

        new AbstractDetailsController(databaseConnectionDetails, DatabaseConnection, responseHandler, $scope,
            {
                entityId: $stateParams.id,
                getValidators: getValidators,
                onSuccessSaveForm: onSuccessSaveForm
            }
        );

        databaseConnectionDetails.databaseTypes = ['POSTGRES', 'MYSQL', 'ORACLE', 'H2'];
        databaseConnectionDetails.testConnection = testConnection;
        databaseConnectionDetails.goToViewMode = goToViewMode;



        function testConnection() {
            $log.log('testConnection()');
            if (databaseConnectionDetails.entityForm.$invalid) {
                $log.log('Form is invalid and could not be saved.');
                $scope.$broadcast('show-errors-check-validity');
                return;
            }

            DatabaseConnection.test({}, databaseConnectionDetails.entity, function (data) {
                databaseConnectionDetails.entityForm.$setPristine();
                $log.log('data.form: ', data);
                angular.forEach(data.form, function (rejectedValue, fieldName) {
                    $log.log('fiel22dName: ' + fieldName + ', ' + rejectedValue);
                    if (fieldName !== 'id' && fieldName !== 'moduleId') {
                        databaseConnectionDetails.entityForm[fieldName].$setValidity('serverMessage', true);
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
                    databaseConnectionDetails.entityForm[fieldName].$setValidity('serverMessage', false);
                    serverMessage.assign($scope, message);
                }

                $scope.$broadcast('show-errors-check-validity');
            });
        }

        function onSuccessSaveForm() {
            $state.go('application.multiVisualization.modules.databaseConnection.list');
        }

        function goToViewMode() {
            $state.go('application.multiVisualization.modules.databaseConnection.list');
        }

        function getValidators() {
            var validators = [];

            validators['name'] =
                [
                    new NotNull(),
                    new Length(0, 40)
                ];

            validators['description'] =
                [
                    new Length(0, 1000)
                ];

            validators['username'] =
                [
                    new NotNull(),
                    new Length(0, 40)
                ];

            validators['password'] =
                [
                    new NotNull(),
                    new Length(0, 40)
                ];
            validators['databaseHost'] =
                [
                    new NotNull(),
                    new Length(0, 255)
                ];
            validators['databasePort'] =
                [
                    new NotNull()
                ];
            validators['databaseName'] =
                [
                    new NotNull(),
                    new Length(0, 40)
                ];

            return validators;
        }
    }
})();