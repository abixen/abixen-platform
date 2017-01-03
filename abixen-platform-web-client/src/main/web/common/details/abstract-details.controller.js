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

'use strict';

function AbstractDetailsController(extendedController, Resource, responseHandler, $scope, config) {
    var abstractDetailsController = extendedController;

    var onSuccessSaveForm = angular.isDefined(config) ? angular.isDefined(config.onSuccessSaveForm) ? config.onSuccessSaveForm : undefined : undefined;
    var onSuccessGetEntity = angular.isDefined(config) ? angular.isDefined(config.onSuccessGetEntity) ? config.onSuccessGetEntity : undefined : undefined;
    var entitySubObject = angular.isDefined(config) ? angular.isDefined(config.entitySubObject) ? config.entitySubObject : undefined : undefined;

    abstractDetailsController.entity = angular.isDefined(config) ? angular.isDefined(config.initEntity) ? config.initEntity : {} : {};
    abstractDetailsController.validators = angular.isDefined(config) ? angular.isDefined(config.getValidators) ? config.getValidators() : [] : [];

    abstractDetailsController.saveForm = saveForm;

    getEntity(config.entityId, onSuccessGetEntity);

    function getEntity(id, callback) {
        if (id) {
            Resource.get({id: id})
                .$promise
                .then(onGetResult);
        }

        function onGetResult(entity) {
            abstractDetailsController.entity = entity;

            if (callback) {
                callback();
            }
        }
    }

    function saveForm() {
        if (abstractDetailsController.entity.id) {
            update(onSuccessSaveForm);
        } else {
            save(onSuccessSaveForm);
        }
    }

    function save(callback) {

        Resource
            .save(abstractDetailsController.entity)
            .$promise
            .then(onSaveResult);

        function onSaveResult(formValidationResult) {
            abstractDetailsController.entity = formValidationResult.form;
            responseHandler.handle(abstractDetailsController.entityForm, formValidationResult, $scope, entitySubObject);

            if (callback && formValidationResult.formErrors.length === 0) {
                callback();
            }
        }
    }

    function update(callback) {

        Resource
            .update({id: abstractDetailsController.entity.id}, abstractDetailsController.entity)
            .$promise
            .then(onUpdateResult);

        function onUpdateResult(formValidationResult) {
            abstractDetailsController.entity = formValidationResult.form;
            responseHandler.handle(abstractDetailsController.entityForm, formValidationResult, $scope, entitySubObject);

            if (callback && formValidationResult.formErrors.length === 0) {
                callback();
            }
        }
    }
}