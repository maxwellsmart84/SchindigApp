(function() {
  'use strict';

  angular

    .module('schindig')
    .factory('LoginRegisterService', function($http, $state, $q, $cordovaDevice, $cordovaToast){

      var ip = "http://10.0.10.72:8080";
      // var ip = "http://10.0.10.72:8080";
      var registerUrl = ip + '/user/create';
      var loginUrl = ip + '/user/login';



      var uuidAuth = function(uuid) {
        console.log('uuid fire');
        var device = $cordovaDevice.getDevice();
        $scope.uuid = device.uuid;
        console.log("testy", uuid);
        return $http.get(ip + "/validate/" + uuid).success(function(data){
          console.log('uuid data',data);
        });
      };

      var login = function(loginData) {
        console.log('login data', loginData.device);
        return $http.post(loginUrl, loginData)
          .success(function(data){
            console.log('Login Success: ', data);
            localStorage.setItem('userID', data);
            $state.go('home');
          }).error(function(data){
              console.log('error', data.message);
              // $cordovaToast.show(data.message, 'short', 'bottom')
          });
      };
      var createUser = function(data) {
        return $http.post(registerUrl, data)
          .success(function(data){
            console.log('success',data);
            $state.go('login');
          }).error(function(data){
            console.log('error', data);
            // $cordovaToast.show(data.message, 'long', 'bottom')
          });
      };
      return {
        createUser: createUser,
        login: login,
        uuidAuth: uuidAuth
      };
    });
}());
