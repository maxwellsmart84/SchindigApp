(function() {
  'use strict';

  angular
    .module('manageParty')
    .controller('ManagePartyController', function(
      $http,
      $scope,
      $state,
      $stateParams,
      ManagePartyService,
      EventWizardService
    ){
      var vm = this;
      $scope.listCanSwipe = true;
      var rawUserID = +localStorage.getItem('userID')
      var userID = {
        userID: rawUserID
      }
      ManagePartyService.getHostedParties(userID)
        .success(function(data){
          $scope.hostedParties = data;
          console.log('party one ',data);
        })
        .error(function(data){
          console.log('error');
        })
      $scope.onPartyDelete = function(item){
        console.log($scope.hostedParties.indexOf(item));

        var rawUserID = +localStorage.getItem('userID');
        var partyDeleteID = item.partyID;
        var partyDeleteData = {
              partyID: partyDeleteID
        }
        $scope.hostedParties.splice($scope.hostedParties.indexOf(item), 1);
        ManagePartyService.deleteParty(partyDeleteData)
          .then(function(data){
            $scope.hostedParties = $scope.hostedParties;
        });
      };

      $scope.onItemDelete = function(item) {
        var listDelete = {
          listID: item.listID
        }
        $scope.onePartyFavor.splice($scope.onePartyFavor.indexOf(item), 1);
        ManagePartyService.deleteFavorFromParty(listDelete)
          .then(function(data){
            console.log('deleted',data);
          })
        };
      $scope.data = {
          showDelete: true
       };

      ////MANAGE/EDIT HOSTED PARTIES////
      $scope.viewOne = function(party){
        localStorage.setItem('OnePartyID', party.partyID);
      };
      $scope.loadOne = function(){
        var rawPartyID = +localStorage.getItem('OnePartyID');
        ManagePartyService.getOneHostedParty(rawPartyID).then(function(data){
          console.log('data?', data);
          $scope.oneParty = data.data;
        });
      };
      $scope.loadOneFavor = function(){
        var rawPartyID = +localStorage.getItem('OnePartyID');
        ManagePartyService.getPartyFavor(rawPartyID).then(function(data){
          console.log('load favors',data);
          $scope.onePartyFavor = data.data;
        });
      };
      $scope.loadInvitedPeople = function(){
        var rawPartyID = +localStorage.getItem('OnePartyID');
        ManagePartyService.getInvitedPeeps(rawPartyID).then(function(data){
          console.log('load invited people', data.data);
          $scope.inviteList = data.data;
        });
      };
      $scope.goToManageFavor = function(){
        $state.go('manageFavor')
      };
      $scope.goToManageInvites = function(){
        $state.go('manageInvites')
      };
      $scope.getNameValue = function(value){
        console.log('changed value',value);
        $scope.partyName = value;
        console.log('scoped value',$scope.partyName);
      };
      $scope.getDescriptionValue = function(descriptionValue){
        console.log('changed descriptionValue',descriptionValue);
        $scope.description = descriptionValue;
        console.log('scoped description',$scope.description);
      };
      $scope.getLocationValue = function(locationValue){
        console.log('changed descriptionValue',locationValue);
        $scope.local = locationValue;
        console.log('scoped location',$scope.local);
      };
      $scope.editData = function(partyName, description, local, themeValue, doggy, parking, partyDate){
        var partyID = +localStorage.getItem('OnePartyID');
        console.log('what is this', local);
        var data = {
          party: {
            partyDate: partyDate,
            parking: parking,
            byob: doggy,
            theme: themeValue,
            local: local,
            partyName: partyName,
            description: description,
            partyID: partyID
          }
        };
        console.log(data.party.byob);
        EventWizardService.updateWizData(data).success(function(updatedWizData){
          console.log('new party', updatedWizData);
          $scope.oneParty = updatedWizData;
          // $state.go('home');
        });
      };

    });
}());
