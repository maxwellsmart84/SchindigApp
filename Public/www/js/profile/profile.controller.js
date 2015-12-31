(function() {
  'use strict';
  angular
    .module('profile')
    .controller('ProfileController', function(
      $scope,
      $cordovaCamera,
      $cordovaVibration,
      ProfileService,
      $cordovaGeolocation,
      $ionicPlatform,
      $cordovaContacts,
      $http
    ){

//MAKES PHONE VIBRATE///
      $scope.vibrate = function() {
        $cordovaVibration.vibrate(100);
      };

      var posOptions = {timeout: 10000, enableHighAccuracy: true};
        $cordovaGeolocation
          .getCurrentPosition(posOptions)
          .then(function (position) {
            var lat  = position.coords.latitude;
            var long = position.coords.longitude;
            $scope.lat = lat;
            $scope.long = long;
          }, function(err) {
            // error
          });

          //LOGOUT USER//
      $scope.logOut = function (){
        var deviceData = {
          device: uuid
        };
        console.log("logout data", deviceData);
        NavigationService.logOutUser(deviceData);
        $state.go("login");
      };


//TAKE PICTURE AND SET IT TO {{PROFILEPICTURE}}//
      var url = 'http://tiny-tiny.herokuapp.com/collections/ng-shoppingcart4';

      $scope.getProfilePicture = function(){
        var profilePicture = $http.get('http://tiny-tiny.herokuapp.com/collections/ng-shoppingcart4')
        .success(function(data){
          $scope.profilePicture = data[1].image;
          console.log(data[5].image);
        });

      };
      $scope.takePicture = function() {
        var options = {
          destinationType: Camera.DestinationType.DATA_URL,
          encodingType: Camera.EncodingType.JPEG
        };
        $cordovaCamera.getPicture({})
          .then(function(data) {
            $scope.profilePicture =  data;
          }, function(error){
          });
      };

       $scope.savePhoto = function(item) {
       var data = {
         'image': item
       };
        $http.post(url, data);
      };
        ///CONTACTS
      $scope.getAllContacts = function (){
        $cordovaContacts.find({filter : ''}).then(function(result) { //omitting parameter to .find() causes all contacts to be returned
          $scope.contacts = result;
          console.log(result);
        });
      };


    });

  ///CONTACTS





}());
