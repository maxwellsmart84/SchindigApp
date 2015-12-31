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
                   var stringData = JSON.stringify(result);
                   var parseData = JSON.parse(stringData);
                   $scope.contactName = parseData;
                }, function(error){
                  console.log('error', error);
                });
            };
        $scope.isChecked = false;

        ////PUSH TO CONTACT ARRAY////
        vm.contactArray = [];
        $scope.pushToContactArray = function(){
           var myElements = document.getElementsByClassName('true');
            _.each(myElements, function(el,idx,array){
              var parsed = JSON.parse(el.id);
              vm.contactArray.push(parsed);
            });
        };
        ///CONTACT DOM STUFF
        vm.contactDataArray=[];
        $scope.showConfirm = function() {
          var confirmPopup = $ionicPopup.confirm({
            title: 'Send Invitations',
            template: 'Are you ready to send out Invites and Create your Party?'
          });
          confirmPopup.then(function(res){
            if(res){
              var partyID = +localStorage.getItem('partyID');
              var contactData;
              var data;
              _.each(vm.contactArray, function(el,idx,array){
               contactData = {
                 name: el.name.formatted,
                 phone: el.phoneNumbers[0].value
              };
              vm.contactDataArray.push(contactData);
              data = {
                inviteDump: vm.contactDataArray,
                party: {
                  partyID: partyID
                }
              };
            });
              EventWizardService
                .updateWizData(data).then(function(data){
                   $state.go('home');
              });
            }
            else {
              alert("There was an error");
            }
          });
        };
      })
}());
