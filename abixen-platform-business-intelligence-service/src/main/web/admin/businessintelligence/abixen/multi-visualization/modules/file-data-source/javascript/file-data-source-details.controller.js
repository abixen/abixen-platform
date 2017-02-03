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
                        position: column.position
                    })
                }
            });
            fileDataSourceDetails.saveForm();
        }

        function getColumns(fileData) {
            $log.debug('fileData: ', fileData);
            fileDataSourceDetails.fileColumns = [];
            if (fileData && fileData.columns) {
                fileData.columns.forEach(function (column, index) {
                    var selected = false;
                    if (fileDataSourceDetails.entity.columns != null && fileDataSourceDetails.entity.columns != undefined) {
                        fileDataSourceDetails.entity.columns.forEach(function (selectedColumn) {
                            if (selectedColumn.name.toUpperCase() === column.name.toUpperCase()) {
                                selected = true
                            }
                        })
                    }

                    fileDataSourceDetails.fileColumns.push({
                        name: column.name,
                        position: index,
                        selected: selected
                    })
                })
            }
        }

        function onSuccessSaveForm() {
            $state.go('application.multiVisualization.modules.fileDataSource.list');
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
            return validators;
        }
    }
})();