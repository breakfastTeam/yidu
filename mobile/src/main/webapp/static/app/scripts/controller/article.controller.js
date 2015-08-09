/**
 * Created by qingfeilee on 2015/4/18.
 */
angular.module('articleController', ['service'])
    .controller('articleDetailCtrl', ['$scope','$stateParams', '$timeout', '$state', '$modal', 'ctx', 'ArticleRest',
        function ($scope, $stateParams, $timeout, $state, $modal, ctx, articleRest) {
            articleRest.customGET('getArticleDetail', {articleId:$stateParams.articleId}).then(function (data) {
                $scope.article = data.obj;
            });

        }])
