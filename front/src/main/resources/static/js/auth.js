angular.module('shop').controller('AuthController', function ($scope, $location, $http, $localStorage) {

    const authContextPath = "http://localhost:8089/auth"


    $scope.login = function() {
        $http.post(authContextPath, $scope.user)
             .then(resp => {
                               console.log(resp);

                               if (resp.data.token) {

                                    $http.defaults
                                         .headers
                                         .common
                                         .Authorization = 'Bearer ' + resp.data.token;

                                    $localStorage.msvShopUser = {username: $scope.user.username, token: resp.data.token};

                                    $scope.user.username = null;
                                    $scope.user.password = null;

                                    $location.path('/');
                               }
                           },
                   resp => {
                               console.error(resp);
                           });
    };


    $scope.logout = function() {
        delete $localStorage.msvShopUser;
        $http.defaults.headers.common.Authorization = '';
        $scope.user = null;
        $location.path('/');
    };


    $scope.isLogin = function() {
        if ($localStorage.msvShopUser) {
            return true;
        } else {
            return false;
        }
    };

});