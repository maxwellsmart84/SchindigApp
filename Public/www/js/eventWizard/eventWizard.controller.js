(function() {
  'use strict';
  angular
    .module("eventWizard")
    .controller("WizardLandingController", function(
      $scope,
      $http,
      $state,
      $stateParams,
      $ionicPlatform,
      EventWizardService,
      $cordovaToast
    ){
      var vm = this;
      ////GET WIZARD DATA////
      EventWizardService.getWizard().then(function(data){
        $scope.wizardItems = data;
        $scope.get = function(nameId) {
          var id = parseInt(nameId.nameId);
          for (var i = 0; i < data.data.length; i++) {
            if (i === id) {
              return data.data[i-1];
            }
          }
        return null;
      };
      // $scope.partySubType = $scope.get($stateParams);
    });

    /////POST NEW PARTY/////

    $scope.partyType='none';
    // $scope.subType='none';
    $scope.getValue = function(value){
      var newVal = JSON.parse(value);
      $scope.partyType = newVal.partyType;
      // $scope.subType = newVal.subType;
    };
    // $scope.getSubValue = function(subValue){
    //   $scope.subTypeVal = subValue;
    // };
    $scope.newWizPartyPost = function(partyType, local, partyName, partyDate){
      var rawUserID = +localStorage.getItem('userID');
      console.log('what is this id', rawUserID);
      if(local === undefined){
        // $cordovaToast.show('All Fields Required', 'short', 'bottom')
      } else {
        var item = {
          party: {
            partyDate: partyDate,
            local: local.formatted_address,
            partyType: partyType,
            partyName: partyName
          },
          userID: rawUserID
        };
        console.log('item.party', item.userID);
        item.party.partyDate = JSON.stringify(item.party.partyDate);
        item.party.partyDate = JSON.parse(item.party.partyDate);
        EventWizardService.newWizPartyPost(item)
          .success(function(data){
            localStorage.setItem('partyID', data.partyID);
            // $cordovaToast.show('Party Created.'+' Finish the details here, or edit your party in Manage Parties', 'short', 'bottom')
            $state.go('details');
        });
      }
    };
    })

    .controller("EventWizardController", function(
      $scope,
      $http,
      $state,
      $stateParams,
      $cordovaContacts,
      $ionicPlatform,
      EventWizardService,
      $ionicPopup,
      $timeout
    ){
      var vm = this;
      ///PATCH DATE, TIME AND NAME/////
    $scope.partyDeetsPatch = function(description, themeVal, parking){
      var partyID = +localStorage.getItem('partyID');
      var byobElements = document.getElementsByClassName('byob');
      var themeElements = document.getElementsByClassName('theme');
      var byobStatus;
      var themeStatus;
      if(byobElements.length !== 0){
        byobStatus = true;
      } else{
        byobStatus = false;
      }
      if(themeElements.length !== 0){
        themeStatus = true;
      } else {
        themeStatus = false;
      }
      var data = {
        party: {
          description: description,
          byob: byobStatus,
          partyID: partyID,
          theme: themeVal,
          parking: parking,
          themeCheck: themeStatus
        }
      };
      EventWizardService.updateWizData(data)
        .success(function(updatedWizData){
          $state.go('stretchgoal');
      }).error(function(data){
        console.log('details error', data);
      });
    };

     ////STRETCHGOAL PATCH and SCOPES////
     $scope.parking='none';
     $scope.getParking = function(parking){
       $scope.parking = parking;
     };
     $scope.stretchGoalData = function (stretchGoal, stretchName){
       var partyID = +localStorage.getItem('partyID');
       var data = {
         party: {
           stretchGoal: stretchGoal,
           stretchName: stretchName,
           partyID: partyID
         }
       };
       EventWizardService.updateWizData(data)
        .success(function(updatedWizData){
         $state.go('favors');
       });
     };
    })
}());
