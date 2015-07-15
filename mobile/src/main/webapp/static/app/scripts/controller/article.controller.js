/**
 * Created by qingfeilee on 2015/4/18.
 */
angular.module('articleController', ['service'])
    .controller('articleDetailCtrl', ['$scope','$stateParams', '$timeout', '$state', '$modal', 'ctx', 'ArticleRest',
        function ($scope, $stateParams, $timeout, $state, $modal, ctx, articleRest) {

            $scope.$emit('changeOperateType');
            $scope.$emit('hideSearch');
            $scope.$emit('hideItem');
            $scope.$emit('hideSlider');

            $scope.$emit('setHeaderTitle',"每日一读");

            articleRest.customGET('getArticleDetail', {articleId:$stateParams.articleId}).then(function (data) {
                $scope.article = data.obj;
            });

        }])
