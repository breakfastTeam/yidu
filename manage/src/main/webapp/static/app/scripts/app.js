'use strict';

// Declare app level module which depends on views, and components
var app = angular.module('dongtai', [
    'ui.router',
    'ui.bootstrap',
    'angularUtils.directives.dirPagination',
    'tree',
    'ng.ueditor',
    'ui.tree',
    'fileupload',
    'fancybox',
    'restangular',
    'jlareau.pnotify',
    'mgcrea.ngStrap.datepicker',
    'mgcrea.ngStrap.timepicker',
    'dongtai.constants',
    'indexController',
    'articleController',
    'subjectController',
    'wechatController',
    'wechatTypeController',
    'contrast'

]);
app.config(function ($stateProvider, $urlRouterProvider) {
       $urlRouterProvider.when("", "/article");
   });
app.config(function(RestangularProvider, ctx) {
    RestangularProvider.setBaseUrl(ctx);
});
app.config(function(paginationTemplateProvider) {
    paginationTemplateProvider.setPath('views/pagination.tpl.html');
});
app.config(['notificationServiceProvider', function(notificationServiceProvider) {
    notificationServiceProvider.setDefaults({
        history: false,
        closer: false,
        closer_hover: false
    });
}])
app.filter('to_trusted', ['$sce', function ($sce) {
    return function (text) {
        return $sce.trustAsHtml(text);
    }
}]);