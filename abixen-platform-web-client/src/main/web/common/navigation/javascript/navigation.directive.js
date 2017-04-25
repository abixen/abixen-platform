/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

(function () {

    'use strict';

    angular
        .module('platformNavigationModule')
        .directive('platformNavigation', platformNavigationDirective);

    platformNavigationDirective.$inject = [
        '$state',
        'applicationNavigationItems',
        '$translate',
        '$filter',
        'platformSecurity',
        'User'
    ];

    function platformNavigationDirective($state, applicationNavigationItems, $translate, $filter, platformSecurity, User) {

        NavigationController.$inject = ['$scope'];

        return {
            restrict: 'E',
            transclude: true,
            templateUrl: 'common/navigation/html/platform-navigation.html',
            scope: {
                addNewModule: '&addNewModule',
                addNewPage: '&addNewPage',
                logout: '&logout',
                selectedItem: '=selectedItem',
                showDropdown: '=showDropdown',
                showEditUser: '=showEditUser',
                editUser: '&editUser'
            },
            link: link,
            controller: NavigationController,
            controllerAs: 'navigation',
            bindToController: true
        };

        function NavigationController($scope) {
            var navigation = this;
            var baseUserUrl = '/api/application/users/';
            var mobileView = 992;

            navigation.applicationNavigationItems = applicationNavigationItems;
            navigation.sidebarItems = applicationNavigationItems.sidebarItems;
            navigation.topbarItems = applicationNavigationItems.topbarItems;
            navigation.topbarDropdownItems = applicationNavigationItems.topbarDropdownItems;
            navigation.redirectAction = applicationNavigationItems.getRedirectAction();
            navigation.dropdownStyleClass = applicationNavigationItems.getDropdownStyleClass();
            navigation.toggle = true;
            navigation.locales = [
                {title: 'English', img: '/common/navigation/image/united-states_flat.png', name: 'ENGLISH'},
                {title: 'Polski', img: '/common/navigation/image/poland_flat.png', name: 'POLISH'},
                {title: 'Russian', img: '/common/navigation/image/russia_flat.png', name: 'RUSSIAN'},
                {title: 'Spanish', img: '/common/navigation/image/spain_flat.png', name: 'SPANISH'},
                {title: 'Ukrainian', img: '/common/navigation/image/ukraine_flat.png', name: 'UKRAINIAN'}
            ];

            navigation.switchLocale = switchLocale;
            navigation.changeState = changeState;
            navigation.toggleSidebar = toggleSidebar;

            $scope.$watch(getWidth, onWidthChange);
            $scope.$watch(platformSecurity.getPlatformUser, onUserChange);

            $scope.$on(platformParameters.events.SIDEBAR_ELEMENT_SELECTED, function (event, id) {
                navigation.selectedItem = id;
            });

            if (!navigation.selectedItem && !$state.params.id) {
                navigation.selectedItem = 0;
            }


            function switchLocale(locale) {
                User.selectLanguage({selectedLanguage: locale.name}, {})
                    .$promise
                    .then(onSelectLanguage);

                function onSelectLanguage() {
                    navigation.selectedLocale = locale;
                    $translate.use(navigation.selectedLocale.name);
                    platformSecurity.reloadPlatformUser();
                }
            }

            function changeState(sidebarItem) {
                navigation.selectedItem = sidebarItem.id;
                if (sidebarItem.isPage) {
                    $state.go(sidebarItem.state, {id: sidebarItem.id});
                } else {
                    $state.go(sidebarItem.state);
                }
            }

            function toggleSidebar() {
                navigation.toggle = !navigation.toggle;
            }

            function onUserChange() {
                if(platformSecurity.getPlatformUser()){
                    navigation.platformUser = platformSecurity.getPlatformUser();
                    navigation.avatarUrl = baseUserUrl + navigation.platformUser.id + '/avatar/' + navigation.platformUser.avatarFileName;
                    navigation.selectedLocale = $filter('filter')(navigation.locales, {name: navigation.platformUser.selectedLanguage})[0];
                    $translate.use(navigation.selectedLocale.name);
                }
            }

            function onWidthChange(newValue) {
                if (newValue >= mobileView) {
                    navigation.toggle = true;
                } else {
                    navigation.toggle = false;
                }
            }

            function getWidth() {
                return window.innerWidth;
            }
        }

        function link(scope, element, attrs) {
            window.onresize = function () {
                scope.$apply();
            };
        }
    }
})();