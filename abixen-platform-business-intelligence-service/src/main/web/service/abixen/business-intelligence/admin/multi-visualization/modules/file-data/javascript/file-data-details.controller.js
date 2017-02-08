(function () {

    'use strict';

    angular
        .module('platformFileDataModule')
        .controller('FileDataDetailController', FileDataDetailController);

    FileDataDetailController.$inject = [
        '$scope',
        '$http',
        '$state',
        '$stateParams',
        '$log',
        'FileData',
        'responseHandler'
    ];

    function FileDataDetailController($scope, $http, $state, $stateParams, $log, FileData, responseHandler) {
        $log.log('FileDataDetailController');
        var fileDataDetails = this;

        new AbstractDetailsController(fileDataDetails, FileData, responseHandler, $scope,
            {
                entityId: $stateParams.id,
                getValidators: getValidators,
                onSuccessSaveForm: onSuccessSaveForm,
                onSuccessGetEntity: onSuccessGetEntity
            }
        );

        fileDataDetails.fileData = [];
        fileDataDetails.beforeSaveForm = beforeSaveForm;

        $scope.$watch('fileDataDetails.fileData', function () {
            if (fileDataDetails.fileData !== undefined && fileDataDetails.fileData !== [] && fileDataDetails.fileData.length > 0) {
                $scope.$broadcast('GridDataUpdated', fileDataDetails.fileData);
            }
        }, true);

        function beforeSaveForm() {
            fileDataDetails.entity.columns = [];
            if (fileDataDetails.fileData[0] != null) {
                Object.keys(fileDataDetails.fileData[0]).forEach(function (column) {
                    if (column !== undefined && column !== null && column !== '' && column !== '$$hashKey') {
                        var values = [];
                        fileDataDetails.fileData.forEach(function (row) {
                            values.push({value: row[column]})
                        });
                        fileDataDetails.entity.columns.push({
                            name: column,
                            values: values
                        });
                    }
                });
            }
            $log.debug('entity.id: ',fileDataDetails.entity);
            fileDataDetails.saveForm();
        }

        function onSuccessSaveForm() {
            $state.go('application.multiVisualization.modules.fileData.list');
        }

        function onSuccessGetEntity() {
            if (fileDataDetails.entity.columns == null || fileDataDetails.entity.columns.length === 0 || fileDataDetails.entity.columns == undefined){
                return;
            }
            var parsedData = [];

            fileDataDetails.entity.columns[0].values.forEach(function (row, index) {
                var parsedRow = [];
                fileDataDetails.entity.columns.forEach(function (column, index1) {
                    parsedRow['col' + index1] = column.values[index].value;
                });
                parsedData.push(parsedRow);
            });
            fileDataDetails.fileData = parsedData;
            $scope.$broadcast('GridDataUpdated', parsedData);

        }

        function getValidators() {
            var validators = [];

            validators['name'] =
                [
                    new NotNull(),
                    new Length(0, 60)
                ];

            validators['description'] =
                [
                    new Length(0, 1000)
                ];
            return validators;
        }
    }
})();