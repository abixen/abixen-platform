var layoutControllers = angular.module('layoutControllers', ['ui.codemirror','angularFileUpload','ngCookies']);

layoutControllers.controller('LayoutListController', ['$scope', '$http', '$log', 'uiGridConstants', 'Layout', 'gridFilter', 'applicationNavigationItems', '$state', function ($scope, $http, $log, uiGridConstants, Layout, gridFilter, applicationNavigationItems, $state) {
    $log.log('LayoutListController');

    angular.extend(this, new AbstractCrudListController($scope, $http, $log, uiGridConstants, Layout, gridFilter));

    $scope.entityListGrid.columnDefs = [
        {field: 'id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
        {field: 'title', pinnedLeft: true, width: 200},
        {field: 'createdBy.layoutname', name: 'Created By', width: 200},
        {field: 'createdDate', width: 200, cellFilter: "date:'" + platformParameters.formats.DATE_TIME_FORMAT + "'"},
        {field: 'lastModifiedBy.layoutname', name: 'Last Modified By', width: 200},
        {
            field: 'lastModifiedDate',
            width: 200,
            cellFilter: "date:'" + platformParameters.formats.DATE_TIME_FORMAT + "'"
        }
    ];

    $scope.query = {
        and: [
            {
                name: 'title',
                operation: '=',
                value: 'Layout View'
            }

        ]
    };


    $scope.filterCriteria = {
        layout: 0,
        size: 20,
        sort: 'id,asc',
        gridFilterParameters: []
    };

    var newLayoutButton = {
        id: 1,
        styleClass: 'btn add-new-object-button',
        title: 'New Layout',
        faIcon: 'fa fa-plus',
        onClick: function () {
            $state.go('application.layouts.add');
        },
        visible: true,
        disabled: false
    };

    applicationNavigationItems.setTopbarItem(newLayoutButton);

    $scope.read();
}]);

layoutControllers.controller('LayoutDetailController', ['$scope', '$http', '$state', '$stateParams', '$log', 'Layout','FileUploader','$cookies', function ($scope, $http, $state, $stateParams, $log, Layout,FileUploader,$cookies) {
    $log.log('LayoutDetailController');
    $scope.isUploadIcon = false;
    angular.extend(this, new AbstractCrudDetailController($scope, $http, $state, $stateParams, $log, Layout, 'application.layouts'));
    $scope.editorOptions = {
        lineWrapping: true,
        lineNumbers: true,
        readOnly: 'nocursor',
        mode: 'xml',
        theme: 'theme'
    };
    $scope.editor = CodeMirror.fromTextArea(document.getElementById("xmlInput"), {
        lineNumbers: true,
        mode: "text/html",
        matchBrackets: true
    });
    $scope.get($stateParams.id);

    $scope.$watch('entity', function () {

        if ($scope.entity != null) {
            $scope.editor.getDoc().setValue($scope.entity.content);
            if ($scope.entity.iconFileName === null) {
                entity.iconFileName = 'default-layout-icon.png';
            }
        }
    });

    $scope.isUploadLayoutIcon = function () {
        return $state.current.name === 'application.layouts.edit'
    };

    var layoutIconChange = $scope.layoutIconChange = this;
    layoutIconChange.userBaseUrl = '/api/admin/layouts/';
    layoutIconChange.xsrfToken = $cookies.get($http.defaults.xsrfCookieName);

    new AbstractUploaderController(layoutIconChange, FileUploader, layoutIconChange.xsrfToken,
        {
            uploaderUrl: layoutIconChange.userBaseUrl + $stateParams.id + '/icon',
            uploaderAlias: 'iconFile',
            uploaderMethod: 'POST',
            uploaderFunctionConfig: function (controller) {
                controller.onCompleteAll = onCompleteAll;
            }
        }
    );
    layoutIconChange.cancelAll = cancelAll;

    function onCompleteAll() {
        if ($scope.isUploadIcon) {
            $scope.isUploadIcon = false;
            layoutIconChange.uploader.clearQueue();
            $scope.get($stateParams.id);
        }
    };

    function cancelAll() {
        layoutIconChange.uploader.cancelAll();
        $scope.isUploadIcon = false;
    };

    $scope.showUploadLayoutIcon = function () {
        $scope.isUploadIcon = true;
    };

}]);

layoutControllers.controller('LayoutPermissionsController', ['$scope', '$stateParams', '$log', '$state', 'AclRolesPermissions', function ($scope, $stateParams, $log, $state, AclRolesPermissions) {
    $log.log('LayoutPermissionsController');

    angular.extend(this, new AbstractPermissionsController($scope, $stateParams, $log, $state, AclRolesPermissions, 'layout', $stateParams.id, 'application.layouts'));

    $scope.get();
}]);
