(function () {

    'use strict';

    angular
        .module('platformAdminApplication', [
            'platformNavigationModule',
            'platformPermissionModule',
            'platformRoleModule',
            'platformPageModule',
            'platformUserModule',
            'platformLayoutModule',
            'platformModuleModule',
            'platformModuleTypeModule',
            'multiVisualizationModule',
            'platformCommonModule',
            'platformListGridModule',
            'pascalprecht.translate',
            'ui.bootstrap',
            'ui.router',
            'ngAnimate',
            'ngTouch',
            'ngRoute',
            'toaster',
            'ui.grid',
            'ui.grid.exporter',
            'ui.grid.selection',
            'ui.grid.pinning',
            'ui.grid.resizeColumns',
            'ui.grid.moveColumns',
            'ui.grid.autoResize',
            'ui.bootstrap.showErrors'
        ]);
})();