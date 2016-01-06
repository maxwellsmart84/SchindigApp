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
      NavigationService,
      LoginRegisterService
    ){
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
        var device = $cordovaDevice.getDevice();
        console.log('what device? ', device.uuid);
        var dataUUID = device.uuid;
        var deviceData = {
          device: dataUUID
        };
        console.log("logout data", deviceData.device);
        NavigationService.logOutUser(deviceData);
        $state.go("login");
      };


    });
}());
