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
               $scope.contactName = vm.contactsArray;
            }, function(error){
              console.log('error', error);
            });
       };
        $scope.isChecked = false;

        ////PUSH TO CONTACT ARRAY////
        vm.contactArray = [];
        var myElements = '';
        var parsed = '';
        $scope.pushToContactArray = function(){
          myElements = '';
          vm.contactArray = [];
          myElements = document.getElementsByClassName('true');
           console.log('myelements', myElements.length);
            _.each(myElements, function(el,idx,array){
              parsed = JSON.parse(el.id);
              console.log(parsed.name);
              vm.contactArray.push(parsed);
            });
            vm.contactDataArray = [];
            console.log('how long is this', vm.contactArray.length);
        };
        ///CONTACT DOM STUFF
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
              console.log('thisi s OTHER lenght',vm.contactDataArray.length);
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
