(function() {
  'use strict';

  angular
    .module('navigation')
    .factory('NavigationService', function($http, $state){
        var ip = "hhttp://104.236.244.159:8080/";
        var logOutUrl = ip + "/user/logout";

        var logOutUser = function(data) {
          return $http.post(logOutUrl, data)
          .success(function(data){
            console.log("logoutService", data);
          });
        };

        return {
          logOutUser: logOutUser
        };
    });
}());
