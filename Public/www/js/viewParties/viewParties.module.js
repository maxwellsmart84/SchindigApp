(function() {
  'use strict';

  angular
    .module('viewParties', [
      'ionic',
      'ngCordova'
    ])
    .config(function($stateProvider){
      $stateProvider
        .state('allParties', {
          url:'/allParties',
          templateUrl: 'js/viewParties/views/allParties.html',
          controller: 'ViewPartyController'
        })
        .state('invitedParty', {
          url:'invitedParty/:partyID',
          templateUrl: 'js/viewParties/views/invitedParty.html',
          controller: 'ViewPartyController'
        });
    });
}());
