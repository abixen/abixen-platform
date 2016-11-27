var platformKpiChartModuleControllers = angular.module('platformKpiChartModuleControllers', []);

platformKpiChartModuleControllers.controller('KpiChartModuleInitController', ['$scope', '$http', '$log', 'KpiChartModuleInit', function ($scope, $http, $log, KpiChartModuleInit) {
    $log.log('KpiChartModuleInitController');

    $scope.moduleId = null;

    $scope.showConfigurationWizard = function () {
        $scope.subview = 'configuration';
    }

    $scope.showChart = function () {
        $scope.subview = 'chart';
    }

    $scope.$on(platformParameters.events.RELOAD_MODULE, function (event, id) {
        $log.log('RELOAD MODULE EVENT', event, id);

        $scope.moduleId = id;

        $scope.$emit(platformParameters.events.START_REQUEST);
        KpiChartModuleInit.get({id: id}, function (data) {
            $log.log('KpiChartModuleInit has been got: ', data);
            $scope.subview = 'chart';
            $scope.$emit(platformParameters.events.STOP_REQUEST);
        }, function (error) {
            $scope.$emit(platformParameters.events.STOP_REQUEST);
            if (error.status == 401) {
                $scope.$emit(platformParameters.events.MODULE_UNAUTHENTICATED);
            } else if (error.status == 403) {
                $scope.$emit(platformParameters.events.MODULE_FORBIDDEN);
            }
        });
    });

    $scope.$on('CONFIGURATION_MODE', function (event, id) {
        $log.log('CONFIGURATION_MODE EVENT', event, id)
        $scope.subview = 'configuration';
        $scope.$emit(platformParameters.events.CONFIGURATION_MODE_READY);
    });

    $scope.$on('VIEW_MODE', function (event, id) {
        $log.log('VIEW_MODE EVENT', event, id)
        $scope.subview = 'chart';
        $scope.$emit(platformParameters.events.VIEW_MODE_READY);
    });

    $scope.$emit(platformParameters.events.MODULE_READY);
}]);

platformKpiChartModuleControllers.controller('KpiChartModuleConfigurationController', ['$scope', '$log', 'KpiChartModuleConfiguration', function ($scope, $log, KpiChartModuleConfiguration) {
    $log.log('KpiChartModuleConfigurationController');

    $log.log('$scope.moduleId: ' + $scope.moduleId);

    angular.extend(this, new AbstractModuleApplicationCrudDetailController($scope, $log, KpiChartModuleConfiguration));

    $scope.colorCodes = ['DEFAULT', 'DANGER', 'WARNING', 'SUCCESS'];
    $scope.animationTypes = ['linearEase', 'easeInQuad', 'easeOutQuad', 'easeInOutQuad'
        , 'easeInCubic', 'easeOutCubic', 'easeInOutCubic', 'easeInQuart', 'easeOutQuart'
        , 'easeInOutQuart', 'easeInQuint', 'easeOutQuint', 'easeInOutQuint', 'easeInSine'
        , 'easeOutSine', 'easeInOutSine', 'easeInExpo', 'easeOutExpo', 'easeInOutExpo'
        , 'easeInCirc', 'easeOutCirc', 'easeInOutCirc', 'easeInElastic', 'easeOutElastic'
        , 'easeInOutElastic', 'easeInBack', 'easeOutBack', 'easeInOutBack', 'easeInBounce'
        , 'easeOutBounce', 'easeInOutBounce'];


    $scope.goToViewMode = function () {
        $scope.$emit('VIEW_MODE');
    }

    $scope.get($scope.moduleId);
}]);

platformKpiChartModuleControllers.controller('KpiChartModuleController', ['$scope', '$log', 'KpiChartModule', function ($scope, $log, KpiChartModule) {
    $log.log('KpiChartModuleController');

    $log.log('$scope.moduleId: ' + $scope.moduleId);

    $scope.getColor = function (colorCode) {
        $log.log('---------->'+colorCode);
        if(colorCode == 'DEFAULT'){
            return '#333333';
        }
        if(colorCode == 'DANGER'){
            return '#c6000a';
        }
        if(colorCode == 'WARNING'){
            return '#c97200';
        }
        if(colorCode == 'SUCCESS'){
            return '#4ab200';
        }
    }

    $scope.kpiChartModule = null;



    $scope.$emit(platformParameters.events.START_REQUEST);
    KpiChartModule.get({id: $scope.moduleId}, function (data) {
        $log.log('KpiChartModule has been got: ', data);
        $scope.kpiChartModule = data;
        $scope.$emit(platformParameters.events.STOP_REQUEST);
    }, function (error) {
        $scope.$emit(platformParameters.events.STOP_REQUEST);
        if (error.status == 401) {
            $scope.$emit(platformParameters.events.MODULE_UNAUTHENTICATED);
        } else if (error.status == 403) {
            $scope.$emit(platformParameters.events.MODULE_FORBIDDEN);
        }
    });

}]);
