/**
 * Created by qingfeilee on 2015/4/18.
 */
angular.module('subscribeController', ['service'])
    .controller('subscribeCtrl', ['$scope', '$timeout', '$state', '$modal', 'ctx', 'SubscribeRest', 'WechatRest', 'WechatTypeRest',
        function ($scope, $timeout, $state, $modal, ctx, subscribeRest, wechatRest, wechatTypeRest) {
            $scope.$emit('showItem');
            $scope.$emit('showSearch');
            $scope.$emit('setHeaderTitle',"订阅");
            $scope.$watch('$viewContentLoaded', function() {
                $timeout(function(){
                    $scope.$emit('hideSlider');
                },1000)
            });
            $scope.pageNo = 1;
            wechatTypeRest.customGET('getWechatType').then(function (data) {
                $scope.$emit('changeOperateType', "subscribe", data.obj);
                var typeId = data.obj[0].firstLevel.id;
                $scope.typeId = typeId;
                getWechatByType(typeId, $scope.pageNo);
            });

            $scope.wechatDetail = function(id){
                $state.go('wechatDetail',{wechatId: id});
            }

            $scope.$on('getWechats', function (e, typeId) {
                getWechatByType(typeId, $scope.pageNo);
            });

            $scope.last = true;
            function getWechatByType(typeId, pageNo){
                $scope.$emit('hideSlider');
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
                getWechatByType($scope.typeid, $scope.pageNo);
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

    .controller('subscribeContentCtrl', ['$scope','$stateParams', '$timeout', '$state', '$modal', 'ctx',  'WechatRest','ArticleRest',
        function ($scope,$stateParams, $timeout, $state, $modal, ctx, wechatRest, articleRest) {
            $scope.$emit('hideSearch');
            $scope.$emit('showItem');
            $scope.$emit('setHeaderTitle',"私人订制");
            $scope.$watch('$viewContentLoaded', function() {
                $timeout(function(){
                    $scope.$emit('hideSlider');
                },1000)
            });
            $scope.pageNo = 1;
            $scope.last = true;
            $scope.$emit('changeOperateType', "personal", [{name:"按时间", type:"time"},{name:"按账号", type:"type"}]);

            $scope.showType = "time";
            wechatRest.customGET('getSubscribeWechat', {id:$stateParams.wechatId}).then(function (data) {
                $scope.wechats = data.obj;
            });

            getSubscribeArticles($scope.pageNo);
            function getSubscribeArticles(pageNo){
                articleRest.customGET('getSubscribeArticles', {pageNo:pageNo}).then(function (data) {
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
                getSubscribeArticles($scope.pageNo);
            }

            $scope.$on('changePersonal', function (e, type) {
                $scope.showType = type;
                $scope.$emit('hideSlider');
            });

            $scope.toWechatArticles = function(id, name){
                $state.go("wechatArticles", {wechatId:id, wechatName:name});
            }

            $scope.goToDetail = function (articleId) {
                $state.go("articleDetail", {articleId:articleId});
            }

        }])
    .controller('wechatArticlesCtrl', ['$scope','$stateParams', '$timeout', '$state', '$modal', 'ctx', 'ArticleRest',
        function ($scope,$stateParams, $timeout, $state, $modal, ctx, articleRest) {
            $scope.$emit('hideItem');
            $scope.$emit('hideSearch');
            $scope.$emit('hideSlider');
            $scope.$emit('setHeaderTitle', $stateParams.wechatName);

            articleRest.customGET('getWechatArticles', {wechatId:$stateParams.wechatId, pageNo:1}).then(function (data) {
                $scope.articles = data.obj.content;
            });

            $scope.goToDetail = function (articleId) {
                $state.go("articleDetail", {articleId:articleId});
            }

        }])

