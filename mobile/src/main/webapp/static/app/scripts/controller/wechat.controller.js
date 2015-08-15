/**
 * Created by qingfeilee on 2015/4/18.
 */
angular.module('wechatController', ['service'])
    .controller('searchWechatCtrl', ['$scope', '$timeout', '$state', '$modal', 'ctx',  'SubscribeRest', 'WechatRest',
        function ($scope, $timeout, $state, $modal, ctx, subscribeRest, wechatRest) {
            $scope.$emit('hideItem');
            $scope.$emit('hideSearch');
            $scope.$emit('hideSlider');
            $scope.$emit('setHeaderTitle',"查询公众号");
            $scope.showResult = "non-display";
            $scope.last = true;
            $scope.pageNo = 1;
            $scope.search = function(){
                $scope.pageNo = 1;
                searchWechat($scope.pageNo);
            }
            function searchWechat(pageNo){
                var reqData = {name: $scope.keyword, pageNo:pageNo};
                wechatRest.customGET('searchWechat', reqData).then(function (data) {
                    $scope.showResult = "";
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
                searchWechat($scope.typeid, $scope.pageNo);
            }

            $scope.wechatDetail = function(id){
                $state.go('wechatDetail',{wechatId: id});
            }

            $scope.toSearchFromInternet = function(){
                $state.go('searchWechatFromInternet',{name: $scope.keyword});
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
                    'btn-danger':isSubscribe
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
        }])
    .controller('searchWechatFromInternetCtrl', ['$scope','$stateParams', '$timeout', '$state', '$modal', 'ctx',  'SubscribeRest', 'WechatRest',
        function ($scope,$stateParams, $timeout, $state, $modal, ctx, subscribeRest, wechatRest) {
            $scope.keyword = $stateParams.name;
            $scope.search = function(){
                var reqData = {name: $scope.keyword};
                wechatRest.customGET('searchWechatFromInternet', reqData).then(function (data) {
                    $scope.wechats = data.obj;
                });
            }

            $scope.wechatDetail = function(id){
                $state.go('wechatDetail',{wechatId: id});
            }

            $scope.operateSubscribe = function (openId, isSubscribe, index, event) {
                event.stopPropagation();

                if(isSubscribe){
                    subscribeRest.customGET('unsubscribe', reqData).then(function (data) {
                        $scope.wechats[index].sub = false;
                    });
                }else{
                    var reqData = {openId:openId};
                    subscribeRest.customGET('subscribeNewWechat', reqData).then(function (data) {
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
        }])
    .controller('wechatDetailCtrl', ['$scope','$stateParams', '$timeout', '$state', '$modal', 'ctx',  'SubscribeRest', 'WechatRest','ArticleRest',
        function ($scope,$stateParams, $timeout, $state, $modal, ctx, subscribeRest, wechatRest, articleRest) {
            wechatRest.customGET('wechatDetail', {id:$stateParams.wechatId}).then(function (data) {
                $scope.wechat = data.obj;
            });
            $scope.pageNo = 1;
            getWechatArticles($stateParams.wechatId, $scope.pageNo);
            function getWechatArticles(wechatId, pageNo){
                articleRest.customGET('getWechatArticles', {wechatId:wechatId, pageNo:pageNo}).then(function (data) {
                    $scope.last = data.obj.last;
                    if($scope.pageNo>1) {
                        $scope.articles = $scope.articles.concat(data.obj.content);
                    }else{
                        $scope.articles = data.obj.content;
                    }
                });
            }
            $scope.loadMore = function(){
                $scope.pageNo = $scope.pageNo + 1;
                console.log($stateParams.wechatId+"   "+$scope.pageNo);
                getWechatArticles($stateParams.wechatId, $scope.pageNo);
            }

            $scope.operateSubscribe = function (wechatId, isSubscribe) {
                var reqData = {wechatId:wechatId};
                if(isSubscribe){
                    subscribeRest.customGET('unsubscribe', reqData).then(function (data) {
                        $scope.wechat.sub = false;
                    });
                }else{
                    subscribeRest.customGET('subscribe', reqData).then(function (data) {
                        $scope.wechat.sub = true;
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
                    'btn-danger':isSubscribe
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
        }])

