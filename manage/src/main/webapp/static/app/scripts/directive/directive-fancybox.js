/**
 * Created by qingfeilee on 2015/5/13.
 */
angular.module('fancybox', []).directive('fancybox', function () {
    return {
        require: '?ngModel',
        restrict: 'A',
        link: function (scope, element, attrs, ngModel) {
            $("#"+attrs.id).fancybox({
                openEffect	: 'elastic',
                closeEffect	: 'elastic',
                helpers : {
                    title : {
                        type : 'inside'
                    }
                }
            });
        }
    };
});

