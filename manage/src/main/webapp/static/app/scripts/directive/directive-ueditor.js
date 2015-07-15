///**
// * Created by qingfeilee on 2015/4/19.
// */
//angular.module('ueditor', []).directive('ueditor', function () {
//    return {
//        require: '?ngModel',
//        restrict: 'A',
//        scope: {
//            config: "=",
//            ready: "="
//        },
//        link: function (scope, element, attrs, ngModel) {
//            if(typeof UE != 'undefined'){
//                var editor = new UE.ui.Editor({
//                    initialContent:'',
//                    toolbars: [
//                        ['source', 'undo', 'redo', 'bold', 'italic',  'removeformat', 'formatmatch', 'autotypeset', 'blockquote',
//                            'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist']
//                    ],
//                    initialFrameHeight:scope.height || 120,
//                    autoHeightEnabled:false,
//                    wordCount:false,
//                    elementPathEnabled: false
//                });
//            }
//
//            editor.render(element[0]);
//        }
//    };
//});
//
//

