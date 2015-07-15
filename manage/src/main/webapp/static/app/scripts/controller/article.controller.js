/**
 * Created by qingfeilee on 2015/4/18.
 */
angular.module('articleController', ['service'])
    .controller('articleCtrl', ['$scope', '$timeout', '$state', '$modal', 'ctx', 'ArticleRest','SubjectRest',   function ($scope, $timeout, $state, $modal, ctx, articleRest, subjectRest) {

        $scope.subjectName = "请选择类型";
        $scope.subjectId = "";
        $scope.setSubjectName = function(subjectName, subjectId){
            $scope.subjectName = subjectName;
            $scope.subjectId = subjectId;
        }

        subjectRest.customGET('getAllSubject').then(function (data) {
            $scope.subjects = data.obj;
        });


        getResultsPage(1);

        $scope.pageChanged = function (pageNo) {
            getResultsPage(pageNo);
        };
        /**
         * 获取指定页面信息
         * **/
        function getResultsPage(pageNo) {
            $scope.currentPage = pageNo;
            var reqData = {article: $scope.article, pageNo: pageNo};
            articleRest.customGET('list', reqData).then(function (data) {
                $scope.pageSize = data.obj.size;
                $scope.articles = data.obj.content;
                $scope.total = data.obj.totalElements;
            });
        }

        $scope.pagination = {
            current: 1
        };


        $scope.publish = function(articleId,index){
            articleRest.customGET('publish', {articleId:articleId}).then(function (data) {
                $scope.articles[index] = data.obj;
            });
        }

        $scope.unpublish = function(articleId, index){
            articleRest.customGET('unpublish', {articleId:articleId}).then(function (data) {
                $scope.articles[index] = data.obj;
            });
        }

        $scope.changeSubject = function(articleId, subjectId, index){
            articleRest.customGET('changeSubject', {articleId:articleId, subjectId:subjectId}).then(function (data) {
                $scope.articles[index] = data.obj;
            });
        }

    }])
    .controller('articleDetailCtrl', ['$scope','$stateParams', '$timeout', '$state', '$modal', 'ctx', 'ArticleRest',   function ($scope,$stateParams, $timeout, $state, $modal, ctx, articleRest) {

        var articleId = $stateParams.articleId;
        articleRest.customGET('getArticleDetail', {articleId:articleId}).then(function (data) {
            $scope.article = data.obj;
        });
    }])
