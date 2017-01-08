(function () {

    'use strict';

    angular
        .module('platformFileDataSourceModule')
        .controller('FileDataSourceDetailController', FileDataSourceDetailController);

    FileDataSourceDetailController.$inject = [
        '$scope',
        '$http',
        '$state',
        '$stateParams',
        '$log',
        'FileDataSource',
        'responseHandler'
    ];

    function FileDataSourceDetailController($scope, $http, $state, $stateParams, $log, FileDataSource, responseHandler) {
        $log.log('FileDataSourceDetailController');
        var fileDataSourceDetails = this;

        new AbstractDetailsController(fileDataSourceDetails, FileDataSource, responseHandler, $scope,
            {
                entityId: $stateParams.id,
                getValidators: getValidators,
                onSuccessSaveForm: onSuccessSaveForm
            }
        );

        fileDataSourceDetails.fileData = [];
        fileDataSourceDetails.beforeSaveForm = beforeSaveForm;

        $scope.$watch('fileDataSourceDetails.fileData', function () {
            if (fileDataSourceDetails.fileData !== undefined && fileDataSourceDetails.fileData !== [] && fileDataSourceDetails.fileData.length > 0) {
                $scope.$broadcast('GridDataUpdated', fileDataSourceDetails.fileData);
            }
        }, true);

        function beforeSaveForm() {


            fileDataSourceDetails.saveForm();
        }

        function onSuccessSaveForm() {
            $state.go('application.multiVisualization.modules.fileDataSource.list');
        }

        function getValidators() {
            var validators = [];

            validators['name'] =
                [
                    new NotNull(),
                    new Length(6, 40)
                ];

            validators['description'] =
                [
                    new Length(0, 40)
                ];
            return validators;
        }
    }
})();