/**
 * Created by lenovo on 2015/5/15.
 */

angular.module('contrast',[])
    .directive("contrast", [function () {
        return {
            restrict: 'A',
            require: "ngModel",
            link: function (scope, element, attrs, ctrl) {
                if (ctrl) {
                    var otherInput = element.inheritedData("$formController")[attrs.contrast];

                    var repeatValidator = function (value) {
                        var validity = value === otherInput.$viewValue;
                        ctrl.$setValidity("contrast", validity);
                        return validity ? value : undefined;
                    };
                    ctrl.$parsers.push(repeatValidator);
                    ctrl.$formatters.push(repeatValidator);

                    otherInput.$parsers.push(function (value) {
                        ctrl.$setValidity("contrast", value === ctrl.$viewValue);
                        return value;
                    });
                }
            }
        };
    }]);
