let SpringApp = angular.module('shop', ['ngRoute']);

const productsContextPath = "http://localhost:8080/shop/api/v1/products"
const cartContextPath = "http://localhost:8080/shop/api/v1/cart"


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
        .otherwise({
            redirectTo: '/'
        });
});




SpringApp.controller('ProductController', function ($scope, $rootScope, $routeParams, $http) {

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