(function() {
  'use strict';

  angular
    .module('navigation')
    .controller('NavigationController', function(
      $http,
      $scope,
      $state,
      $stateParams,
      $ionicPlatform,
      $cordovaDevice,
      NavigationService

    ){

      var uuid;
      $ionicPlatform.ready(function() {
        var device = $cordovaDevice.getDevice();
        uuid = device.uuid;
        console.log("navigation uuid", device.uuid);
        LoginRegisterService.uuidAuth(uuid);
    });

        //TOP LEVEL NAVIGATION//
      $scope.wizardGo = function(){
        $state.go('wizard');
      };
      $scope.managePartyGo = function(){
        $state.go('manageLanding');
      };
      $scope.allPartiesGo = function(){
        console.log('go go go ');
        $state.go('allParties');
      };
      $scope.profileGO = function(){
        $state.go('profile');
      };
          //LOGOUT USER//
      $scope.logOut = function (){
        var deviceData = {
          device: uuid
        };
        console.log("logout data", deviceData);
        NavigationService.logOutUser(deviceData);
        $state.go("login");
      };


    });
}());
