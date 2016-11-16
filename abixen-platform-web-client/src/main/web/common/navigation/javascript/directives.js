var platformNavigationDirectives = angular.module('platformNavigationDirectives', []);

platformNavigationDirectives.directive('platformNavigation', ['$log', '$state', 'applicationNavigationItems', 'User', function ($log, $state, applicationNavigationItems, User) {
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
            scope.baseUserUrl = '/api/application/users/';
            scope.avatarUrl = '';
            var mobileView = 992;

            scope.getWidth = function () {
                return window.innerWidth;
            };

            scope.$watch(scope.getWidth, function (newValue, oldValue) {
                $log.log('scope.toggle: ' + scope.toggle);
                if (newValue >= mobileView) {
                    scope.toggle = true;
                } else {
                    scope.toggle = false;
                }

            });

            if (!scope.selectedItem && !$state.params.id) {
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

            scope.toggleSidebar = function () {
                scope.toggle = !scope.toggle;
            };

            window.onresize = function () {
                scope.$apply();
            };

            scope.$on(platformParameters.events.SIDEBAR_ELEMENT_SELECTED, function (event, id) {
                scope.selectedItem = id;
            });

            var getUser = function (id) {
                $log.log('selected user id:', id);
                if (id) {
                    User.get({id: id}, function (data) {
                        scope.user = data;
                        scope.avatarUrl = scope.baseUserUrl + scope.user.id + '/avatar/' + scope.user.avatarFileName;
                        $log.log('User has been got: ', scope.user);
                    });
                } else {
                    scope.user = {};
                }
            };
            if (scope.platformUser != null) {
                getUser(scope.platformUser.id);
            } else {
                scope.$watch('platformUser', function () {
                    $log.log('scope platform user watch', scope.platformUser);
                    if (scope.platformUser != null) {
                        getUser(scope.platformUser.id);
                    }
                });
            }

        }
    };
}]);