/**
 * Created by qingfeilee on 2015/4/18.
 */
angular.module('wechatTypeController', ['service'])
    .controller('wechatTypeCtrl', ['$scope', '$timeout', '$state', '$modal', 'ctx', 'WechatTypeRest', function ($scope, $timeout, $state, $modal, ctx, wechatTypeRest) {
        /**
         * 获取4S店用户信息
         * **/
        getResultsPage(1);

        $scope.pageChanged = function (pageNo) {
            getResultsPage(pageNo);
        };
        /**
         * 获取指定页面信息
         * **/
        function getResultsPage(pageNo) {
            $scope.currentPage = pageNo;
            var reqData = {subject: $scope.subject, pageNo: pageNo};
            wechatTypeRest.customGET('list', reqData).then(function (data) {
                $scope.pageSize = data.obj.size;
                $scope.wechatTypes = data.obj.content;
                $scope.total = data.obj.totalElements;
            });
        }

        $scope.pagination = {
            current: 1
        };

        /**
         * 获取一级分类
         * */
        wechatTypeRest.customGET('getAllFirstLevel').then(function (data) {
            console.log(data.obj);
        });

    }])
    .controller('wechatTypeEditCtrl', ['$scope', '$stateParams', '$timeout', '$state', '$modal', 'ctx', 'WechatTypeRest', function ($scope, $stateParams, $timeout, $state, $modal, ctx, wechatTypeRest) {
        var wechatTypeId = $stateParams.wechatTypeId;
        if (wechatTypeId) {
            var reqData = {id: wechatTypeId};
            wechatTypeRest.customGET('getWechatTypeDetail', reqData).then(function (data) {
                $scope.wechatType = data.obj;
                if($scope.wechatType.parentId){
                    wechatTypeRest.customGET('getSecondLevel', {parentId: $scope.wechatType.parentId}).then(function (data) {
                        $scope.secondLevels = data.obj;
                        if (data.obj.length <= 0) {
                            $scope.secondLevels = [{}];
                        }
                    });
                }else{
                    wechatTypeRest.customGET('getSecondLevel', {parentId: $scope.wechatType.id}).then(function (data) {
                        $scope.secondLevels = data.obj;
                        if (data.obj.length <= 0) {
                            $scope.secondLevels = [{}];
                        }
                    });
                }

            });
        } else {
            $scope.wechatType = {};
            $scope.secondLevels = [{}];
        }

        $scope.addSecondLevel = function () {
            if ($scope.secondLevels.length < 5) {
                $scope.secondLevels.push({});
            }
        }

        $scope.saveOrUpdate = function () {
            if ($scope.wechatType) {
                var parentId = $scope.wechatType.parentId;
                var reqData = "";
                if (parentId) {//二级菜单
                    reqData = {wechatType: $scope.wechatType};
                } else {//一级菜单
                    reqData = {wechatType: $scope.wechatType, wechatTypes: $scope.secondLevels};
                }
            } else {
                reqData = {wechatType: $scope.wechatType, wechatTypes: '[' + $scope.secondLevels + ']'};
            }

            console.log(reqData);
            wechatTypeRest.customGET('saveOrUpdate', reqData).then(function (data) {
                if (data.success) {
                    window.history.go(-1);
                }
            });
        }
    }])
