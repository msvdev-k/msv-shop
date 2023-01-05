angular.module('shop').controller('ProductController', function ($scope, $http, $localStorage) {

    const productsContextPath = "http://localhost:8089/shop/api/v1/products"
    const cartContextPath = "http://localhost:8089/cart/api/v1/cart/" + $localStorage.msvShopGuestCartId


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