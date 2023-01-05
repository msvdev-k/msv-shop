angular.module('shop').controller('CartController', function ($scope, $http, $localStorage) {

    const cartContextPath = "http://localhost:8089/cart/api/v1/cart/" + $localStorage.msvShopGuestCartId

    // Загрузка корзины
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


    // Очистка корзины
    $scope.clearCart = function() {
            $http.delete(cartContextPath)
            .then(resp => {
                            $scope.loadCartItems();
                          },
                  resp => {
                            console.error(resp);
                          });
    };


    // Добавить единицу товара в корзину
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


    // Удалить единицу товара из корзину
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


    // Удалить товар из корзины
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