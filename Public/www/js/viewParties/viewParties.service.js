(function() {
  'use strict';
  angular
  .module('viewParties')
  .factory('ViewPartyService', function($http, $state){
    var vm = this;


    var ip = "http://localhost:8080";
    // var ip = "http://104.236.244.159:8100";


    var viewHostedPartiesURL = ip + '/parties/host';
    var viewInvitedPartiesURL = ip +'/parties/user';
    var rsvpURL = ip + '/party/rsvp';
    var updatePartyUrl = ip + '/party/update';


    var userGet = function(userID){
      console.log('what is this id', userID);
      return $http.get(ip + '/user/' + userID);
    };
    // var venmoPay = function(partyID, userID, amount){
    //   var venmoData = {
    //     partyID: partyID,
    //     userID: userID,
    //     amount: amount
    //   };
    //   return $http.post(ip + '/venmo/payment', venmoData)
    // };

    // var venmoReq = {
    //   method: 'GET',
    //   url: "ip + '/venmo/' + partyID + '/' + userID",
    //   headers: {
    //
    //   }
    // }
    var venmoGet = function(partyID, userID){
      return $http.get(ip + '/venmo/' + partyID + '/' + userID)
    };

    var patchStretchStatus = function(stretchValue){
      return $http.patch(updatePartyUrl, stretchValue);
    };
    var postRsvp = function(userRsvp){
      return $http.post(rsvpURL, userRsvp);
    };
    var getHostedParties = function(userID){
      return $http.post(viewHostedPartiesURL, userID)
        .success(function(data){
          console.log('host success', data);
        });
    };
    var updatedHostedParties = function (data){
      return $http.patch(updatedHostedPartiesURL, data)
        .success(function(data){
        });
    };
    var getInvitedParties = function (userID){
      return $http.post(viewInvitedPartiesURL, userID)
        .success(function(data){
      });
    };
    var getOneParty = function (partyID, userID){
       partyID = partyID;
       userID = userID;
       return $http.get(ip + '/party/' + partyID + '/' + userID)
         .success(function(data){
           console.log('what is this data', data);
       });
     };
    var getPartyFavor = function(partyID){
        partyID = partyID;
        return $http.get(ip + '/party/'+ partyID +'/favors')
          .success(function(data){
        });
      };
      var favorClaim = function(favorData){
        favorData = favorData;
        return $http.post(ip + '/party/claim', favorData)
          .success(function(data){
            console.log(data.message);
        });

      };

      var patchStretchStatus = function(stretchValue){
        return $http.patch(updatePartyUrl, stretchValue);
      };
      // var favorUnclaim = function(favorData){
      //   favorData = favorData;
      //   return $http.post(ip + '/party/claim', favorData)
      // }

    return {
      getHostedParties: getHostedParties,
      updatedHostedParties: updatedHostedParties,
      getInvitedParties: getInvitedParties,
      getOneParty: getOneParty,
      getPartyFavor : getPartyFavor,
      favorClaim: favorClaim,
      postRsvp: postRsvp,
      venmoGet: venmoGet,
      userGet: userGet,
      patchStretchStatus: patchStretchStatus
    };
  });


}());
