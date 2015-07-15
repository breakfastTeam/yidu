/**
 * Created by qingfeilee on 2015/4/18.
 */
angular.module('indexController', [])
    .controller('indexCtrl', ['$scope', function ($scope) {
        $scope.addHideLeftSlider = true;
        /***
         * 定义左侧工具栏的操作内容
         * **/
        $scope.$on('changeOperateType', function (e, operateType, data) {
            $scope.operateType = operateType;
            if (operateType == "subject") {//分类
                $scope.subjects = data;
            } else if (operateType == "subscribe") {//订阅
                $scope.wechatTypeModals = data;
            } else if (operateType == "personal") {//私人订制
                $scope.personals = data;
            }
            $('.slimScrollDiv').slimscroll({
                height: 'auto'
            });
        });


        /**
         * 隐藏左侧工具栏和右侧内容区
         * **/
        $scope.$on('hideSlider', function () {
            $scope.sliderDisplay = "left-slider-hide";
        });
        /**
         * 显示左侧工具栏和右侧内容区
         * **/
        $scope.$on('showSlider', function () {
            $scope.sliderDisplay = "";
        });

        /**
         * 显示左侧工具栏和右侧内容区
         * **/
        $scope.$on('hideHeader', function () {
            $scope.headerDisplay = "non-display";
            $scope.contentContentTopZero = "pdt-0";
        });

        /**
         * 显示左侧工具栏和右侧内容区
         * **/
        $scope.$on('showHeader', function () {
            $scope.headerDisplay = "";
        });

        /**
         * 显示item
         * **/
        $scope.$on('showItem', function () {
            $scope.itemShow = "";
            $scope.backShow = "non-display";
        });
        /**
         * 隐藏item
         * **/
        $scope.$on('hideItem', function () {
            $scope.itemShow = "non-display";
            $scope.backShow = "";
        });

        /**
         * 显示搜索
         * **/
        $scope.$on('showSearch', function () {
            $scope.searchShow = "";
        });
        /**
         * 隐藏搜索
         * **/
        $scope.$on('hideSearch', function () {
            $scope.searchShow = "non-display";
        });

        /**
         * 设置应用标题
         * **/
        $scope.$on('setHeaderTitle', function (e, title) {
            $scope.headerTitle = title;
        });

        /**
         * 获取当前热点词下的所有文章
         * **/
        $scope.getArticles = function (subjectId) {
            $scope.parentIndex = -1;
            $scope.$broadcast('getArticles', subjectId);
        }

        /**
         * 获取当前分类下的所有微信信息
         * **/
        $scope.getWechats = function (typeId, secondIndex, type) {
            if (type == 'first') {
                $scope.parentIndex = -1;
            }
            console.log($scope.parentIndex + "  " + secondIndex);
            $scope.secondIndex = secondIndex;
            $scope.$broadcast('getWechats', typeId);
        }

        $scope.changePersonal = function (type) {
            $scope.$broadcast('changePersonal', type);
        }

        /**
         * 切换左侧工具栏和右侧内容栏的隐藏和展示
         * **/
        $scope.toggleSlider = function () {
            var display = $scope.sliderDisplay;

            if (!display || display == "left-slider-hide") {
                $scope.addHideLeftSlider = false;
                $scope.sliderDisplay = "left-slider-show";
            } else {
                $scope.sliderDisplay = "left-slider-hide";

            }

        };
        /**
         * 切换左侧工具栏和右侧内容栏的隐藏和展示
         * **/
        $scope.back = function () {
            window.history.go(-1);
        };


        /**
         * 隐藏左侧工具栏同时展开右侧内容区
         * **/
        $scope.hideSlider = function () {
            $scope.sliderDisplay = "display-none";
            $scope.contentFullWidth = "mgl-0";
        }

        $scope.toggleSecondLevel = function (parentIndex) {
            $scope.parentIndex = parentIndex;

        }

    }])
