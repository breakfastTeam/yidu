/**
 * Created by qingfeilee on 2015/4/18.
 */
angular.module('indexController', [])
    .controller('indexCtrl', ['$scope','$state', function ($scope, $state) {

        $scope.openSlider = function(){
            $scope.menuOpen = "st-menu-open";
        }
        $scope.closeSlider = function(){
            $scope.menuOpen = "";
        }
        $scope.goToSearch = function(){
            $state.go('searchWechat');
        }

    }])
