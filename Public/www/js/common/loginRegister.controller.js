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
        // CORDOVA DEVICE//
        var uuid;
      //   $ionicPlatform.ready(function() {
      //     var device = $cordovaDevice.getDevice();
      //     uuid = device.uuid;
      //     console.log("device uuid", device.uuid);
      // });

      // console.log("variable uuid", uuid);


        //LOGIN USER AND ROUTE
      $scope.login = function(username, password){
        var loginData = {
          user : {
            username: username,
            password: password
          },
          device : uuid
        };
        LoginRegisterService.login(loginData)
      };

      $scope.signUp = function(){
        $state.go('createNewUser');
      };

      //FOR TO GET TO OUR VIEWS - DELETE FOR PRODUCTION

      $scope.dog = function (){
        $state.go('hostedParty');
      };

      //DELETE TO TOP COMMENT
      $scope.createNewUserToast = function(){
        $cordovaToast.show('               Please use accurate information,                                      as this will be a primary means of communcation.','long','bottom')
      };
      $scope.createNewUser = function(username, password, firstName, lastName, email, phone){
        var userData = {
          username: username,
          password: password,
          firstName: firstName,
          lastName: lastName,
          email: email,
          phone: phone
        };
        LoginRegisterService.createUser(userData)
      };
    });

}());
