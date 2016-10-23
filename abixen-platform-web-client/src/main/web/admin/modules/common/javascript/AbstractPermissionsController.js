function AbstractPermissionsController($scope, $stateParams, $log, $state, AclRolesPermissions, permissionAclClassCategoryName, objectId, stateParent) {
    $log.log('AbstractPermissionsController');

    $scope.aclRolesPermissionsDto;

    $scope.get = function () {
        $log.log('get() - permissionAclClassCategoryName: ', permissionAclClassCategoryName, ', objectId: ', objectId);
        AclRolesPermissions.get({
            objectId: objectId,
            permissionAclClassCategoryName: permissionAclClassCategoryName
        }, function (data) {
            $scope.aclRolesPermissionsDto = data;
            $log.log('AclRolesPermissions has been got: ', $scope.aclRolesPermissionsDto);
        });
    };

    $scope.saveForm = function () {
        $log.log('saveForm() - permissionAclClassCategoryName: ', permissionAclClassCategoryName, ', objectId: ', objectId);
        AclRolesPermissions.update({
            objectId: objectId,
            permissionAclClassCategoryName: permissionAclClassCategoryName
        }, $scope.aclRolesPermissionsDto, function (data) {
            //$scope.aclRóolesPermissionsDto = data;
            $log.log('AclRolesPermissions has been updated: ', $scope.aclRolesPermissionsDto);
            $scope.goBack();
        });
    };

    $scope.goBack = function(){
        $state.go(stateParent + '.list')
    }

}