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
        'responseHandler',
        'toaster'
    ];

    function FileDataDetailController($scope, $http, $state, $stateParams, $log, FileData, responseHandler, toaster) {
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
        fileDataDetails.onFileUpload = onFileUpload;
        fileDataDetails.readFirstRowAsColumnName = true;

        $scope.$watch('fileDataDetails.fileData', function () {
            if (fileDataDetails.fileData !== undefined && fileDataDetails.fileData !== [] && fileDataDetails.fileData.length > 0) {
                transformDataForTable(fileDataDetails.fileData);
            }
        }, true);

        function beforeSaveForm() {
            fileDataDetails.entity.columns = [];
            if (fileDataDetails.fileData != null) {
                fileDataDetails.fileData.forEach(function (column, index) {
                    if (column !== undefined && column !== null && column !== '' && column !== '$$hashKey') {
                        $log.debug("column: ", column);
                        fileDataDetails.entity.columns.push({
                            name: column.name ? column.name : ("col"+index),
                            dataValueType: column.dataValueType,
                            position: column.position,
                            values: column.values
                        });
                    }
                });
            }
            $log.debug('entity.id: ',fileDataDetails.entity);
            fileDataDetails.saveForm();
        }

        function onSuccessSaveForm() {
            $state.go('application.multiVisualisation.modules.fileData.list');
        }

        function transformDataForTable(data) {
            var parsedData = [];
            data[0].values.forEach(function (row, index) {
                var parsedRow = [];
                data.forEach(function (column, index1) {
                    if (column.name){
                        parsedRow[column.name] = column.values[index].value;
                    } else {
                        parsedRow['col' + index1] = column.values[index].value;
                    }
                });
                parsedData.push(parsedRow);
            });
            $scope.$broadcast('GridDataUpdated', parsedData);
        }

        function onSuccessGetEntity() {
            if (fileDataDetails.entity.columns == null || fileDataDetails.entity.columns.length === 0 || fileDataDetails.entity.columns == undefined){
                return;
            }
            fileDataDetails.fileData = fileDataDetails.entity.columns;
        }

        function onFileUpload(file) {
            $log.debug("UploadFile", file);
            var fd = new FormData();
            fd.append('file', file);
            FileData.parse({readFirstColumnAsColumnName:fileDataDetails.readFirstRowAsColumnName}, fd, function (message) {
                if (message.data.length > 0) {
                    fileDataDetails.fileData = message.data;
                } else {
                    message.fileParseErrors.forEach(function (element){
                        toaster.pop('error', 'parsed', element.errorMsg);
                        fileDataDetails.fileData = [];
                        $scope.$broadcast('GridDataUpdated', []);
                    })
                }
            });
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