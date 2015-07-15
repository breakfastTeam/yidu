/**
 * Created by qingfeilee on 2015/4/18.
 */
angular.module('service', ['restangular'])

    .factory('ArticleRest', ['Restangular', function(Restangular) {
        return Restangular.all('article');
    }])
    .factory('SubjectRest', ['Restangular', function(Restangular) {
        return Restangular.all('subject');
    }])
    .factory('WechatTypeRest', ['Restangular', function(Restangular) {
        return Restangular.all('wechatType');
    }])
    .factory('WechatRest', ['Restangular', function(Restangular) {
        return Restangular.all('wechat');
    }])