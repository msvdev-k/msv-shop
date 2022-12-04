let SpringApp = angular.module('shop', ['ngRoute', 'ngStorage']);

const productsContextPath = "http://localhost:8080/shop/api/v1/products"
const cartContextPath = "http://localhost:8080/shop/api/v1/cart"
const authContextPath = "http://localhost:8080/shop/auth"


SpringApp.config(function ($routeProvider, $locationProvider, $sceProvider) {
    $sceProvider.enabled(false);
    $routeProvider
        .when('/', {
            templateUrl: 'products.html',
            controller: 'ProductController'
        })
        .when('/cart', {
            templateUrl: 'cart.html',
            controller: 'CartController'
        })
        .when('/login', {
                    templateUrl: 'login.html',
                    controller: 'AuthController'
                })
        .otherwise({
            redirectTo: '/'
        });
});




SpringApp.controller('ProductController', function ($scope, $rootScope, $routeParams, $http, $localStorage) {

    if ($localStorage.msvShopUser) {

        try {
            let jwt = $localStorage.msvShopUser.token;
            let payload = JSON.parse(atob(jwt.split('.')[1]));
            let currentTime = parseInt(new Date().getTime() / 1000);
            if (currentTime > payload.exp) {
                console.log("Token is expired!!!");
                delete $localStorage.msvShopUser;
                $http.defaults.headers.common.Authorization = '';
            }
        } catch (e) {
        }

        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.msvShopUser.token;
    }


    $scope.pagination = {
        pageNumber: 1,
        pageSize: 10
    };


    $scope.loadProducts = function() {
        $http({
            url: productsContextPath,
            method: 'GET',
            params: {
                page: $scope.pagination.pageNumber,
                page_size: $scope.pagination.pageSize
            }
        }).then(resp => {
                            console.log(resp.data)
                            $scope.products = resp.data.content;
                            $scope.pagination = {
                                    empty: resp.data.empty,
                                    firstPage: resp.data.first,
                                    lastPage: resp.data.last,
                                    pageNumber: resp.data.number+1,
                                    pageSize: resp.data.size,
                                    totalPages: resp.data.totalPages,
                                    pageSequence: Array(resp.data.totalPages).fill().map((element, index) => index + 1)
                                };
                            console.log($scope.pagination)
                        },
                resp => {
                            console.error(resp);
                        });
    };




    $scope.addToCart = function (productId) {

        $http.put(cartContextPath + '/add/' + productId)
             .then(resp => {
                               console.log(resp);
                           },
                   resp => {
                               console.error(resp);
                           });

    };


    $scope.shiftPage = function (count) {
        $scope.pagination.pageNumber += count;
        $scope.loadProducts();
    };

    $scope.changePage = function (number) {
            $scope.pagination.pageNumber = number;
            $scope.loadProducts();
        };

    $scope.loadProducts();
});




SpringApp.controller('CartController', function ($scope, $rootScope, $routeParams, $location, $http) {


    $scope.loadCartItems = function() {
            $http.get(cartContextPath)
            .then(resp => {
                            $scope.cart = resp.data;
                            console.log($scope.cart);
                          },
                  resp => {
                            console.error(resp);
                          });
    };


    $scope.addToCart = function (productId) {
            $http.put(cartContextPath + '/add/' + productId)
                 .then(resp => {
                                   console.log(resp);
                                   $scope.loadCartItems();
                               },
                       resp => {
                                   console.error(resp);
                               });

    };


    $scope.subFromCart = function (productId) {
                $http.put(cartContextPath + '/sub/' + productId)
                     .then(resp => {
                                       console.log(resp);
                                       $scope.loadCartItems();
                                   },
                           resp => {
                                       console.error(resp);
                                   });

        };


    $scope.removeFromCart = function (productId) {
            $http.put(cartContextPath + '/remove/' + productId)
                 .then(resp => {
                                   console.log(resp);
                                   $scope.loadCartItems();
                               },
                       resp => {
                                   console.error(resp);
                               });

    };

    $scope.loadCartItems();

});




SpringApp.controller('AuthController', function ($scope, $rootScope, $routeParams, $location, $http, $localStorage) {

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