(function() {
 'use strict';
 angular
  .module('schindig',[
    'ionic',
    'eventWizard',
    'loginRegister',
    'viewParties',
    'navigation',
    'profile',
    'ngCordova',
    'manageParty',
    'underscore',
    'ionic-material',
    'underscore',
    'ion-google-place',
    'ion-alpha-scroll'
  ])
    .run(function($ionicPlatform, $cordovaDevice, $http, $state) {
      var uuid;
<<<<<<< HEAD

      // var ip = 'http://10.0.10.72:8080';
      var ip = 'http://104.236.244.159:8100';


      // var ip = 'http://localhost:8080';
      $ionicPlatform.ready(function() {
        // UUID STUFF- COMMENT OUT FOR DESIGN
        var device = $cordovaDevice.getDevice();
        uuid = device.uuid;
        console.log("device uuid", device.uuid);
        $http.get(ip + "/validate/" +uuid).success(function(data){
            console.log('response from validate route', data.userID);
            console.log('response from validate route', data);

            if (data === 0) {
              console.log('failure uuid');
              $state.go('login');
            }
            else {
              console.log('success uuid', data);
              localStorage.setItem('userID', data);
              $state.go('home');
            }
          });
        if (window.cordova && window.cordova.plugins && window.cordova.plugins.Keyboard) {
          cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
          cordova.plugins.Keyboard.disableScroll(true);
        }
        if (window.StatusBar) {
          StatusBar.hide();
        }
      });
    })
    .config(function($stateProvider, $urlRouterProvider) {
      $urlRouterProvider.otherwise('/login');
      $stateProvider
      .state('redirect', {
        url: '/',
        templateUrl: 'js/common/views/redirect.html'
      })
      .state('login', {
        url: '/login',
        templateUrl: 'js/common/views/login.html',
        controller: 'LoginRegisterController'
      })
      .state('createNewUser', {
        url: '/createNewUser',
        templateUrl: 'js/common/views/createNewUser.html',
        controller: 'LoginRegisterController'
      });
    });
    angular
      .module('underscore', [])
      .factory('_', function ($window) {
        return $window._;
      });
}());
