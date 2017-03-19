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

    var platformAdminApplicationModules = [
        'ngResource',
        'pascalprecht.translate',
        'platformHttp',
        'platformComponent',
        'platformUtilsModule',
        'platformNavigationModule',
        'platformPermissionModule',
        'platformRoleModule',
        'platformPageModule',
        'platformUserModule',
        'platformLayoutModule',
        'platformModuleModule',
        'platformModuleTypeModule',
        'platformListGridModule',
        'platformThumbModule',
        'ui.bootstrap',
        'ui.router',
        'ngAnimate',
        'ngTouch',
        'ngRoute',
        'toaster',
        'ui.bootstrap.showErrors',
        'ngScrollbar',
        'platformUploadFileModule',
        'webClientTemplatecache'
    ];

    for (var i = 0; i < externalModules.length; i++) {
        if (testIfModuleExists(externalModules[i])) {
            platformAdminApplicationModules.push(externalModules[i]);
        }
    }

    angular
        .module('platformAdminApplication', platformAdminApplicationModules);

    function testIfModuleExists(moduleName) {
        try {
            angular.module(moduleName);
            console.log('Module ' + moduleName + ' exists');
            return true;
        } catch (e) {
            console.error(e);
            console.error('Module ' + moduleName + ' doesn\'t exist');
        }
        return false;
    }
})();