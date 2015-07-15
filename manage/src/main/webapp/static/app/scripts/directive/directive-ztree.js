angular.module('tree', []).directive('tree', function () {
    return {
        require: '?ngModel',
        restrict: 'A',
        link: function (scope, element, attrs, ngModel) {
            var defaultSetting = {
                data: {
                    key: {
                        title: "t"
                    },
                    simpleData: {
                        enable: true
                    }
                },
                callback: {
                    onClick: function (event, treeId, treeNode, clickFlag) {
                    }
                }
            };
            var setting = scope.setting ||defaultSetting;
            var zNodes = scope.zNodes;
            $.fn.zTree.init(element, setting, zNodes);
        }
    };
});

