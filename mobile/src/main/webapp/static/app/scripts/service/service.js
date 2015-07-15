/**
 * Created by qingfeilee on 2015/4/18.
 */
angular.module('service', ['restangular'])

    .factory('SubjectRest', ['Restangular', function(Restangular) {
        return Restangular.all('subject');
    }])
    .factory('ArticleRest', ['Restangular', function(Restangular) {
        return Restangular.all('article');
    }])
    .factory('SubscribeRest', ['Restangular', function(Restangular) {
        return Restangular.all('subscribe');
    }])
    .factory('WechatRest', ['Restangular', function(Restangular) {
        return Restangular.all('wechat');
    }])
    .factory('WechatTypeRest', ['Restangular', function(Restangular) {
        return Restangular.all('wechatType');
    }])

