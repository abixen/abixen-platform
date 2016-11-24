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
        $log.log('CONFIGURATION_MODE 666666 EVENT', event, id)
        $scope.subview = 'configuration';

    });

    $scope.$on('VIEW_MODE', function (event, id) {
        $log.log('VIEW_MODE EVENT', event, id)
        $scope.subview = 'chart';

    });

    $scope.$emit(platformParameters.events.MODULE_READY);
}]);

platformMagicNumberModuleControllers.controller('MagicNumberModuleConfigurationController', ['$scope', '$log', 'MagicNumberModuleConfiguration', function ($scope, $log, MagicNumberModuleConfiguration) {
    $log.log('MagicNumberModuleConfigurationController');

    $log.log('$scope.moduleId: ' + $scope.moduleId);

    angular.extend(this, new AbstractModuleApplicationCrudDetailController($scope, $log, MagicNumberModuleConfiguration));

    $scope.colorCodes = ['DEFAULT', 'DANGER', 'WARNING', 'SUCCESS'];

    $scope.goToViewMode = function () {
        $scope.$emit('VIEW_MODE');
    }

    var $icon = $('#iconClassInput');

    debugger;
    var icons = ["fa-glass", "fa-music",
        "fa-search", "fa-envelope-o", "fa-heart", "fa-star", "fa-star-o", "fa-user", "fa-film", "fa-th-large", "fa-th", "fa-th-list", "fa-check", "fa-remove", "fa-close",
        "fa-times", "fa-search-plus", "fa-search-minus", "fa-power-off", "fa-signal", "fa-gear", "fa-cog", "fa-trash-o", "fa-home", "fa-file-o", "fa-clock-o", "fa-road",
        "fa-download", "fa-arrow-circle-o-down", "fa-arrow-circle-o-up", "fa-inbox", "fa-play-circle-o", "fa-rotate-right", "fa-repeat", "fa-refresh", "fa-list-alt",
        "fa-lock", "fa-flag", "fa-headphones", "fa-volume-off", "fa-volume-down", "fa-volume-up", "fa-qrcode", "fa-barcode", "fa-tag", "fa-tags", "fa-book", "fa-bookmark",
        "fa-print", "fa-camera", "fa-font", "fa-bold", "fa-italic", "fa-text-height", "fa-text-width", "fa-align-left", "fa-align-center", "fa-align-right", "fa-align-justify"];

    $icon.autocomplete({
        minLength: 0,
        source: icons,
        focus: function (event, ui) {
            $icon.val(ui.item.value);
            return false;
        }
    });

    $icon.data("ui-autocomplete")._renderItem = function (ul, item) {

        var $li = $('<li>'), $span = $('<span>');
        var display = "fa " + item.value;
        $span.attr('class', display);

        $li.attr('data-value', display);
        $li.append('<a href="#">');
        $li.find('a').append($span);//.append(item.label);

        return $li.appendTo(ul);
    };


    $scope.get($scope.moduleId);
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
