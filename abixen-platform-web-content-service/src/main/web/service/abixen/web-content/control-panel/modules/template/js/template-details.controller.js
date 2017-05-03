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
        .module('webContentServiceTemplateModule')
        .controller('WebContentServiceTemplateDetailsController', WebContentServiceTemplateDetailsController);

    WebContentServiceTemplateDetailsController.$inject = [
        '$scope',
        '$state',
        '$stateParams',
        '$log',
        'Template',
        'responseHandler'
    ];

    function WebContentServiceTemplateDetailsController($scope, $state, $stateParams, $log, Template, responseHandler) {
        $log.log('WebContentServiceTemplateDetailsController');

        var templateDetails = this;

        templateDetails.ckeditorOptions = {
            language: 'en',
            allowedContent: true,
            entities: false,
            extraPlugins: 'colorbutton'
        };

        angular.extend(templateDetails, new AbstractDetailsController(templateDetails, Template, responseHandler, $scope,
            {
                entityId: $stateParams.id,
                getValidators: getValidators,
                onSuccessSaveForm: onSuccessSaveForm
            }
        ));


        function onSuccessSaveForm() {
            $state.go('application.webContentService.template.list');
        }

        function getValidators() {
            var validators = [];

            validators['name'] =
                [
                    new NotNull(),
                    new Length(6, 255)
                ];

            validators['content'] =
                [
                    new NotNull()
                ];

            return validators;
        }

    }
})();
