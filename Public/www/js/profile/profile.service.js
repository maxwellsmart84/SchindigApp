(function() {
  'use strict';

  angular
    .module('profile')
    .factory('ProfileService', function($http, $state){
      var vm = this;
      var ip = 'http://1localhost:8080';
      var userProfilePartiesURL = ip +'/user';
      var userProfileUpdateURL = ip + '/user/update';

      var getData = function() {
        console.log('profile service');
        return $http.get('http://tiny-tiny.herokuapp.com/collections/ng-shoppingcart2');
      };
      return {
        getData: getData
      };
    });


}());
