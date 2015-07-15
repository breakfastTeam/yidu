/**
 * Created by qingfeilee on 2015/5/13.
 */
angular.module('fileupload', []).directive('fileupload', function () {
    return {
        restrict: 'A',
        scope:{
            done:'&',
            progressall:'&'
        },
        link: function (scope, element, attrs) {
            var optionsObj = {
              dataType:'json'
            };
            if(scope.done){
                optionsObj.done=function(e, data){
                    scope.$apply(function(){
                        scope.done({e:e, data:data})
                    });
                };
            }
            if(scope.progressall){
                optionsObj.progressall = function(e, data){
                    scope.progressall({e:e, data:data});
                }
            }
            element.fileupload(optionsObj);
        }
    };
});

