(function () {

    'use strict';

    angular
        .module('platformAdminApplication')
        .factory('platformHttpInterceptor', platformAdminApplicationHttpInterceptor);

    platformAdminApplicationHttpInterceptor.$inject = ['$q', '$injector'];

    function platformAdminApplicationHttpInterceptor($q, $injector) {

        return {
            responseError: onResponseError
        };

        function onResponseError(rejection) {

            console.log('rejection: ', rejection);

            var applicationLoginUrl = 'http://localhost:8080/login';
            var applicationModulesUrlPrefix = '/application/modules';

            //TODO

            if (rejection.data && rejection.data.path && rejection.data.path.indexOf(applicationModulesUrlPrefix) == 0 && rejection.status != 401 && rejection.status != 403 && rejection.status != 500) {
                return $q.reject(rejection);
            }

            if (rejection.status == 401) {
                window.location = applicationLoginUrl;
            } else if (rejection.status == 403) {
                var toaster = $injector.get('toaster');
                toaster.pop(platformParameters.statusAlertTypes.ERROR, rejection.data.error, rejection.data.message);
            } else if (rejection.status == 500) {
                var toaster = $injector.get('toaster');
                toaster.pop(platformParameters.statusAlertTypes.ERROR, rejection.data.error, rejection.data.message);
            }

            return $q.reject(rejection);
        }
    }
})();