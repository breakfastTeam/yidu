app.config(["$stateProvider", "ctx", function ($stateProvider, ctx) {
    $stateProvider
        .state('article', {
            url: '/article',
            controller: 'articleCtrl',
            templateUrl: ctx + '/views/content/article.html'
        })
        .state('articleEdit', {
            url: '/articleEdit',
            controller: 'articleEditCtrl',
            templateUrl: ctx + '/views/content/articleEdti.html'
        })
        .state('articleDetail', {
            url: '/articleDetail/:articleId',
            controller: 'articleDetailCtrl',
            templateUrl: ctx + '/views/content/articleDetail.html'
        })

        .state('subject', {
            url: '/subject',
            controller: 'subjectCtrl',
            templateUrl: ctx + '/views/content/subject.html'
        })
        .state('subjectEdit', {
            url: '/subjectEdit/:subjectId',
            controller: 'subjectEditCtrl',
            templateUrl: ctx + '/views/content/subjectEdit.html'
        })
        .state('subjectAdd', {
            url: '/subjectAdd',
            controller: 'subjectEditCtrl',
            templateUrl: ctx + '/views/content/subjectEdit.html'
        })

        .state('wechatType', {
            url: '/wechatType',
            controller: 'wechatTypeCtrl',
            templateUrl: ctx + '/views/content/wechatType.html'
        })

        .state('wechat', {
            url: '/wechat',
            controller: 'wechatCtrl',
            templateUrl: ctx + '/views/content/wechat.html'
        })
        .state('wechatAdd', {
            url: '/wechatAdd',
            controller: 'wechatAddCtrl',
            templateUrl: ctx + '/views/content/wechatAdd.html'
        })

        .state('wechatTypeEdit', {
            url: '/wechatTypeEdit/:wechatTypeId',
            controller: 'wechatTypeEditCtrl',
            templateUrl: ctx + '/views/content/wechatTypeEdit.html'
        })
        .state('wechatTypeAdd', {
            url: '/wechatTypeAdd',
            controller: 'wechatTypeEditCtrl',
            templateUrl: ctx + '/views/content/wechatTypeEdit.html'
        })

}])