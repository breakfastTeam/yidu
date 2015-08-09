/**
 * Created by qingfeilee on 2015/4/18.
 */
angular.module('subscribeController', ['service'])

    .controller('subscribeCtrl', ['$scope','$stateParams', '$timeout', '$state', '$modal', 'ctx',  'WechatRest','ArticleRest',
        function ($scope,$stateParams, $timeout, $state, $modal, ctx, wechatRest, articleRest) {

            $scope.pageNo = 1;
            $scope.last = true;

            wechatRest.customGET('getSubscribeWechat', {id:$stateParams.wechatId}).then(function (data) {
                $scope.wechats = data.obj;
            });

            getSubscribeArticles($scope.pageNo);
            function getSubscribeArticles(pageNo){
                articleRest.customGET('getSubscribeArticles', {pageNo:pageNo}).then(function (data) {
                    $scope.wechatArticleModels = data.obj;
                    if($scope.pageNo>1) {
                        $scope.wechatArticleModels = $scope.wechatArticleModels.concat(data.obj);
                    }else{
                        $scope.wechatArticleModels = data.obj;
                    }

                    if(data.obj){
                        $scope.last = false;
                    }else{
                        $scope.last = true;
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

            articleRest.customGET('getWechatArticles', {wechatId:$stateParams.wechatId, pageNo:1}).then(function (data) {
                console.log(data.obj.content);
                $scope.articles = data.obj.content;
            });

            $scope.goToDetail = function (articleId) {
                $state.go("articleDetail", {articleId:articleId});
            }

        }])

