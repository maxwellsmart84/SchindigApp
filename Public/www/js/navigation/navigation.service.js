(function() {
  'use strict';

  angular
    .module('navigation')
    .factory('NavigationService', function($http, $state){

      var ip = "http://104.236.244.159:8100";

      // var ip = "http://104.236.244.159:8100";
      var logOutUrl = ip + "/user/logout";
      var logOutUser = function(data) {
        return $http.post(logOutUrl, data);
      };
        return {
          logOutUser: logOutUser
        };
    });
}());
