/**
 * Created by qingfeilee on 2015/4/18.
 */
angular.module('indexController', [])
    .controller('indexCtrl', ['$scope','$state','CustomerRest', function ($scope, $state, customerRest) {

        $scope.openSlider = function(){
            $scope.menuOpen = "st-menu-open";
        }
        $scope.closeSlider = function(){
            $scope.menuOpen = "";
        }
        $scope.goToSearch = function(){
            $state.go('searchWechat');
        }

        customerRest.customGET('getCustomerInfo').then(function (data) {
            window.sessionStorage.setItem("customerId",data.obj.id);
            window.sessionStorage.setItem("customerName",data.obj.name);
            window.sessionStorage.setItem("customerAvatar",data.obj.avatar);

        });
        $scope.$on('setHeaderTitle', function (e, t) {
            $scope.title = t;
        });

    }])
