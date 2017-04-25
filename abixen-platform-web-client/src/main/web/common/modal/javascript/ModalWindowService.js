var commonModalWindowServices = angular.module('commonModalWindowServices', ['webClientTemplatecache']);

commonModalWindowServices.provider('modalWindow',
    function ModalWindow() {
        var okButtonClass = '';
        var cancelButtonClass = '';
        var warningWindowClass = '';

        return ({
            getOkButtonClass: getOkButtonClass,
            setOkButtonClass: setOkButtonClass,
            getCancelButtonClass: getCancelButtonClass,
            setCancelButtonClass: setCancelButtonClass,
            getWarningWindowClass: getWarningWindowClass,
            setWarningWindowClass: setWarningWindowClass,

            $get: ['$uibModal', instantiateModalWindow]
        });

        function getOkButtonClass() {
            return okButtonClass;
        }

        function setOkButtonClass(newOkButtonClass) {
            okButtonClass = newOkButtonClass;
        }

        function getCancelButtonClass() {
            return cancelButtonClass;
        }

        function setCancelButtonClass(newCancelButtonClass) {
            cancelButtonClass = newCancelButtonClass;
        }

        function getWarningWindowClass() {
            return warningWindowClass;
        }

        function setWarningWindowClass(newWarningWindowClass) {
            warningWindowClass = newWarningWindowClass;
        }

        function instantiateModalWindow($uibModal) {
            return ({
                openConfirmWindow: openConfirmWindow
            });

            function openConfirmWindow(title, message, windowType, callback) {
                $uibModal.open({
                    animation: true,
                    templateUrl: '/common/modal/html/confirm-window.html',
                    controller: 'ModalWindowController',
                    resolve: {
                        acceptFunction: function () {
                            return callback;
                        },
                        okButtonClass: function () {
                            return getOkButtonClass();
                        },
                        cancelButtonClass: function () {
                            return getCancelButtonClass();
                        },
                        windowClass: function () {
                            return getWindowClass(windowType);
                        },
                        title: function () {
                            return title;
                        },
                        message: function () {
                            return message;
                        }
                    }
                });
            }

            function getWindowClass(windowType) {
                switch (windowType) {
                    case 'warning':
                        return getWarningWindowClass();
                    default:
                        return ''
                }
            }

        }
    }
);