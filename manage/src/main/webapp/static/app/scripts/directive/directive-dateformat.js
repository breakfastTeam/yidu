/**
 * Created by qingfeilee on 2015/4/27.
 */

angular.module('dateFormat',[])
    .directive('date', ['$filter',function($filter) {
        var dateFilter = $filter('date');
        return {
            require: 'ngModel',
            link: function(scope, elm, attrs, ctrl) {
                function formatter(value) {
                    return dateFilter(value, 'yyyy-MM-dd');
                }
                function parser() {
                    return ctrl.$modelValue;
                }
                ctrl.$formatters.push(formatter);
                ctrl.$parsers.unshift(parser);

            }
        };
    }]);
