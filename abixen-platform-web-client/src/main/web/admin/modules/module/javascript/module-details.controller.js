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
        .module('platformModuleModule')
        .controller('ModuleDetailsController', ModuleDetailsController);

    ModuleDetailsController.$inject = [
        '$scope',
        '$state',
        '$stateParams',
        '$log',
        'Module',
        'responseHandler'
    ];

    function ModuleDetailsController($scope, $state, $stateParams, $log, Module, responseHandler) {
        $log.log('ModuleDetailsController');

        var moduleDetails = this;

        new AbstractDetailsController(moduleDetails, Module, responseHandler, $scope,
            {
                entityId: $stateParams.id,
                getValidators: getValidators,
                onSuccessSaveForm: onSuccessSaveForm
            }
        );

        function onSuccessSaveForm() {
            $state.go('application.modules.list');
        }

        function getValidators() {
            var validators = [];

            validators['title'] =
                [
                    new NotNull(),
                    new Length(6, 40)
                ];

            validators['description'] =
                [
                    new Length(0, 100)
                ];

            return validators;
        }
    }
})();