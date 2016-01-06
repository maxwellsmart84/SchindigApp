(function() {
  'use strict';

  angular
    .module('schindig')
    .controller('LoginRegisterController', function(
      $http,
      $scope,
      $state,
      $stateParams,
      LoginRegisterService,
      $cordovaDevice,
      $ionicPlatform,
      $cordovaToast
    )
      {
        //LOGIN USER AND ROUTE
      $scope.login = function(username, password){
        var vm = this;
        vm.device = $cordovaDevice.getDevice();
        // uuid = device.uuid;
        console.log('login uuid', device.uuid);
        var uuid = vm.device.uuid;
        var loginData = {
          user : {
            username: username,
            password: password
          },
          device: uuid
        };
        LoginRegisterService.login(loginData).then(function(data){
          console.log('is this my userid', data);
        });
      };

      $scope.signUp = function(){
        $state.go('createNewUser');
      };

      //DELETE TO TOP COMMENT
      $scope.createNewUserToast = function(){
        // $cordovaToast.show('               Please use accurate information,                                      as this will be a primary means of communcation.','long','bottom')
      };
      $scope.createNewUser = function(username, password, firstName, lastName, email, phone){
        console.log('bluecat');
        var userData = {
          username: username,
          password: password,
          firstName: firstName,
          lastName: lastName,
          email: email,
          phone: phone
        };
        LoginRegisterService.createUser(userData);
      };
    });

}());
