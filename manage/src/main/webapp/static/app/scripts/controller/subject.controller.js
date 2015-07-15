/**
 * Created by qingfeilee on 2015/4/18.
 */
angular.module('subjectController', ['service'])
    .controller('subjectCtrl', ['$scope', '$timeout', '$state', '$modal', 'ctx', 'SubjectRest', function ($scope, $timeout, $state, $modal, ctx, subjectRest) {
        /**
         * 获取4S店用户信息
         * **/
        getResultsPage(1);

        $scope.pageChanged = function (pageNo) {
            getResultsPage(pageNo);
        };
        /**
         * 获取指定页面信息
         * **/
        function getResultsPage(pageNo) {
            $scope.currentPage = pageNo;
            var reqData = {subject: $scope.subject, pageNo: pageNo};
            subjectRest.customGET('list', reqData).then(function (data) {
                $scope.pageSize = data.obj.size;
                $scope.subjects = data.obj.content;
                $scope.total = data.obj.totalElements;
            });
        }

        $scope.pagination = {
            current: 1
        };
    }])
    .controller('subjectEditCtrl', ['$scope', '$stateParams', '$timeout', '$state', '$modal', 'ctx', 'SubjectRest', function ($scope, $stateParams, $timeout, $state, $modal, ctx, subjectRest) {
        var subjectId = $stateParams.subjectId;
        if (subjectId) {
            var reqData = {id: subjectId};
            subjectRest.customGET('getSubjectDetail', reqData).then(function (data) {
                $scope.subject = data.obj;
            });
        }
        $scope.saveOrUpdate = function () {
            var reqData = {subject: $scope.subject};
            subjectRest.customGET('saveOrUpdate', reqData).then(function (data) {
                if (data.success) {
                    window.history.go(-1);
                }
            });

        }
    }])
