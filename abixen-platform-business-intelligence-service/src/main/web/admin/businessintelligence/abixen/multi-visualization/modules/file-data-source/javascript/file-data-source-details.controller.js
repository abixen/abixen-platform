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
                onSuccessSaveForm: onSuccessSaveForm,
                onSuccessGetEntity: onSuccessGetEntity
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
            fileDataSourceDetails.entity.columns = [];
            Object.keys(fileDataSourceDetails.fileData[0]).forEach(function (column) {
                if (column !== undefined && column !== null && column !== '' && column !== '$$hashKey') {
                    var values = [];
                    fileDataSourceDetails.fileData.forEach(function (row) {
                            values.push({ value: row[column]})
                    });
                    fileDataSourceDetails.entity.columns.push({
                        name: column,
                        values : values});
                }
            });
            $log.debug('entity.id: ',fileDataSourceDetails. entity);
            fileDataSourceDetails.saveForm();
        }

        function onSuccessSaveForm() {
            $state.go('application.multiVisualization.modules.fileDataSource.list');
        }

        function onSuccessGetEntity() {
            if (fileDataSourceDetails.entity.columns == null && fileDataSourceDetails.entity.columns == undefined){
                return;
            }
            var parsedData = [];

            fileDataSourceDetails.entity.columns[0].values.forEach(function (row, index) {
                var parsedRow = [];
                fileDataSourceDetails.entity.columns.forEach(function (column, index1) {
                    parsedRow['col' + index1] = column.values[index].value;
                });
                parsedData.push(parsedRow);
            });
            fileDataSourceDetails.fileData = parsedData;
            $scope.$broadcast('GridDataUpdated', parsedData);

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