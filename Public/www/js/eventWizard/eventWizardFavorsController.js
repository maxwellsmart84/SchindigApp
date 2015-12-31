(function() {
  'use strict';

  angular
    .module('eventWizard')
      .controller('FavorsController', function(
        $scope,
        $http,
        $state,
        $stateParams,
        EventWizardService,
        $cordovaToast

      ){
        var vm = this;
        ////GET FAVORS////
        var partyID = +localStorage.getItem('partyID');
         EventWizardService
          .getFavors(partyID).then(function(data){
              $scope.favors = data.data
          });

        /////FAVORS PATCH/////
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
          EventWizardService.updatePartyFavorList(data).success(function(data){
            // $cordovaToast.show(data.message, 'short', 'bottom')
            $state.go('invites');
          });
        };

        /////ADD FAVOR TO DATA/////
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
                if(newDataBlue.data.favorName == null){
                 return;
              }
                else if(newDataBlue.data.favorName.length == 0 ){
                  return;
              }
                else {
                $scope.favors.unshift(newDataBlue.data);
              }
            });
          } else {
            return;
          }
        };
      });
}());
