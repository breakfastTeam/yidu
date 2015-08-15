/**
 * Created by qingfeilee on 2015/4/18.
 */
angular.module('findController', ['service'])
    .controller('findCtrl', ['$scope', '$timeout', '$state', '$modal', 'ctx', 'SubscribeRest', 'WechatRest', 'WechatTypeRest',
        function ($scope, $timeout, $state, $modal, ctx, subscribeRest, wechatRest, wechatTypeRest) {

            $scope.index = 0;
            $scope.pageNo = 1;
            wechatTypeRest.customGET('getWechatType').then(function (data) {
                $scope.wechatTypes = data.obj;
                $scope.$watch('$viewContentLoaded', function() {
                    swipe();
                });
                toggleLevelInner(0);
                $scope.$watch("$viewContentLoaded",function(){
                    $timeout(function(){gmu.$('#navigator').navigator();},1);
                });
            });



            $scope.toggleLevel = function(index){
                toggleLevelInner(index);
            }

            function toggleLevelInner(index){
                $scope.index=index;
                $scope.wechatSecondLevels = $scope.wechatTypes[index].secondLevel;
            }

            $scope.goToFindWechat = function(wechatTypeId){
                $state.go("findWechat", {wechatTypeId:wechatTypeId});
            }

        }])
    .controller('findWechatCtrl',['$scope','$state', '$stateParams','SubscribeRest', 'WechatRest',
        function($scope, $state,$stateParams,subscribeRest, wechatRest){

        $scope.typeId = $stateParams.wechatTypeId;
        $scope.pageNo = 1;
        getWechatByType($scope.typeId, $scope.pageNo);


        $scope.last = true;
        function getWechatByType(typeId, pageNo){
            var reqData = {typeId: typeId, pageNo:pageNo};
            wechatRest.customGET('getWechatByType', reqData).then(function (data) {
                $scope.last = data.obj.last;
                if($scope.pageNo>1) {
                    $scope.wechats = $scope.wechats.concat(data.obj.wechatModel);
                }else{
                    $scope.wechats = data.obj.wechatModel;
                }
            });
        }

        $scope.loadMore = function(){
            $scope.pageNo = $scope.pageNo + 1;
            getWechatByType($scope.typeId, $scope.pageNo);
        }

        $scope.operateSubscribe = function (wechatId, isSubscribe, index, event) {
            event.stopPropagation();
            var reqData = {wechatId:wechatId};
            if(isSubscribe){
                subscribeRest.customGET('unsubscribe', reqData).then(function (data) {
                    $scope.wechats[index].sub = false;
                });
            }else{
                subscribeRest.customGET('subscribe', reqData).then(function (data) {
                    $scope.wechats[index].sub = true;
                });
            }
        }

        /**
         设置订阅/取消订阅按钮样式
         * **/
        $scope.getBtnClass = function(isSubscribe) {
            return {
                'btn':true,
                'pull-right':true,
                'bg-success':!isSubscribe,
                'btn-info':isSubscribe
            };
        };
        /**
         设置订阅/取消订阅按钮样式
         * **/
        $scope.getIconClass = function(isSubscribe) {
            return {
                'fa':true,
                'fa-plus':!isSubscribe,
                'fa-minus':isSubscribe
            };
        };

        $scope.wechatDetail = function(id){
            $state.go('wechatDetail',{wechatId: id});
        };
        $scope.goToSearchWechat = function(){
            $state.go('searchWechatFromInternet');
        }

    }])