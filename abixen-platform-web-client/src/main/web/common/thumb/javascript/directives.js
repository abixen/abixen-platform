var platformApplicationDirectives = angular.module('platformApplicationDirectives', []);

var CONTENT_HEIGHT_OFFSET = 175; // modal-header + modal-footer + a little of spare whitespace extra

platformApplicationDirectives.directive('windowResize', ['$window', '$timeout', function ($window, $timeout) {

    return {
        link: link,
        scope: {
            header: '@header',
            body: '@body',
            footer: '@footer'
        }
    };

    function link(scope, element, attrs){

        scope.height = $window.innerHeight;

        scope.redraw = function ($event) {
            var size = {
                width: window.innerWidth || document.body.clientWidth,
                height: window.innerHeight || document.body.clientHeight
            };
            var documentWrapper = angular.element(document);
            var wrapperHeight = angular.element(documentWrapper.find('form-container'));

            var offsetHeight = 0;
            for (var i = 0; i < wrapperHeight.length; i++) {
                offsetHeight += wrapperHeight[i].offsetHeight;
            }
            var contentHeightComputed = (size.height - offsetHeight - CONTENT_HEIGHT_OFFSET);
            angular.element(element[0]).css('height', contentHeightComputed + 'px');
        };
        scope.redraw();

        $timeout(function () {
            scope.redraw();
        }, 500);

        angular.element($window).bind('resize', function () {
            scope.redraw();
        });
    }
}]);