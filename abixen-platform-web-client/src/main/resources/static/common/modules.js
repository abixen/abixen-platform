var commonModalWindowControllers = angular.module('commonModalWindowControllers', []);

commonModalWindowControllers.controller('ModalWindowController', ['$scope', '$uibModalInstance', 'acceptFunction', 'okButtonClass', 'cancelButtonClass', 'windowClass', 'message', 'title', function ($scope, $uibModalInstance, acceptFunction, okButtonClass, cancelButtonClass, windowClass, message, title) {

    $scope.okButtonClass = okButtonClass;
    $scope.cancelButtonClass = cancelButtonClass;
    $scope.windowClass = windowClass;
    $scope.message = message;
    $scope.title = title;

    $scope.discard = function () {
        $uibModalInstance.dismiss();
    };

    $scope.accept = function () {
        $uibModalInstance.dismiss();
        acceptFunction();
    };
}]);;var commonModalWindowServices = angular.module('commonModalWindowServices', []);

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
);;var platformModalModule = angular.module('platformModalModule', ['commonModalWindowControllers', 'commonModalWindowServices']);


;var platformNavigationDirectives = angular.module('platformNavigationDirectives', []);

platformNavigationDirectives.directive('platformNavigation', ['$log', '$state', 'applicationNavigationItems', function ($log, $state, applicationNavigationItems) {
    return {
        restrict: 'E',
        transclude: true,
        templateUrl: '/common/navigation/html/platform-navigation.html',
        scope: {
            addNewModule: '&addNewModule',
            addNewPage: '&addNewPage',
            logout: '&logout',
            platformUser: '=platformUser',
            selectedItem: '=selectedItem',
            showDropdown: '=showDropdown',
            editUser: '&editUser'
        },
        link: function (scope, element, attrs) {

            scope.applicationNavigationItems = applicationNavigationItems;
            scope.sidebarItems = applicationNavigationItems.sidebarItems;
            scope.topbarItems = applicationNavigationItems.topbarItems;
            scope.topbarDropdownItems = applicationNavigationItems.topbarDropdownItems;
            scope.redirectAction = applicationNavigationItems.getRedirectAction();
            scope.dropdownStyleClass = applicationNavigationItems.getDropdownStyleClass();
            scope.toggle = true;
            var mobileView = 992;

            scope.getWidth = function() {
                return window.innerWidth;
            };

            scope.$watch(scope.getWidth, function(newValue, oldValue) {
                $log.log('scope.toggle: ' + scope.toggle);
                if (newValue >= mobileView) {
                    scope.toggle = true;
                } else {
                    scope.toggle = false;
                }

            });

            if(!scope.selectedItem && !$state.params.id) {
                scope.selectedItem = 0;
            }

            scope.changeState = function (sidebarItem) {
                scope.selectedItem = sidebarItem.id;
                if (sidebarItem.isPage) {
                    $state.go(sidebarItem.state, {id: sidebarItem.id});
                } else {
                    $state.go(sidebarItem.state);
                }

            };
            //scope.$watch('applicationNavigationItems.getTopbarItems()', function(newValue, oldValue) {
             //   $log.log('applicationNavigationItems.topbarItems: ', applicationNavigationItems.getTopbarItems());
            //    scope.topbarItems = applicationNavigationItems.getTopbarItems();
            //});

            scope.toggleSidebar = function() {
                scope.toggle = !scope.toggle;
            };

            window.onresize = function() {
                scope.$apply();
            };

            scope.$on(platformParameters.events.SIDEBAR_ELEMENT_SELECTED, function (event, id) {
                scope.selectedItem = id;
            });

        }
    };
}]);;var platformNavigationModule = angular.module('platformNavigationModule', ['platformNavigationDirectives', 'platformNavigationServices']);


;var platformNavigationServices = angular.module('platformNavigationServices', []);

platformNavigationServices.provider("applicationNavigationItems", function () {
    var sidebarItems = [];
    var topbarItems = [];
    var topbarDropdownItems = [];
    var redirectAction = {};
    var dropdownStyleClass = 'btn-group';
    return {
        addSidebarItem: function (page) {
            sidebarItems.push(page);
            return this;
        },
        editSidebarItem: function (id, title) {
            for (var i = 0; i < sidebarItems.length; i++) {
                if (id == sidebarItems[i].id) {
                    sidebarItems[i].title = title;
                }
            }
            return this;
        },
        getSidebarItems: function () {
            return sidebarItems;
        },
        addTopbarItem: function (button) {
            topbarItems.push(button);
            return this;
        },
        setTopbarItem: function (button) {
            topbarItems.length = 0;
            topbarItems.push(button);
            return this;
        },
        clearTopbarItems: function () {
            topbarItems.length = 0;
            return this;
        },
        getTopbarItems: function (button) {
            return topbarItems;
        },
        addTopbarDropdownItem: function (topbarDropdownItem) {
            topbarDropdownItems.push(topbarDropdownItem);
            return this;
        },
        setRedirectAction: function (redirectActionItem) {
            redirectAction = redirectActionItem;
            return this;
        },
        getRedirectAction: function () {
            return redirectAction;
        },
        setDropdownStyleClass: function (styleClass) {
            dropdownStyleClass = styleClass;
            return this;
        },
        getDropdownStyleClass: function () {
            return dropdownStyleClass;
        },
        $get: function () {
            return {
                sidebarItems: sidebarItems,
                topbarItems: topbarItems,
                topbarDropdownItems: topbarDropdownItems,
                redirectAction: redirectAction,
                dropdownStyleClass: dropdownStyleClass,
                addSidebarItem: this.addSidebarItem,
                editSidebarItem: this.editSidebarItem,
                getSidebarItems: this.getSidebarItems,
                addTopbarItem: this.addTopbarItem,
                setTopbarItem: this.setTopbarItem,
                getTopbarItems: this.getTopbarItems,
                clearTopbarItems: this.clearTopbarItems,
                addTopbarDropdownItem: this.addTopbarDropdownItem,
                setRedirectAction: this.setRedirectAction,
                getRedirectAction: this.getRedirectAction,
                setDropdownStyleClass: this.setDropdownStyleClass,
                getDropdownStyleClass: this.getDropdownStyleClass,
            };
        }
    };
});