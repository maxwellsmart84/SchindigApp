(function() {
  'use strict';

  angular
    .module('viewParties')
    .controller('ViewPartyController', function(
      $http,
      $scope,
      $state,
      $stateParams,
      ViewPartyService

    ){

      var vm = this;

      $scope.hostedParties = 'hostData';
      $scope.invitedParties='invData';
      var rawUserID = +localStorage.getItem('userID');

      var userID = {
      userID: rawUserID
    };



    //INVITED PARTIES GET
    ViewPartyService.getInvitedParties(userID)
      .success(function(invData){
        $scope.invitedParties = invData;
      })
      .error(function(data){
        console.log('error');
      });


      $scope.getOneInvParty = function (party){
        localStorage.setItem('OnePartyID', party.partyID);
      };

      $scope.loadOneInvParty = function(){
        var partyIdItem = +localStorage.getItem('OnePartyID');
        ViewPartyService.getOneInvitedParty(partyIdItem).then(function(data){
          $scope.invPartyOne = data.data;
        });
      };


      //HOSTED PARTIES GET
      ViewPartyService.getHostedParties(userID)
        .success(function(hostData){
          $scope.hostedParties = hostData;
        })
        .error(function(data){
          console.log('error');
        });


        $scope.loadOneFavor = function(){
            var rawPartyID = +localStorage.getItem('OnePartyID');
            ViewPartyService.getPartyFavor(rawPartyID).then(function(data){
              console.log('favor data', data.data);
              $scope.onePartyFavor = data.data;
                });
              };


    });


}());
