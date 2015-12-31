(function() {
  'use strict';

  angular
    .module('viewParties')
    .controller('ViewPartyController', function(
      $http,
      $scope,
      $state,
      $stateParams,
      ViewPartyService,
      ionicMaterialMotion,
      $ionicPopup,
      ManagePartyService

    ){

      var vm = this;
      $scope.showInviteVar = false;
      $scope.showFavorVar = true;
      $scope.showInvite = function(){
        console.log('invite var', $scope.showInviteVar);
        $scope.showInviteVar = true;
        $scope.showFavorVar = false;
      };
      $scope.showFavor = function(){
        console.log('invite var', $scope.showInviteVar);
        $scope.showInviteVar = false;
        $scope.showFavorVar = true;
      };
      $scope.hostedParties = 'hostData';
      $scope.invitedParties='invData';
      $scope.favorData = 'favorData;';

      var rawUserID = +localStorage.getItem('userID');
      var userID = {
      userID: rawUserID
    };
    //THIS IS PROBABLY USED, BLAKE IS STUPID
    //THIS IS DEFINITELY NOT USED, MAX..

    ///RSVP///
    $scope.rsvpShowMainBool = true;
    $scope.rsvpShowBool = false;
    $scope.rsvpShow = function(){
      $scope.rsvpShowBool = true;
      $scope.rsvpShowMainBool = false;
    }
    $scope.rsvp = function(rsvpStatus){
      $scope.rsvpShowMainBool = true;
      $scope.rsvpShowBool = false;
      console.log('what rsvp', rsvpStatus);
      var partyID = +localStorage.getItem('oneInvPartyID');
      var userID = +localStorage.getItem('userID');
      var userRsvp = {
        partyID: partyID,
        userID: userID,
        invites: {
          rsvpStatus: rsvpStatus
        }
      };
      console.log('userrsvp', userRsvp);
      ViewPartyService.postRsvp(userRsvp)
        .success(function(data){
          ManagePartyService.getInvitedPeeps(partyID).then(function(data){
            console.log('load invited people', data.data);
            $scope.inviteList = data.data;
          });
          console.log('success', data);
        });
    };

    //INVITED PARTIES GET
    ViewPartyService.getInvitedParties(userID)
      .success(function(invData){
        console.log('parties success', invData);
        $scope.invitedParties = invData;
      })
      .error(function(data){
        console.log('error', data);
      });

      $scope.getOneInvParty = function (party){
        localStorage.setItem('oneInvPartyID', party.partyID);
      };

      $scope.loadOneInvParty = function(){
        var partyIdItem = +localStorage.getItem('oneInvPartyID');
        ViewPartyService.getOneParty(partyIdItem).then(function(data){
          console.log('invite data',data.data);
          if(data.data.byob === true){
            console.log('true');
            data.data.byob = "BYOB"
          } else {
            console.log('false');
            data.data.byob = "Booze Included"
          }
          if(data.data.themeCheck === true){
            console.log('theme true');
            data.data.theme = data.data.theme
          } else {
            console.log('theme false');
            data.data.theme = 'does not have a theme'
          }
          console.log('byob statsu', data.data.byob);
          $scope.invPartyOne = data.data;
        });
      };
      $scope.loadInvitedPeople = function(){
        var rawPartyID = +localStorage.getItem('oneInvPartyID');
        ManagePartyService.getInvitedPeeps(rawPartyID).then(function(data){
          console.log('load invited people', data.data);
          $scope.inviteList = data.data;
        });
      };
      //HOSTED PARTIES GET
      ViewPartyService.getHostedParties(userID)
        .success(function(hostData){
          $scope.hostedParties = hostData;
          console.log(hostData);
        })
        .error(function(data){
          console.log('error', rawUserID);
        });
      $scope.getOneHostParty = function (party) {
        localStorage.setItem('oneHostPartyID', party.partyID);
      };
      $scope.loadOneHostParty = function(){
        var partyIdItem = +localStorage.getItem('oneHostPartyID');
        ViewPartyService.getOneParty(partyIdItem).then(function(data){
          console.log('hostpartyData', data.data);
          $scope.hostPartyOne = data.data;
        });
      };


        //FAVOR CLAIMING//
      $scope.loadOneFavor = function(){
          var rawPartyID = +localStorage.getItem('oneInvPartyID');
          ViewPartyService.getPartyFavor(rawPartyID).then(function(data){
            console.log('favor data', data.data);
            $scope.onePartyFavor = data.data;
        });
      };

      $scope.showFavorConfirm = function(favor){
        var favorClaimPopup = $ionicPopup.confirm ({
          title: 'Claim Party Favor?',
          template: 'Are you REALLY going to bring this?'
        });
        favorClaimPopup.then(function(res){
          console.log('que?',res);
          if(res){
            var data = {
              favor: {
              favorID: favor.favor.favorID
              },
              userID: rawUserID,
              listID: favor.listID
            };
            console.log('postFavor', data);
            ViewPartyService.favorClaim(data)
              .then(function(data){
                favor.claimed = true;
                favor.user = data.data.user;
            });
          }
          else {
            return
            }
          });
        };
    });
}());
