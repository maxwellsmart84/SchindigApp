(function() {
  'use strict';
  angular
    .module('eventWizard')
    .controller('ContactsController', function(
      $scope,
      $http,
      $state,
      $stateParams,
      EventWizardService,
      $cordovaContacts,
      $ionicPopup
    ){
      var vm = this;
       //CORDOVA CONTACTS AND INVITATIONS //
              ////NG CORDOVA FINDS CONTACTS BY NO FILTER AND RETURNS ALL CONTACTS///////
              ////IN PHONE HARDWARE/////
       vm.contactsArray =[];
       $scope.getContactList = function() {
             $cordovaContacts
             .find({})
             .then(function(result) {
               var stringData = JSON.stringify(result);
               var parseData = JSON.parse(stringData);
               _.each(parseData, function(el){
                 var oneUser = {
                   name: el.name.formatted,
                   phone: el.phoneNumbers[0].value
                 }
                 parsed = JSON.parse(el.id);
                 vm.contactsArray.push(oneUser)
               });

            }, function(error){
              console.log('error', error);
            });
            $scope.contactName = vm.contactsArray;
       };




        ////PUSH TO CONTACT ARRAY////
            //////BUILDS AN ARRAY OF ALL SELECTED CONTACTS///////
        $scope.isChecked = false;
        vm.contactArray = [];
        var myElements = '';
        var parsed = '';
        $scope.pushToContactArray = function(){
          myElements = '';
          vm.contactArray = [];
          myElements = document.getElementsByClassName('true');
            _.each(myElements, function(el,idx,array){
              parsed = JSON.parse(el.id);
              vm.contactArray.push(parsed);
            });
            vm.contactDataArray = [];
        };
        ///SENDS CONTACTS TO DB ////
            ////SUCCESS FUNCTION RUNS BACK TO SPLASH PAGE/////
            ////UPDATES CURRENT PARTY'S INVITE LIST WHILE SENDING OUT SMS TO ALL PHONE NUMBERS////
        vm.contactDataArray=[];
        var contactData = {};
        var data = {};
        $scope.showConfirm = function() {
          vm.contactDataArray = [];
          if(vm.contactArray === []){
            $state.go('manageParty');
            return
          } else{
            var confirmPopup = $ionicPopup.confirm({
              title: 'Send Invitations',
              template: 'Are you ready to send out Invites and View your Party?'
            });
            confirmPopup.then(function(res){
              if(res){
                var partyID = +localStorage.getItem('partyID');
                data.inviteDump = [];
                _.each(vm.contactArray, function(el,idx,array){
                 contactData = {
                   name: el.name,
                   phone: el.phone
                };
                vm.contactDataArray.push(contactData);
                data = {
                  inviteDump: vm.contactDataArray,
                  party: {
                    partyID: partyID
                  }
                };
              });
              vm.contactArray = [];
              $state.go('home');
              EventWizardService
                .updateWizData(data).success(function(data){
                  console.log('data', data);
                });
              }
              else {
                alert("No one wants to come anyway..");
              }
            });
          }
        };
      })
}());
