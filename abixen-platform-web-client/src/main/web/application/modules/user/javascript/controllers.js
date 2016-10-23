var userControllers = angular.module('userControllers', []);

userControllers.controller('UserDetailsController', ['$scope', '$http', '$state', '$stateParams', '$log', 'User', 'UserPassword', '$parse', '$uibModalInstance', 'userId', 'toaster',
    function ($scope, $http, $state, $stateParams, $log, User, UserPassword, $parse, $uibModalInstance, userId, toaster) {
        $log.log('UserDetailsController');

        $scope.user = {};
        $scope.userGender = ['MALE', 'FEMALE'];
        $scope.password = {
          currentPassword: null, newPassword: null, retypeNewPassword: null
        };
        $scope.formErrors = [];

        $scope.today = function() {
            $scope.user.birthday = new Date();
        };
        $scope.today();

        $scope.clear = function () {
            $scope.user.birthday = null;
        };

        $scope.open = function($event) {
            $scope.status.opened = true;
        };

        $scope.setDate = function(year, month, day) {
            $scope.user.birthday = new Date(year, month, day);
        };

        $scope.status = {
            opened: false
        };

        $scope.user.gender = null;

        $scope.cancel = function () {
            $uibModalInstance.dismiss();
        };

        $scope.setFormScope = function(scope){
            $scope.formScope = scope;
        };

        var getUser = function (id) {
            $log.log('selected user id:', id);
            if (id) {
                User.get({id: id}, function (data) {
                    $scope.user = data;
                    $log.log('User has been got: ', $scope.user);
                });
            } else {
                $scope.user = {};
            }
        };

        getUser(userId);

        var updateUser = function () {
            $scope.userForm = $scope.formScope.userForm;
            $scope.selectedForm = $scope.userForm;
            $log.log('$scope.userForm.$invalid: ' + $scope.userForm.$invalid);
            $log.log('$scope.userForm.$invalid: ', $scope.userForm);

            if($scope.userForm.$invalid){
                $log.log('Form is invalid and could not be saved.');
                $scope.$broadcast('show-errors-check-validity');
                return;
            }

            $log.log('save() - user: ', $scope.user);

            User.update({id: $scope.user.id}, $scope.user, function (data) {
                $scope.userForm.$setPristine();
                $log.log('data.form: ' , data);
                angular.forEach(data.form, function (rejectedValue, fieldName) {
                    $log.log('fieldName: ' + fieldName + ', ' + rejectedValue);
                    if(fieldName !== 'id'){
                        $scope.userForm[fieldName].$setValidity('serverMessage', true);
                    }
                });

                if (data.formErrors.length == 0) {
                    $log.log('Entity has been saved: ', $scope.user);
                    $uibModalInstance.dismiss();
                    toaster.pop(platformParameters.statusAlertTypes.SUCCESS, 'Updated', 'The account has been updated successfully.');
                    return;
                }

                for (var i = 0; i < data.formErrors.length; i++) {
                    var fieldName = data.formErrors[i].field;
                    $log.log('fieldName: ' + fieldName);
                    var message = data.formErrors[i].message;
                    var serverMessage = $parse('userForm.' + fieldName + '.$error.serverMessage');
                    $scope.userForm[fieldName].$setValidity('serverMessage', false);
                    serverMessage.assign($scope, message);
                }

                $scope.$broadcast('show-errors-check-validity');
            });
        };

        var changeUserPassword = function () {
            $scope.userChangePasswordForm = $scope.formScope.userChangePasswordForm;
            $scope.selectedForm = $scope.userChangePasswordForm;
            $log.log('$scope.userChangePasswordForm.$invalid: ' + $scope.userChangePasswordForm.$invalid);
            $log.log('$scope.userChangePasswordForm.$invalid: ', $scope.userChangePasswordForm);

            if($scope.userChangePasswordForm.$invalid){
                $log.log('Form is invalid and could not be saved.');
                $scope.$broadcast('show-errors-check-validity');
                return;
            }
            $log.log('changeUserPassword() - password:', $scope.password.currentPassword, $scope.password.newPassword, $scope.password.retypeNewPassword);

            UserPassword.update({id: $scope.user.id}, $scope.password, function (data) {
                $scope.userChangePasswordForm.$setPristine();
                $log.log('data.form: ' , data);
                angular.forEach(data.form, function (rejectedValue, fieldName) {
                    $log.log('fieldName: ' + fieldName + ', ' + rejectedValue);
                    if(fieldName !== 'id'){
                        $scope.userChangePasswordForm[fieldName].$setValidity('serverMessage', true);
                    }
                });

                if (data.formErrors.length == 0) {
                    $log.log('Entity has been saved: ', $scope.user);
                    $uibModalInstance.dismiss();
                    toaster.pop(platformParameters.statusAlertTypes.SUCCESS, 'Updated', 'The password has been changed successfully.');
                    return;
                }

                for (var i = 0; i < data.formErrors.length; i++) {
                    var fieldName = data.formErrors[i].field;
                    $log.log('fieldName: ' + fieldName);
                    var message = data.formErrors[i].message;
                    var serverMessage = $parse('userChangePasswordForm.' + fieldName + '.$error.serverMessage');
                    $scope.userChangePasswordForm[fieldName].$setValidity('serverMessage', false);
                    serverMessage.assign($scope, message);
                }

                $scope.$broadcast('show-errors-check-validity');
            })
        };

        $scope.saveForm = function () {
            if ($scope.user.id != null) {

                if(!$scope.selectedForm) {
                    $scope.selectedForm = $scope.formScope.userForm;
                }

                if ($scope.selectedForm.$name == 'userForm') {
                    updateUser();
                } else if ($scope.selectedForm.$name == 'userChangePasswordForm') {
                    changeUserPassword();
                } else if ($scope.selectedForm.$name == 'userRolesForm') {

                }
            }
        };

        $scope.selectForm = function (form) {
            $scope.selectedForm = form;
        }

    }]);