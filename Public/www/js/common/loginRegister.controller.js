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
        // console.log('cordova getting device', $cordovaDevice.getUUID());

      //cordova
      //   var uuid;
      //   $ionicPlatform.ready(function() {
      //     var device = $cordovaDevice.getDevice();
      //     console.log('what is this', device.uuid);
      //     uuid = device.uuid;
      //     console.log("device uuid", uuid);
      // });



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
        LoginRegisterService.login(loginData);
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
