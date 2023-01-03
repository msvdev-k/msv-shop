(function () {
    angular
        .module('shop', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);


    function config($routeProvider) {
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
    };


    function run($http, $localStorage) {

        const cartContextPath = "http://localhost:8089/cart/api/v1/cart"

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
                else {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.msvShopUser.token;
                }
            } catch (e) {}
        }

        if (!$localStorage.msvShopGuestCartId) {
            $http.get(cartContextPath+ "/guest_cart_id")
                        .then(resp => {
                                        $localStorage.msvShopGuestCartId = resp.data;
                                        console.log(resp.data);
                                      });
        }
    };
})();