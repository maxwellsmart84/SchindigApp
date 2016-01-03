(function() {
  'use strict';

  angular
    .module('manageParty')
    .controller('ManagePartyController', function(
      $http,
      $scope,
      $state,
      $timeout,
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
            $scope.loadOneFavorBrowse();
          })
        };
      $scope.data = {
          showDelete: true
       };

      ////MANAGE/EDIT HOSTED PARTIES////
      $scope.viewOne = function(party){
        localStorage.setItem('OnePartyID', party.partyID);
        localStorage.setItem('partyID', party.partyID);
      };
      $scope.loadOne = function(){
        console.log('does this fire?');
        var rawPartyID = +localStorage.getItem('partyID');
        var userID = +localStorage.getItem('userID')
        ManagePartyService
          .getOneHostedParty(rawPartyID, userID)
            .then(function(data){
              console.log('data?', data.data[1]);
              $scope.oneParty = data.data[1];
              $scope.showInviteVar = false;
        });
      };
      $scope.loadOneFavor = function(){
        var rawPartyID = +localStorage.getItem('OnePartyID');
        ManagePartyService.getPartyFavor(rawPartyID)
          .then(function(data){
            console.log('load favors',data);
            $scope.onePartyFavor = data.data;
        });
      };
      $scope.loadInvitedPeople = function(){
        var rawPartyID = +localStorage.getItem('OnePartyID');
        console.log('what is this id', rawPartyID);
        ManagePartyService.getInvitedPeeps(rawPartyID).then(function(data){
          console.log('load invited people', data);
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
      $scope.getByob = function(doggy){
        console.log('doggy value', doggy);
      };
      $scope.editData = function(partyName, description, local, themeValue, doggy, parking, partyDate){
        var partyID = +localStorage.getItem('OnePartyID');
        console.log('what is this', doggy);
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
        EventWizardService.updateWizData(data)
          .success(function(updatedWizData){
            console.log('new party', updatedWizData);
            $scope.oneParty = updatedWizData;
            // $state.go('home');
        });
      };
      $scope.showInviteVar = false;
      $scope.showGuestListVar = true;
      $scope.isChecked = false;

      $scope.showInvite = function(){
        $scope.showInviteVar = true;
        $scope.showGuestListVar = false;
        console.log('what is this checked', $scope.isChecked);
        console.log('invite var', $scope.showInviteVar);
      };
      $scope.showGuestList = function(){
        $scope.showInviteVar = false;
        $scope.showGuestListVar = true;
        console.log('invite var', $scope.showInviteVar);
      };


      $scope.goToFavorBrowse = function(){
        $timeout($scope.loadOneFavorBrowse(), 3000)
        $state.go('manageFavorBrowse');
      };
      $scope.loadOneFavorBrowse = function(){
        var partyID = +localStorage.getItem('OnePartyID');
        ManagePartyService.oneFavorBrowse(partyID)
          .success(function(data){
            $scope.loadOneFavor();
            $scope.browseFavors = data;
            console.log('data', data);
        });
      };
      vm.favorArray = [];
      $scope.favorCheck = false;
      $scope.pushToFavorArray = function(data){
       var myElements = document.getElementsByClassName('yes');
        _.each(myElements, function(el,idx,array){
          var parsed = JSON.parse(el.id);
          vm.favorArray.push(parsed);
        });
        var partyID = +localStorage.getItem('partyID');
        var rawUserID = +localStorage.getItem('userID');
        var data = {
          userID: rawUserID,
          partyID: partyID,
          favorDump: vm.favorArray
        };
        EventWizardService.updatePartyFavorList(data)
          .then(function(data){
            console.log('what kind of data', data);
            vm.favorArray = [];
            // $scope.loadOneFavorBrowse();
          });
      };
      $scope.goBackToManage = function(){
        $timeout($scope.loadOneFavor(), 3000)
        $state.go('manageFavor');
      };

      $scope.addFavorToData = function(favorDoo){
        var partyID = +localStorage.getItem('partyID');
        var userID = +localStorage.getItem('userID');
        var favorData = {
          favor: {
            favorName: favorDoo
          },
          partyID: partyID
        };
        if (favorData != null || favorData.favor.favorName != "") {
            var newDataBlue;
            EventWizardService.addFavorToData(favorData)
              .then(function(data){
                newDataBlue = data;
            }).then(function(){
              console.log(newDataBlue.data.favorName);
              if(newDataBlue.data.favorName == null){
                console.log('if');
                return;
            }
              else if(newDataBlue.data.favorName.length == 0 ){
                console.log('else if');
                return;
            }
              else {
                console.log('else', newDataBlue.data);
                $scope.browseFavors.unshift(newDataBlue.data);
            }
          });
        } else {
          console.log('doodad');
          return;
        }
      };
    });
}());
