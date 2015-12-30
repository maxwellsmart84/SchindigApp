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
  ])
    .run(function($ionicPlatform, $cordovaDevice, $http, $state) {
      var uuid;
      var ip = 'http://localhost:8080';
      $ionicPlatform.ready(function() {
        //UUID STUFF- COMMENT OUT FOR DESIGN
        // var device = $cordovaDevice.getDevice();
        // uuid = device.uuid;
        // console.log("device uuid", device.uuid);
        // $http.get(ip + ":8080/validate/" +uuid).success(function(data){
        //     console.log('response from validate route', data);
        //     if (data === 0) {
        //       $state.go('login');
        //     }
        //     else {
        //       localStorage.setItem('userID', data);
        //       $state.go('home');
        //     }
        //   });
        if (window.cordova && window.cordova.plugins && window.cordova.plugins.Keyboard) {
          cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
          cordova.plugins.Keyboard.disableScroll(true);
        }
        if (window.StatusBar) {
          StatusBar.styleDefault();
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
