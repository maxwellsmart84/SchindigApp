(function() {
  'use strict';

  angular

    .module('eventWizard', [
      'ionic',
      'schindig',
      'ngCordova',
      'profile',
      'manageParty',
      'underscore',
      'ion-google-place'
    ])
    .config(function($stateProvider, $urlRouterProvider){
    $stateProvider

      .state('wizard', {
        url: "/wizard",
        templateUrl: "js/eventWizard/views/evtNameType.html",
        controller: "WizardLandingController"

      })
      .state('details', {
        url:"/wizard/details",
        templateUrl: "js/eventWizard/views/partyDetails.html",
        controller: "EventWizardController as evtCtrl"
      })
      .state('stretchgoal', {
        url:"/wizard/stretchgoal",
        templateUrl:"js/eventWizard/views/evtStretchGoal.html",
        controller: "EventWizardController as evtCtrl"
      })
      .state('favors', {
        url:"/wizard/favors",
        templateUrl: "js/eventWizard/views/evtFavors.html",
        controller: "FavorsController as fvrCtrl"
      })
      .state('invites', {
        url:"/wizard/invites",
        templateUrl:"js/eventWizard/views/evtInvites.html",
        controller:"ContactsController as ctcCtrl"
      });
    });

}());
