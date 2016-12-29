var platformMagicNumberModuleControllers = angular.module('platformMagicNumberModuleControllers', []);

platformMagicNumberModuleControllers.controller('MagicNumberModuleInitController', ['$scope', '$http', '$log', 'MagicNumberModuleInit', function ($scope, $http, $log, MagicNumberModuleInit) {
    $log.log('MagicNumberModuleInitController');

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
        MagicNumberModuleInit.get({id: id}, function (data) {
            $log.log('MagicNumberModuleInit has been got: ', data);
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

platformMagicNumberModuleControllers.controller('MagicNumberModuleConfigurationController', ['$scope', '$log','$uibModal','FaSelectionModalWindowServices','MagicNumberModuleConfiguration', function ($scope, $log, $uibModal,FaSelectionModalWindowServices,MagicNumberModuleConfiguration) {
    $log.log('MagicNumberModuleConfigurationController');
    
    $log.log('$scope.moduleId: ' + $scope.moduleId);

    angular.extend(this, new AbstractModuleApplicationCrudDetailController($scope, $log, MagicNumberModuleConfiguration));

    $scope.colorCodes = ['DEFAULT', 'DANGER', 'WARNING', 'SUCCESS'];
    
    $scope.goToViewMode = function () {
        $scope.$emit('VIEW_MODE');
    }	
    $scope.get($scope.moduleId);
    
    //  We create a selectedIcon object so that we can pass it to the modal.
    //  This will then be modified on the modal and we can assign the selected value
    //  to the entity.iconClass.
    $scope.selectedIcon;
    $scope.iconClassModal = function() {
        $scope.selectedIcon = new Array();  // object
    	FaSelectionModalWindowServices.openSelectionDialog('Select Icon',$scope.selectedIcon,platformParameters.modalSelectionType.SINGLE,'app-modal-window',
        function() {
            $scope.entity.iconClass = $scope.selectedIcon[0];
        });
    }
}]);

platformMagicNumberModuleControllers.controller('MagicNumberModuleController', ['$scope', '$log', 'MagicNumberModule', function ($scope, $log, MagicNumberModule) {
    $log.log('MagicNumberModuleController');

    $log.log('$scope.moduleId: ' + $scope.moduleId);

    $scope.magicNumberModule = null;

    $scope.$emit(platformParameters.events.START_REQUEST);
    MagicNumberModule.get({id: $scope.moduleId}, function (data) {
        $log.log('MagicNumberModuleController has been got: ', data);
        $scope.magicNumberModule = data;
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
