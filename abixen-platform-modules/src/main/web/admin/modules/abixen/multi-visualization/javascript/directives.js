var multiVisualizationDirectives = angular.module('multiVisualizationDirectives', []);

multiVisualizationDirectives.directive('queryBuilder', ['$compile', function ($compile) {
    return {
        restrict: 'E',
        scope: {
            group: '=',
            fields: '='
        },
        templateUrl: '/admin/modules/abixen/multi-visualization/html/query-builder.html',
        compile: function (element, attrs) {
            var content, directive;
            content = element.contents().remove();
            return function (scope, element, attrs) {
                scope.operators = [
                    {name: 'AND'},
                    {name: 'OR'}
                ];

                /*scope.fields = [
                 { name: 'Firstname' },
                 { name: 'Lastname' },
                 { name: 'Birthdate' },
                 { name: 'City' },
                 { name: 'Country' }
                 ];*/

                scope.conditions = [
                    {name: '='},
                    {name: '<>'},
                    {name: '<'},
                    {name: '<='},
                    {name: '>'},
                    {name: '>='}
                ];

                scope.addCondition = function () {
                    scope.group.rules.push({
                        condition: '=',
                        field: 'Firstname',
                        data: ''
                    });
                };

                scope.removeCondition = function (index) {
                    scope.group.rules.splice(index, 1);
                };

                scope.addGroup = function () {
                    scope.group.rules.push({
                        group: {
                            operator: 'AND',
                            rules: []
                        }
                    });
                };

                scope.removeGroup = function () {
                    'group' in scope.$parent && scope.$parent.group.rules.splice(scope.$parent.$index, 1);
                };

                directive || (directive = $compile(content));

                element.append(directive(scope, function ($compile) {
                    return $compile;
                }));
            }
        }
    }
}]);