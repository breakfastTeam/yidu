/**
 * Created by qingfeilee on 2015/4/18.
 */
angular.module('subjectController', ['service'])
    .controller('subjectCtrl', ['$scope', '$timeout', '$state', '$modal', 'ctx', 'SubjectRest', 'ArticleRest', function ($scope, $timeout, $state, $modal, ctx, subjectRest, articleRest) {

        $scope.pageNo = 1;
        $scope.index = 0;
        subjectRest.customGET('list').then(function (data) {
            $scope.subjects = data.obj;
            var subjectId = data.obj[0].id;
            $scope.subjectId = subjectId;
            getSubjectArticles(subjectId, $scope.pageNo);
        });

        $scope.getArticles = function(subjectId, myIndex){
            $scope.subjectId = subjectId;
                $scope.pageNo = 1;
                $scope.index = myIndex;
                getSubjectArticles(subjectId, $scope.pageNo);
        }


        function getSubjectArticles(subjectId, pageNo){
            var reqData = {subjectId: subjectId, pageNo:pageNo};
            articleRest.customGET('getSubjectArticles', reqData).then(function (data) {
                $scope.last = data.obj.last;
                if($scope.pageNo>1){
                    $scope.articles = $scope.articles.concat(data.obj.content);
                }else{
                    $scope.articles = data.obj.content;
                }

            });
        }
        $scope.loadMore = function(){
            $scope.pageNo = $scope.pageNo + 1;
            getSubjectArticles($scope.subjectId, $scope.pageNo);
        }

        $scope.goToDetail = function (articleId) {
            $state.go("articleDetail", {articleId:articleId});
        }

    }])
