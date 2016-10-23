var platformNavigationServices = angular.module('platformNavigationServices', []);

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