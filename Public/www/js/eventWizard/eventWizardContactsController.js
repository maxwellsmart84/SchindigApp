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
       $scope.getContactList = function() {
         var userID = +localStorage.getItem('userID');
         var partyID = +localStorage.getItem('partyID');
         EventWizardService.getContacts(userID, partyID).then(function(data){
           if(data.data.length === 0){
             console.log('DAS LENGTH VAS TOO SMALL');
             $cordovaContacts
             .find({})
             .then(function(result) {
               vm.contactsArray =[];
               var stringData = JSON.stringify(result);
               var parseData = JSON.parse(stringData);
               _.each(parseData, function(el){
                 var oneUser = {
                   name: el.name.formatted,
                   phone: el.phoneNumbers[0].value
                 }
                 parsed = JSON.parse(el.id);
                 console.log('this should be a name', el.name.formatted);
                 vm.contactsArray.push(oneUser)
               });
               var userID = +localStorage.getItem('userID')
               contactData = {
                 userID: userID,
                 contactDump: vm.contactsArray
               }
               EventWizardService.postContactsRoute(contactData).then(function(data){
                 console.log('WHAT IS THIS DATA', data);
                 $scope.contactName = data.data;
               });
            }, function(error){
              console.log('error', error);
            });
           } else {
             console.log('what is this tufckinga data', data);
             $scope.contactName = data.data;
           }
         });
       };
        $scope.isChecked = false;

        ////PUSH TO CONTACT ARRAY////
        vm.contactArray = [];
        var myElements = '';
        var parsed = '';
        $scope.pushToContactArray = function(){
          myElements = document.getElementsByClassName('true');
           console.log('myelements', myElements.length);
            _.each(myElements, function(el,idx,array){
              parsed = JSON.parse(el.id);
              console.log(parsed.name);
              vm.contactArray.push(parsed);
            });
            console.log('how long is this', vm.contactArray.length);
        };
        ///CONTACT DOM STUFF
        vm.contactDataArray=[];
        var contactData = {};
        var data = {};
        var inviteDump = [];
        data.inviteDump = [];

        $scope.showConfirm = function() {
          data.inviteDump = [];
          if(vm.contactArray === []){
            $state.go('manageParty');
            return
          } else{
            var confirmPopup = $ionicPopup.confirm({
              title: 'Send Invitations',
              template: 'Are you ready to send out Invites and Create your Party?'
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
                console.log('this is the name', el.name);
                console.log('pre-lenght',vm.contactDataArray.length)
                console.log('elnameformatte', el.phone);
                vm.contactDataArray.push(contactData);
                data = {
                  inviteDump: vm.contactDataArray,
                  party: {
                    partyID: partyID
                  }
                };
              });
              vm.contactArray = [];
              console.log('thisi s the lenght',data.inviteDump.length);
                EventWizardService
                  .updateWizData(data).success(function(data){
                    console.log('data', data);
                     $state.go('manageParty');
                });
              }
              else {
                alert("There was an error");
              }
            });
          }
        };
      })
}());
