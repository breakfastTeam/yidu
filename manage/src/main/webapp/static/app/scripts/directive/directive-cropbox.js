/**
 * Created by xiaolong on 2015/5/25.
 */
angular.module('cropbox', []).directive('cropbox', function () {
    return {
        require: '?ngModel',
        restrict: 'A',
        link: function (scope, element, attrs, ngModel) {
            console.log("aaaaa");
            var options =
            {
                thumbBox: '.thumbBox',
                spinner: '.spinner'
            }
            $("#"+attrs.id).on('change', function(){
                var reader = new FileReader();
                console.log(attrs);
                console.log($('#imageBox'));
                console.log($('#imageBox').length);
                reader.onload = function(e) {
                    options.imgSrc = e.target.result;
                    console.log("___________________");
                    var cropper = $('#imageBox').cropbox(options);
                }
                reader.readAsDataURL(this.files[0]);
                this.files = [];
            })
        }
    };
});

