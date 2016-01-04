(function() {
  'use strict';

  angular
    .module('manageParty')
    .factory('ManagePartyService', function($http, $state){
      var vm = this;


      var ip = "http://104.236.244.159:8100";
      // var ip = "http://104.236.244.159:8100";

      var viewHostedPartiesURL = ip + '/parties/host';
      var viewInvitedPartiesURL = ip +'/parties/user';
      var updatedHostedPartiesURL = ip + '/party/update';
      var deleteFavorUrl = ip + '/party/favor/delete';
      var deletePartyUrl = ip + '/party/delete';
      var addFavorToDataUrl = ip + "/favor/save";


      var addFavorToData = function(favorData) {
        return $http.post(addFavorToDataUrl, favorData);
      };
      var oneFavorBrowse = function(partyID){
        return $http.get(ip + '/party/' + partyID + '/filter');
      };
      var deleteParty = function(data){
        return $http.post(deletePartyUrl, data);
      };
      var deleteFavorFromParty = function(listDelete){
        console.log(listDelete);
        return $http.post(deleteFavorUrl, listDelete)
          .then(function(data){
            console.log('data', data);
        });
      };
      var getInvitedPeeps = function(partyID){
        return $http.get(ip+'/party/'+partyID+'/invites').success(function(data){
          console.log('invite list',data.length);
        });
      };
      var getHostedParties = function(userID){
        console.log('dog');
        return $http.post(viewHostedPartiesURL, userID)
          .success(function(data){
            console.log('succes view', data);
        });
      };
      var getOneHostedParty = function(partyID, userID){
        console.log(partyID);
        console.log('getting one party');
        partyID = partyID;
        userID = userID;
        return $http.get(ip+'/party/'+partyID+'/'+userID).success(function(data){
          console.log('one party', data);
        });
      };
      var getPartyFavor = function(partyID){
        partyID = partyID;
        return $http.get(ip + '/party/'+ partyID +'/favors')
      };
      var updatedHostedParties = function (data){
        return $http.patch(updatedHostedPartiesURL, data)
          .success(function(data){
            console.log('success updateParty', data);
          });
      };
      return {
        getHostedParties: getHostedParties,
        updatedHostedParties: updatedHostedParties,
        getOneHostedParty: getOneHostedParty,
        getPartyFavor: getPartyFavor,
        getInvitedPeeps: getInvitedPeeps,
        deleteFavorFromParty: deleteFavorFromParty,
        deleteParty: deleteParty,
        oneFavorBrowse: oneFavorBrowse,
        addFavorToData: addFavorToData
      };

    });

}());
