var commonModalWindowControllers = angular.module('commonModalWindowControllers', []);

commonModalWindowControllers.controller('ModalWindowController', ['$scope', '$uibModalInstance', 'acceptFunction', 'okButtonClass', 'cancelButtonClass', 'windowClass', 'message', 'title', function ($scope, $uibModalInstance, acceptFunction, okButtonClass, cancelButtonClass, windowClass, message, title) {

    $scope.okButtonClass = okButtonClass;
    $scope.cancelButtonClass = cancelButtonClass;
    $scope.windowClass = windowClass;
    $scope.message = message;
    $scope.title = title;

    $scope.discard = function () {
        $uibModalInstance.dismiss();
    };

    $scope.accept = function () {
        $uibModalInstance.dismiss();
        acceptFunction();
    };
}]);