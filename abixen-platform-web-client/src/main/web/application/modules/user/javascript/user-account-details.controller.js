(function () {

    'use strict';

    angular
        .module('platformUserModule')
        .controller('UserAccountDetailsController', UserAccountDetailsController);

    UserAccountDetailsController.$inject = [
        '$scope',
        '$http',
        '$cookies',
        '$log',
        'User',
        'UserPassword',
        '$uibModalInstance',
        'platformSecurity',
        'toaster',
        'FileUploader',
        'responseHandler'
    ];

    function UserAccountDetailsController($scope, $http, $cookies, $log, User, UserPassword, $uibModalInstance, platformSecurity, toaster, FileUploader, responseHandler) {
        $log.log('UserAccountDetailsController');

        var userId = platformSecurity.getPlatformUser().id;
        var userBaseUrl = '/api/application/users/';
        var userAccountDetails = this;

        userAccountDetails.userDetails = {};
        userAccountDetails.changePassword = {};

        new AbstractDetailsController(userAccountDetails.userDetails, User, responseHandler, $scope,
            {
                entityId: userId,
                getValidators: getUserDetailsValidators,
                onSuccessSaveForm: onUserDetailsSuccessSaveForm,
                onSuccessGetEntity: onSuccessGetEntity
            }
        );

        new AbstractDetailsController(userAccountDetails.changePassword, UserPassword, responseHandler, $scope,
            {
                initEntity: {
                    id: userId
                },
                getValidators: getChangePasswordValidators,
                onSuccessSaveForm: onChangePasswordSuccessSaveForm
            }
        );

        userAccountDetails.userDetails.uploader = creteUploder();
        userAccountDetails.userDetails.avatarUrl = '';
        userAccountDetails.userDetails.isUploadAvatar = false;
        userAccountDetails.userDetails.genderTypes = [{key: 'MALE'}, {key: 'FEMALE'}];
        userAccountDetails.userDetails.hideUploadContent = hideUploadContent;
        userAccountDetails.userDetails.showUploadContent = showUploadContent;
        userAccountDetails.cancel = cancel;

        userAccountDetails.selectUserDetails = selectUserDetails;
        userAccountDetails.selectChangePassword = selectChangePassword;
        userAccountDetails.selectRoles = selectRoles;
        userAccountDetails.saveForm = saveForm;
        selectUserDetails();


        function saveForm(){
            switch (userAccountDetails.selectedTab){
                case 'userDetails':
                    userAccountDetails.userDetails.saveForm();
                    break;
                case 'changePassword':
                    userAccountDetails.changePassword.saveForm();
                    break;
                default:
                    throw new Error('Unsupported a saveForm() operation.');
            }
        }

        function onSuccessGetEntity() {
            userAccountDetails.userDetails.avatarUrl = userBaseUrl + userId + '/avatar/' + userAccountDetails.userDetails.entity.avatarFileName;
        }

        function selectUserDetails() {
            userAccountDetails.selectedTab = 'userDetails';
        }

        function selectChangePassword() {
            userAccountDetails.selectedTab = 'changePassword';
        }

        function selectRoles() {
            userAccountDetails.selectedTab = 'roles';
        }

        function creteUploder() {
            var uploader = new FileUploader({
                url: userBaseUrl + userId + '/avatar',
                method: 'POST',
                alias: 'avatarFile',
                queueLimit: 1,
                headers: {
                    'X-XSRF-TOKEN': $cookies.get($http.defaults.xsrfCookieName)
                }
            });

            uploader.onAfterAddingAll = function () {
                if (uploader.getNotUploadedItems().length > 1) {
                    uploader.removeFromQueue(0);
                }
            };

            uploader.filters.push({
                name: 'imageFilter',
                fn: function (item /*{File|FileLikeObject}*/, options) {
                    var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
                    return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
                }
            });

            uploader.onAfterAddingFile = function (fileItem) {
            };

            uploader.onCompleteItem = function (fileItem, response, status, headers) {
                if (response.id != undefined) {
                    userAccountDetails.userDetails.avatarUrl = userBaseUrl + userId + '/avatar/' + response.avatarFileName;
                }
            };

            uploader.onCompleteAll = function () {
                if (userAccountDetails.userDetails.isUploadAvatar) {
                    userAccountDetails.userDetails.isUploadAvatar = false;
                    uploader.clearQueue()
                }
            };

            return uploader;
        }

        function hideUploadContent() {
            userAccountDetails.userDetails.uploader.clearQueue();
            userAccountDetails.userDetails.isUploadAvatar = false;
        }

        function showUploadContent() {
            userAccountDetails.userDetails.isUploadAvatar = true;
        }

        function cancel() {
            $uibModalInstance.dismiss();
        }

        function onUserDetailsSuccessSaveForm() {
            platformSecurity.reloadPlatformUser();
            $uibModalInstance.dismiss();
            toaster.pop(platformParameters.statusAlertTypes.SUCCESS, 'Updated', 'The account has been updated successfully.');
        }

        function getUserDetailsValidators() {
            var validators = [];

            validators['screenName'] =
                [
                    new NotNull(),
                    new Length(3, 32)
                ];

            validators['username'] =
                [
                    new NotNull(),
                    new Length(3, 32),
                    new Email()
                ];

            validators['firstName'] =
                [
                    new NotNull(),
                    new Length(2, 64)
                ];

            validators['middleName'] =
                [
                    new Length(2, 64)
                ];

            validators['lastName'] =
                [
                    new NotNull(),
                    new Length(2, 64)
                ];

            validators['jobTitle'] =
                [
                    new Length(2, 64)
                ];
            validators['selectedLanguage'] =
                [
                    new NotNull()
                ];

            return validators;
        }

        function getChangePasswordValidators() {
            var validators = [];

            validators['currentPassword'] =
                [
                    new NotNull(),
                    new Length(2, 64)
                ];

            validators['newPassword'] =
                [
                    new NotNull(),
                    new Length(2, 64)
                ];

            validators['retypeNewPassword'] =
                [
                    new NotNull(),
                    new Length(2, 64),
                    new ConfirmField('newPassword', 'module.user.validator.invalid.passwordMatch')
                ];

            return validators;
        }

        function onChangePasswordSuccessSaveForm() {
            $uibModalInstance.dismiss();
            toaster.pop(platformParameters.statusAlertTypes.SUCCESS, 'Updated', 'The account has been updated successfully.');
        }
    }
})();