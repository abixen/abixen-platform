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
        'FileData',
        'responseHandler'
    ];

    function FileDataSourceDetailController($scope, $http, $state, $stateParams, $log, FileDataSource, FileData, responseHandler) {
        $log.log('FileDataDetailController');
        var fileDataSourceDetails = this;

        new AbstractDetailsController(fileDataSourceDetails, FileDataSource, responseHandler, $scope,
            {
                entityId: $stateParams.id,
                getValidators: getValidators,
                onSuccessSaveForm: onSuccessSaveForm,
                onSuccessGetEntity: onSuccessGetEntity
            }
        );

        fileDataSourceDetails.fileData = [];
        fileDataSourceDetails.fileDatas = [];
        fileDataSourceDetails.fileColumns = [];
        fileDataSourceDetails.beforeSaveForm = beforeSaveForm;
        fileDataSourceDetails.getColumns = getColumns;

        FileData.query({}, function (data) {
            fileDataSourceDetails.fileDatas = data.content;
            $log.log('fileDataSourceDetails.fileDatas: ', fileDataSourceDetails.fileDatas);
            $log.log('fileDataSourceDetails.entity: ', fileDataSourceDetails.entity);
        });

        function beforeSaveForm() {
            $log.debug('entity.id: ',fileDataSourceDetails.entity);
            fileDataSourceDetails.entity.columns = [];
            fileDataSourceDetails.fileColumns.forEach(function (column) {
                if (column.selected === true){
                    fileDataSourceDetails.entity.columns.push({
                        id: column.id,
                        name: column.name,
                        position: column.position,
                        dataValueType: column.dataValueType
                    })
                }
            });
            fileDataSourceDetails.saveForm();
        }

        function getColumns(fileData) {
            if (!fileData) {
                return;
            }
            $log.debug('fileData: ', fileData);
            FileData.columns({id: fileData.id}, function (data) {
                $log.log('fileColumns data: ', data);
                fileDataSourceDetails.fileColumns = [];
                data.forEach(function (column, index) {
                    var selected = false;
                    if (fileDataSourceDetails.entity.columns != null && fileDataSourceDetails.entity.columns != undefined) {
                        fileDataSourceDetails.entity.columns.forEach(function (selectedColumn) {
                            if (selectedColumn.name.toUpperCase() === column.name.toUpperCase()) {
                                selected = true
                            }
                        })
                    }

                    column.selected = selected;
                    fileDataSourceDetails.fileColumns.push(column);
                })
            });
        }


        function onSuccessSaveForm() {
            $state.go('application.multiVisualisation.modules.fileDataSource.list');
        }

        function onSuccessGetEntity() {
            if (fileDataSourceDetails.entity.dataFile === null && fileDataSourceDetails.entity.dataFile === undefined){
                return;
            }
            getColumns(fileDataSourceDetails.entity.dataFile);
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

            validators['fileData'] =
                [
                    new NotNull()
                ];

            return validators;
        }
    }
})();