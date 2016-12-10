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

function AbstractUploaderController(extendedController, FileUploader, X_XSRF_TOKEN, config) {
    var abstractUploaderController = extendedController;

    abstractUploaderController.uploaderUrl = angular.isDefined(config) ? angular.isDefined(config.uploaderUrl) ? config.uploaderUrl : '' : '';
    abstractUploaderController.uploaderAlias = angular.isDefined(config) ? angular.isDefined(config.uploaderAlias) ? config.uploaderAlias : '' : '' ;
    abstractUploaderController.uploaderMethod = angular.isDefined(config) ? angular.isDefined(config.uploaderMethod) ? config.uploaderMethod : 'POST' : 'POST';
    abstractUploaderController.uploaderQueueLimit = angular.isDefined(config) ? angular.isDefined(config.uploaderQueueLimit) ? config.uploaderQueueLimit : 1 : 1;
    abstractUploaderController.uploaderFunctionConfig = angular.isDefined(config) ? angular.isDefined(config.uploaderFunctionConfig) ? config.uploaderFunctionConfig : [] : [];

    abstractUploaderController.uploader = new FileUploader({
        url: abstractUploaderController.uploaderUrl,
        method: abstractUploaderController.uploaderMethod,
        alias: abstractUploaderController.uploaderAlias,
        queueLimit: abstractUploaderController.uploaderQueueLimit,
        headers: {
            'X-XSRF-TOKEN': X_XSRF_TOKEN
        }
    });

    abstractUploaderController.uploader.onAfterAddingAll = onAfterAddingAll;
    abstractUploaderController.uploader.onCompleteAll = undefined;

    if (isFunction(abstractUploaderController.uploaderFunctionConfig)) {
        abstractUploaderController.uploaderFunctionConfig(abstractUploaderController.uploader);
    }

    abstractUploaderController.uploader.filters.push({
        name: 'imageFilter',
        fn: function (item /*{File|FileLikeObject}*/, options) {
            var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
            return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
        }
    });

    function onAfterAddingAll() {
        if (abstractUploaderController.uploader.getNotUploadedItems().length > 1) {
            abstractUploaderController.uploader.removeFromQueue(0);
        }
    }

    function isFunction(functionToCheck) {
        var getType = {};
        return functionToCheck && getType.toString.call(functionToCheck) === '[object Function]';
    }
}