'use strict';

// Declare app level module which depends on views, and components
var app = angular.module('dongtai', [
    'ui.router',
    'ui.bootstrap',
    'ngAnimate',
    'angular-loading-bar',
    'constants',
    'restangular',
    'indexController',
    'subjectController',
    'findController',
    'subscribeController',
    'articleController',
    'wechatController'

]);
app.config(function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.when("", "/subject");
});
app.config(function(RestangularProvider, ctx) {
    RestangularProvider.setBaseUrl(ctx);
});

//app.config(function(cfpLoadingBarProvider) {
//    cfpLoadingBarProvider.includeSpinner = true;
//})

app.filter('to_trusted', ['$sce', function ($sce) {
    return function (text) {
        return $sce.trustAsHtml(text);
    }
}]);