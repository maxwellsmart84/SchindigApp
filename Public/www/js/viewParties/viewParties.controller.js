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
      ionicMaterialInk,
      $ionicPopup,
      $cordovaToast,
      ManagePartyService
    ){

      var vm = this;

      ///PATCH TO STRETCH STATUS// VENMO STUFF ///
      $scope.venmoCheck = function(){
        var userID = +localStorage.getItem('userID');
        ViewPartyService.userGet(userID).then(function(data){
          console.log('DONDE ESTA LA DATA', data.data.venmoID);
          if(data.data.venmoID != null){
            $scope.venmoPay();
          } else {
            $scope.venmoShow();
            ViewPartyService.venmoGet(partyID, userID).then(function(data){
              console.log('there is no VEnDMO', data);
            },function error(data) {
              console.log("OH FUCK",data);
            })
            return
          }
        });
      };
      $scope.pledgeStretch = function(stretchValue){
        var userID = +localStorage.getItem('userID');
        var partyID = +localStorage.getItem('partyID');
        $scope.invPartyOne.stretchStatus += stretchValue;
        var stretchStatusValue = $scope.invPartyOne.stretchStatus;
        var partyID = +localStorage.getItem('oneInvPartyID');
        var patchData = {
          party: {
            partyID: partyID,
            stretchStatus: stretchStatusValue
          }
        };
        ViewPartyService.patchStretchStatus(patchData)
          .success(function(data){
            console.log('success stretch', data);
            $scope.loadOneInvParty();
        });
      };

      $scope.venmoRealMoney = function(stretchAmount){
        var userID = +localStorage.getItem('userID');
        var partyID = +localStorage.getItem('oneInvPartyID');
        ViewPartyService.venmoPay(partyID, userID, stretchAmount).then(function(data){
          console.log('payment was successful');
        });
      };
      $scope.venmoShow = function(){
        var userID = +localStorage.getItem('userID');
        var partyID = +localStorage.getItem('partyID');
        var venmoPopup = $ionicPopup.show ({
          title: 'We are temporarily redirecting you to Venmo!',
          buttons: [
            {
              text: 'Ok',
              onTap: function(){
                window.location = "https://api.venmo.com/v1/oauth/authorize?client_id=3361&scope=make_payments%20access_profile&response_type=code&state="+ partyID + ":" + userID + "&redirect_uri=http://104.236.244.159:8100/venmo/";
                console.log('this should redirect to venmo signup');
              }
            },
            {
              text: 'Leave me!',
              onTap: function(){
                venmoPopup.close();
                console.log('I am Staying On this page');
              }
            }
          ]
        });
      };

      $scope.venmoPay = function() {
        $scope.data = {};
        var venmoPayPopup = $ionicPopup.show({
          template: '<input type="number" placeholder="Your Venmo Account Will Be Charged" ng-model="data.stretchValue">',
          title: 'How much would you like to Chip In?',
          scope: $scope,
          buttons: [
            { text: 'Cancel' },
            {
              text: '<b>Submit</b>',
              type: 'button-positive',
              onTap: function() {
                console.log('what is this value', $scope.data.stretchValue);
                console.log('good job');
                var stretchAmount = $scope.data.stretchValue;
                $scope.pledgeStretch(stretchAmount);
                $scope.venmoRealMoney(stretchAmount);
              }
            }
          ]
        });
      };


      ///////hiding and showing//////

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
    //MAX WON THIS ARGUMENT
    //
    //

    ///RSVP///

    $scope.rsvpShow = function(){
      var rsvpPopup = $ionicPopup.show ({
        title: 'Are You Going?',
        buttons: [
          {
          text: 'Yes',
          onTap: function(){
              $scope.rsvp('Yes');
              $scope.rsvpShowData = "Yes";
              rsvpPopup.close();
          }
          },
          {
          text: 'No',
          onTap: function(){
                $scope.rsvp('No');
                $scope.rsvpShowData = "No";
                rsvpPopup.close();
            }
          },
          {
          text: 'Maybe',
          onTap: function(){
                $scope.rsvp('Maybe');
                $scope.rsvpShowData = "Maybe";
                rsvpPopup.close();
            }
          },
        ]
      });
    };

    $scope.rsvp = function(rsvpStatus){
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
            console.log('load invited people', data);
            $scope.inviteList = data.data;
          });
          console.log('success', data);
          // $cordovaToast.show(data.message, 'short', 'bottom')

        });
      };
        $scope.loadRSVPStatus = function(){
          console.log('is this it?');
          var rawPartyID = +localStorage.getItem('oneInvPartyID');
          var userID = +localStorage.getItem('userID');
          ViewPartyService.getOneParty(rawPartyID, userID).then(function(data){
            console.log("what is this RSVPData?", data.data[1]);
            $scope.rsvpShowData = data.data[1].rsvpStatus;
          });
        };


    //PARKING TOAST//
     $scope.parkingButton= function (parkingInfo){
       console.log("park", parkingInfo);
       $cordovaToast.show(parkingInfo, 'short', 'center');
     };


    //INVITED PARTIES GET
    ///////////TURN INTO FUNCTION INIT/////
    $scope.getAllInvitedParties = function(){
      ViewPartyService.getInvitedParties(userID)
        .success(function(invData){
          console.log('parties success', invData[0]);
          $scope.invitedParties = invData;
        })
        .error(function(data){
          console.log('error', data);
        });
    };

      $scope.getOneInvParty = function (party){
        localStorage.setItem('oneInvPartyID', party.partyID);
      };

      $scope.loadOneInvParty = function(){
        var partyIdItem = +localStorage.getItem('oneInvPartyID');
        var userID = +localStorage.getItem('userID');
        ViewPartyService.getOneParty(partyIdItem, userID).then(function(data){
          console.log('invite data',data);
          if(data.data[1].byob === true){
            console.log(' BYOB true');
            data.data[1].byob = "Yes";
          } else {
            console.log('false');
            data.data[1].byob = "No";
          }
          if(data.data.themeCheck === true){
            console.log('theme true');
            data.data[1].theme = data.data.theme;
          } else {
            console.log('theme false');
            data.data[1].theme = 'does not have a theme';
          }
          console.log('byob statsu', data.data[1].byob);
          $scope.invPartyOne = data.data[1];
        });
      };
      $scope.loadInvitedPeople = function(){
        var rawPartyID = +localStorage.getItem('oneInvPartyID');
        var userID = +localStorage.getItem('userID');
        ManagePartyService.getInvitedPeeps(rawPartyID).then(function(data){
          $scope.inviteList = data.data;
        });
      };

      //HOSTED PARTIES GET
      $scope.getAllHostedParties = function(){
        ViewPartyService.getHostedParties(userID)
          .success(function(hostData){
            $scope.hostedParties = hostData;
            console.log('host success',hostData);
          })
          .error(function(data){
            console.log('error', rawUserID);
          });
      };
      $scope.getOneHostParty = function (party) {
        localStorage.setItem('oneHostPartyID', party.partyID);
      };
      $scope.loadOneHostParty = function(){
        var partyIdItem = +localStorage.getItem('oneHostPartyID');
        ViewPartyService.getOneParty(partyIdItem, userID).then(function(data){
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
      $scope.showFavorUnclaim = function(favor){
        var favorClaimPopup = $ionicPopup.confirm ({
          title: 'Unclaim Party Favor?',
          template: 'Are you REALLY NOT going to bring this?'
        });
        favorClaimPopup.then(function(res){
          console.log('que?',res);
          if(res){
            var data = {
              userID: rawUserID,
              listID: favor.listID
            };
            console.log('postFavor', data);
            ViewPartyService.favorClaim(data)
              .then(function(data){
                favor.claimed = false;
                favor.user = null;
                $scope.loadOneFavor();
                // $cordovaToast.show(data.data.message, 'short', 'bottom')
            });
          }
          else {
            return;
            }
          });
      };
      $scope.showFavorConfirm = function(favor){
        var favorClaimPopup = $ionicPopup.confirm ({
          title: 'Claim Party Favor?',
          template: 'Are you REALLY going to bring this?'
        });
        favorClaimPopup.then(function(res){
          if(res){
            var data = {
              userID: rawUserID,
              listID: favor.listID
            };
            ViewPartyService.favorClaim(data)
              .then(function(data){
                favor.claimed = true;
                favor.user = data.data.user;
                $scope.loadOneFavor();
                // $cordovaToast.show(data.data.message, 'short', 'bottom')
            });
          }
          else {
            return;
          }
          });
        };
    });
}());
