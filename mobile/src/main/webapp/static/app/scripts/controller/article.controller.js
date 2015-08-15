/**
 * Created by qingfeilee on 2015/4/18.
 */
angular.module('articleController', ['service'])
    .controller('articleDetailCtrl', ['$scope', '$stateParams', '$timeout', '$state', '$modal', 'ctx', 'ArticleRest', 'WeixinRest','ShareLogRest',
        function ($scope, $stateParams, $timeout, $state, $modal, ctx, articleRest, weixinRest, shareLogRest) {
            var shareFrom = $stateParams.shareFrom;
            articleRest.customGET('getArticleDetail', {articleId: $stateParams.articleId, shareCustomerId:shareFrom}).then(function (data) {
                $scope.article = data.obj;
                $scope.$emit('setHeaderTitle', data.obj.title);
                getSignature();
            });

            function getSignature(){
                weixinRest.customGET('signature').then(function(data){
                    initConfig(data.obj.appId, data.obj.timestamp, data.obj.nonceStr, data.obj.signature);
                    initShare();
                });
            }
            function initConfig(appId, timestamp, nonceStr, signature){
                wx.config({
                    debug: false,
                    appId: appId,
                    timestamp: timestamp,
                    nonceStr: nonceStr,
                    signature: signature,
                    jsApiList: [
                        "onMenuShareTimeline",
                        "onMenuShareAppMessage",
                        "onMenuShareQQ",
                        "onMenuShareWeibo",
                        "onMenuShareQZone"
                    ]
                });
            }

            function initShare(){
                wx.onMenuShareTimeline({
                    title: $scope.article.title,
                    link: location.href,
                    imgUrl: 'http://www.xiaohuangdou.cn/yidu/styles/img/logo.png',
                    success: function () {
                        shareLogRest.customGET('save',{articleId:$scope.article.id, shareType:"timeline"});
                    },
                    cancel: function () {

                    }
                });

                wx.onMenuShareAppMessage({
                    title: $scope.article.title, // 分享标题
                    desc: '多一度视角，多一度世界', // 分享描述
                    link: location.href, // 分享链接
                    imgUrl: 'http://www.xiaohuangdou.cn/yidu/styles/img/logo.png', // 分享图标
                    type: '', // 分享类型,music、video或link，不填默认为link
                    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
                    success: function () {
                        shareLogRest.customGET('save',{articleId:$scope.article.id, shareType:"appmessage"});
                    },
                    cancel: function () {
                        // 用户取消分享后执行的回调函数
                    }
                });

                wx.onMenuShareQQ({
                    title: $scope.article.title, // 分享标题
                    desc: '多一度视角，多一度世界', // 分享描述
                    link: location.href, // 分享链接
                    imgUrl: 'http://www.xiaohuangdou.cn/yidu/styles/img/logo.png', // 分享图标
                    success: function () {
                        shareLogRest.customGET('save',{articleId:$scope.article.id, shareType:"qq"});
                    },
                    cancel: function () {
                    }
                });

                wx.onMenuShareWeibo({
                    title: $scope.article.title, // 分享标题
                    desc: '多一度视角，多一度世界', // 分享描述
                    link: location.href, // 分享链接
                    imgUrl: 'http://www.xiaohuangdou.cn/yidu/styles/img/logo.png', // 分享图标
                    success: function () {
                        shareLogRest.customGET('save',{articleId:$scope.article.id, shareType:"weibo"});
                    },
                    cancel: function () {
                    }
                });

                wx.onMenuShareQZone({
                    title: $scope.article.title, // 分享标题
                    desc: '多一度视角，多一度世界', // 分享描述
                    link: location.href, // 分享链接
                    imgUrl: 'http://www.xiaohuangdou.cn/yidu/styles/img/logo.png', // 分享图标
                    success: function () {
                        shareLogRest.customGET('save',{articleId:$scope.article.id, shareType:"qzone"});
                    },
                    cancel: function () {
                    }
                });
            }

            articleRest.customGET('getSimilarLatestArticle', {articleId: $stateParams.articleId}).then(function (data) {
                console.log(data);
            });

            $scope.loadMore = function(){
                console.log("-----------------------");
            }

        }])
