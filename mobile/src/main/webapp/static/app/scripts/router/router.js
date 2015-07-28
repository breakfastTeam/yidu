app.config(["$stateProvider", "ctx", function ($stateProvider, ctx) {
    $stateProvider
        .state('subject', {
            url: '/subject',
            controller: 'subjectCtrl',
            templateUrl: ctx + '/views/content/subject.html'
        })
        .state('subscribe', {
            url: '/subscribe',
            controller: 'subscribeCtrl',
            templateUrl: ctx + '/views/content/subscribe.html'
        })
        .state('searchWechat', {
            url: '/searchWechat',
            controller: 'searchWechatCtrl',
            templateUrl: ctx + '/views/content/searchWechat.html'
        })
        .state('wechatDetail', {
            url: '/wechatDetail/:wechatId',
            controller: 'wechatDetailCtrl',
            templateUrl: ctx + '/views/content/wechatDetail.html'
        })
        .state('wechatArticles', {
            url: '/wechatArticles/:wechatId/:wechatName',
            controller: 'wechatArticlesCtrl',
            templateUrl: ctx + '/views/content/wechatArticles.html'
        })
        .state('articleDetail', {
            url: '/articleDetail/:articleId',
            controller: 'articleDetailCtrl',
            templateUrl: ctx + '/views/content/articleDetail.html'
        })
        .state('searchWechatFromInternet', {
            url: '/searchWechatFromInternet/:name',
            controller: 'searchWechatFromInternetCtrl',
            templateUrl: ctx + '/views/content/searchWechatFromInternet.html'
        })
        .state('subscribeContent', {
            url: '/subscribeContent',
            controller: 'subscribeContentCtrl',
            templateUrl: ctx + '/views/content/subscribeContent.html'
        })
}])