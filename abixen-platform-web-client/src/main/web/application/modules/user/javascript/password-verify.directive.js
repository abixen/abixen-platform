(function () {
    'use strict';

    angular
        .module('platformUserModule')
        .directive('passwordVerify', passwordVerifyDirective);

    function passwordVerifyDirective() {
        return {
            require: "ngModel",
            scope: {
                passwordVerify: '='
            },
            link: link
        };

        function link(scope, element, attrs, ctrl) {
            scope.$watch(function () {
                var combined;

                if (scope.passwordVerify || ctrl.$viewValue) {
                    combined = scope.passwordVerify + '_' + ctrl.$viewValue;
                }
                return combined;
            }, function (value) {
                if (value) {
                    ctrl.$parsers.unshift(function (viewValue) {
                        var origin = scope.passwordVerify;
                        if (origin !== viewValue) {
                            ctrl.$setValidity("passwordVerify", false);
                            return undefined;
                        } else {
                            ctrl.$setValidity("passwordVerify", true);
                            return viewValue;
                        }
                    });
                }
            });
        }
    }
})();