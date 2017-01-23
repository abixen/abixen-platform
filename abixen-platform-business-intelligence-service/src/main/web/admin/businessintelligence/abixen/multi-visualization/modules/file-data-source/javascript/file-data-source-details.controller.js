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
                onSuccessSaveForm: onSuccessSaveForm
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
        });

        function beforeSaveForm() {
            $log.debug('entity.id: ',fileDataSourceDetails.entity);
            fileDataSourceDetails.saveForm();
        }

        function getColumns(fileData) {
            $log.debug('fileData: ', fileData);
            fileDataSourceDetails.fileColumns = [];
            fileData.columns.forEach(function (column) {
                fileDataSourceDetails.fileColumns.push({
                    name: column.name,
                    selected:false
                })
            })
        }

        function onSuccessSaveForm() {
            $state.go('application.multiVisualization.modules.fileDataSource.list');
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