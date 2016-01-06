(function() {
  'use strict';

  angular
    .module('navigation')
    .factory('NavigationService', function($http, $state){

      var ip = "http://10.0.10.72:8080";

      // var ip = "http://10.0.10.72:8080";
      var logOutUrl = ip + "/user/logout";
      var logOutUser = function(data) {
        return $http.post(logOutUrl, data);
      };

        return {
          logOutUser: logOutUser
        };
    });
}());
