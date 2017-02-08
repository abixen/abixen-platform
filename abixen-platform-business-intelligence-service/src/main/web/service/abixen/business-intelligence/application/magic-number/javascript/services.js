var platformMagicNumberModuleServices = angular.module('platformMagicNumberModuleServices', ['ngResource']);

platformMagicNumberModuleServices.factory('MagicNumberModuleInit', ['$resource',
    function ($resource) {
        return $resource('/service/abixen/business-intelligence/application/magic-number/init/:id', {}, {
        });
    }]);

platformMagicNumberModuleServices.factory('MagicNumberModule', ['$resource',
    function ($resource) {
        return $resource('/service/abixen/business-intelligence/application/magic-number/:id', {}, {
        });
    }]);

platformMagicNumberModuleServices.factory('MagicNumberModuleConfiguration', ['$resource',
    function ($resource) {
        return $resource('/service/abixen/business-intelligence/application/magic-number/configuration/:id', {}, {
            update: {method: 'PUT'}
        });
    }]);