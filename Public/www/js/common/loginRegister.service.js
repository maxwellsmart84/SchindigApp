(function() {
  'use strict';

  angular

    .module('schindig')
    .factory('LoginRegisterService', function($http, $state, $cordovaDevice, $cordovaToast){
      // var ip = "http://10.0.10.72:8100";
      var ip = "http://104.236.244.159:8100";
      // var ip = http://10.0.10.72:8100"
      // var ip = "http://104.236.244.159:8100";


      var registerUrl = ip + '/user/create';
      var loginUrl = ip + '/user/login';

      var login = function(loginData) {
        console.log('login data', loginData.device);
        return $http.post(loginUrl, loginData)
          .success(function(data){
            console.log('Login Success: ', data);
            localStorage.setItem('userID', data);
            var device = $cordovaDevice.getDevice();
            var uuid = device.uuid;
            $http.get(ip + "/validate/" +uuid).success(function(data){
                console.log('response from validate route', data);
                if (data === 0) {
                  console.log('failure uuid');
                  $state.go('login');
                }
                else {
                  console.log('success uuid');
                  localStorage.setItem('userID', data);
                  $state.go('home');
                }
              });
          });
      };
      var createUser = function(data) {
        return $http.post(registerUrl, data)
          .success(function(data){
            console.log('success',data);
            $state.go('login');
          }).error(function(data){
            console.log('error', data);
            // $cordovaToast.show(data.message, 'long', 'bottom');
          });
      };
      return {
        createUser: createUser,
        login: login
      };
    });
}());
