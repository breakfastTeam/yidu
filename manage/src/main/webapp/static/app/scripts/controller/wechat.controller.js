/**
 * Created by qingfeilee on 2015/4/18.
 */
angular.module('wechatController', ['service'])
    .controller('wechatCtrl', ['$scope', '$timeout', '$state', '$modal', 'ctx', 'WechatRest','WechatTypeRest', function ($scope, $timeout, $state, $modal, ctx, wechatRest, wechatTypeRest) {

        $scope.wechatTypeName = "请选择类型";
        $scope.wechatTypeId = "";
        wechatTypeRest.customGET('getAllWechatType').then(function (data) {
            $scope.wechatTypes = data.obj;
        });

        $scope.setWechatTypeName = function(wechatTypeName, wechatTypeId){
            $scope.wechatTypeName = wechatTypeName;
            $scope.wechatTypeId = wechatTypeId;
        }

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
            wechatRest.customGET('list', reqData).then(function (data) {
                $scope.pageSize = data.obj.size;
                $scope.wechats = data.obj.content;
                $scope.total = data.obj.totalElements;
            });
        }

        $scope.pagination = {
            current: 1
        };

        $scope.changeWechatType = function(wechatId, wechatTypeId, index){
            wechatRest.customGET('changeWechatType', {wechatId:wechatId, wechatTypeId:wechatTypeId}).then(function (data) {
                $scope.wechats[index] = data.obj;
            });
        }
    }])

    .controller('wechatAddCtrl', ['$scope', '$timeout', '$state', '$modal', 'ctx', 'WechatRest','WechatTypeRest', function ($scope, $timeout, $state, $modal, ctx, wechatRest, wechatTypeRest) {

        $scope.wechatTypeName = "请选择类型";
        $scope.wechatTypeId = "";
        wechatTypeRest.customGET('getAllWechatType').then(function (data) {
            $scope.wechatTypes = data.obj;
        });

        $scope.setWechatTypeName = function(wechatTypeName, wechatTypeId){
            $scope.wechatTypeName = wechatTypeName;
            $scope.wechatTypeId = wechatTypeId;
        }

        $scope.wechats = "";
        $scope.search = function () {
            wechatRest.customGET('searchWechatFromInternet', {name:$scope.name}).then(function (data) {
                $scope.wechats = data.obj;
            });
        }
        $scope.save = function (index) {
            wechatRest.customGET('save', {wechat:$scope.wechats[index]}).then(function (data) {
                $scope.wechats[index] = data.obj;
            });
        }

        $scope.delete = function (wechatId, index) {
            console.log(wechatId);
            wechatRest.customGET('delete', {wechatId:wechatId}).then(function (data) {
                $scope.wechats[index].id = "";
            });
        }


        $scope.changeWechatType = function(wechatTypeName, wechatTypeId, index){
            $scope.wechats[index].wechatType = {name:wechatTypeName, id:wechatTypeId};
            $scope.wechats[index].typeId = wechatTypeId;
        }


    }])
