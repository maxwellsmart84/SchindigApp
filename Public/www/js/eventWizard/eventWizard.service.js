(function() {
  'use strict';

  angular
    .module('eventWizard')
    .factory('EventWizardService', function($http, $state){

      var ip = 'http://10.0.10.72:8080';
      var wizCreateUrl = ip + "/party/create";
      var wizUpdateUrl = ip + "/party/update";
      var favorUpdateUrl = ip + "/party/favor";
      var addFavorToDataUrl = ip + "/favor/save";
      var getWizardUrl = ip + "/wizard";
      var invitePostUrl = ip + "/party/update";
      var postContacts = ip + '/user/contacts';

      var getContacts = function(userID, partyID){
        var userID = userID;
        var partyID = partyID;
        return $http.get(ip + '/' + partyID + '/' + userID + '/contacts');
      };
      var postContactsRoute = function(contactsData){
        return $http.post(postContacts, contactsData)
      };
      var getWizard = function() {
        return $http.get(getWizardUrl);
      };
      var newWizPartyPost = function(item){
        return $http.post(wizCreateUrl, item);
      };
      var getOneWizParty = function (wizID){
        return $http.get(getWizardUrl);
      };
      var updateWizData = function(updatedWizData){
        return $http.patch(wizUpdateUrl, updatedWizData);
      };
      var addFavorToData = function(favorData) {
        return $http.post(addFavorToDataUrl, favorData);
      };
      var updateFavorData = function(updatedFavorData){
        return $http.patch(wizUpdateUrl, updatedFavorData);
      };
      var updatePartyFavorList = function (data){
        return $http.post(favorUpdateUrl, data);
      };
      var getFavors = function (partyID) {
        var favorGetUrl = ip + "/favor/" + partyID;
        return $http.get(favorGetUrl);
      };
      var postInviteData = function(inviteData){
        return $http.patch(invitePostUrl, inviteData);
      };

      return {
        getWizard: getWizard,
        newWizPartyPost: newWizPartyPost,
        updateWizData: updateWizData,
        getOneWizParty: getOneWizParty,
        getFavors: getFavors,
        updateFavorData: updateFavorData,
        addFavorToData: addFavorToData,
        postInviteData: postInviteData,
        updatePartyFavorList: updatePartyFavorList,
        postContactsRoute: postContactsRoute,
        getContacts: getContacts
      };
    });
}());
