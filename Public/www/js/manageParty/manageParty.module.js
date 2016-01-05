(function() {
  'use strict';

  angular
    .module('manageParty', [
      'ionic',
      'ngCordova',
      'ion-google-place',
      'xeditable'
    ])
    .run(function(editableOptions) {
      editableOptions.theme = 'bs3';
    })
    .config(function($stateProvider){
      var partyID = +localStorage.getItem('partyID');
      $stateProvider
        .state('manageLanding', {
          url: '/manageLanding',
          templateUrl: 'js/manageParty/views/manageLanding.html',
          controller: 'ManagePartyController'
        })
        .state('manageParty', {
          url: '/manageParty/' + partyID,
          templateUrl: 'js/manageParty/views/manageHostedParty.html',
          controller: 'ManagePartyController'
        })
        .state('manageFavor', {
          url: '/manageParty/manageFavor',
          templateUrl: 'js/manageParty/views/manageHostedFavors.html',
          controller: 'ManagePartyController'
        })
        .state('manageFavorBrowse', {
          url: '/manageParty/manageFavor/Browse',
          templateUrl: 'js/manageParty/views/manageBrowseFavor.html',
          controller: 'ManagePartyController'
        })
        .state('manageInvites', {
          url: '/manageParty/manageInvites',
          templateUrl: 'js/manageParty/views/manageHostedInvites.html',
          controller: 'ManagePartyController'
        })
    });
}());
