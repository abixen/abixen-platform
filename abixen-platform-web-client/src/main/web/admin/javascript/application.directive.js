(function () {
    'use strict';

    angular
        .module('platformAdminApplication')
        .directive('dynamicName', dynamicNameDirective);

    function dynamicNameDirective() {

        return {
            restrict: 'A',
            terminal: true,
            priority: 100000,
            link: link
        };

        function link(scope, element) {
            var name = element.attr('dynamic-name');
            element.removeAttr('dynamic-name');
            element.attr('name', name);
            $compile(element)(scope);
        }
    }

})();