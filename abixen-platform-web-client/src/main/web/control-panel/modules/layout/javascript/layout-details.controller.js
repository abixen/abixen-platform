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
        .module('platformLayoutModule', [
            'ui.codemirror',
            'angularFileUpload',
            'ngCookies'
        ])
        .controller('LayoutDetailsController', LayoutDetailsController);

    LayoutDetailsController.$inject = [
        '$scope',
        '$http',
        '$state',
        '$stateParams',
        '$log',
        'Layout',
        'FileUploader',
        '$cookies',
        'responseHandler',
        'toaster'
    ];

    function LayoutDetailsController($scope, $http, $state, $stateParams, $log, Layout, FileUploader, $cookies, responseHandler, toaster) {
        $log.log('LayoutDetailsController');

        var layoutDetails = this;
        layoutDetails.beforeSaveForm = beforeSaveForm;
        layoutDetails.isNew = true;

        new AbstractDetailsController(layoutDetails, Layout, responseHandler, $scope,
            {
                entityId: $stateParams.id,
                getValidators: getValidators,
                onSuccessGetEntity: onSuccessGetEntity,
                onSuccessSaveForm: onSuccessSaveForm
            }
        );

        layoutDetails.entity.iconFileName = "default-layout-icon.png";

        if($state.current.name === 'application.layouts.add'){
          layoutDetails.entity.content='';
        }

        function onSuccessGetEntity() {
            layoutDetails.isNew = layoutDetails.entity.id === null || layoutDetails.entity.id === undefined;
        }

        function onSuccessSaveForm() {
            if (layoutDetails.isNew){
                toaster.pop(platformParameters.statusAlertTypes.SUCCESS, 'Created', 'New layout has been created. Now you can upload his icon.');
                $state.go('application.layouts.edit.icon', {id: layoutDetails.entity.id, isNew:true});
            } else {
                $state.go('application.layouts.list');
            }
        }

        function beforeSaveForm() {
            layoutDetails.saveForm();
        }

        layoutDetails.isUploadIcon = false;

        layoutDetails.isUploadLayoutIcon = function () {
            return $state.current.name === 'application.layouts.edit'
        };

        layoutDetails.showUploadLayoutIcon = function () {
            layoutDetails.isUploadIcon = true;
        };

        function getValidators() {
            var validators = [];
            validators['title'] =
                [
                    new NotNull(),
                    new Length(1, 40)
                ];
            validators['content'] =
                [
                    new NotNull(),
                    new Length(1, 4000)
                ];
            validators['iconFileName'] =
                [
                    new NotNull(),
                    new Length(1, 100)
                ];

            return validators;
        }
    }
})();