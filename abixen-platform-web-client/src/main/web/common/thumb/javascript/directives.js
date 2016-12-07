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

platformApplicationDirectives.directive('ngThumb', ['$window', function($window) {
    var helper = {
        support: !!($window.FileReader && $window.CanvasRenderingContext2D),
        isFile: function(item) {
            return angular.isObject(item) && item instanceof $window.File;
        },
        isImage: function(file) {
            var type =  '|' + file.type.slice(file.type.lastIndexOf('/') + 1) + '|';
            return '|jpg|png|jpeg|'.indexOf(type) !== -1;
        }
    };

    return {
        restrict: 'A',
        template: '<canvas/>',
        link: function(scope, element, attributes) {
            if (!helper.support) return;

            var params = scope.$eval(attributes.ngThumb);

            if (!helper.isFile(params.file)) return;
            if (!helper.isImage(params.file)) return;

            var canvas = element.find('canvas');
            var reader = new FileReader();

            reader.onload = onLoadFile;
            reader.readAsDataURL(params.file);

            function onLoadFile(event) {
                var img = new Image();
                img.onload = onLoadImage;
                img.src = event.target.result;
            }

            function onLoadImage() {
                var width = params.width || this.width / this.height * params.height;
                var height = params.height || this.height / this.width * params.width;
                canvas.attr({ width: width, height: height });
                canvas[0].getContext('2d').drawImage(this, 0, 0, width, height);
            }
        }
    };
}]);