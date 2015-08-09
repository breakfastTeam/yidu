app.config(["$stateProvider", "ctx", function ($stateProvider, ctx) {
    $stateProvider
        .state('subject', {
            url: '/subject',
            controller: 'subjectCtrl',
            templateUrl: ctx + '/views/content/subject.html'
        })
        .state('find', {
            url: '/find',
            controller: 'findCtrl',
            templateUrl: ctx + '/views/content/find.html'
        })
        .state('findWechat', {
            url: '/findWechat/:wechatTypeId',
            controller: 'findWechatCtrl',
            templateUrl: ctx + '/views/content/findWechats.html'
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
        .state('subscribe', {
            url: '/subscribe',
            controller: 'subscribeCtrl',
            templateUrl: ctx + '/views/content/subscribe.html'
        })
}])