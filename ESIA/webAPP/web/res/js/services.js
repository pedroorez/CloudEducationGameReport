var services = angular.module('services',[]);

// File upload fatory,
// Generate instances of the fileuploader to send images. Return the a JSON
// with informations about the file uploaded
services.factory('services', ['$http', '$q', function ($http, $q) {
    return{
        updateAsset: function(file, currentGameID, assetId, assetType, assetText, answerId, imageFileId, hash){
            var deferred = $q.defer();
            // wrap data
            var fd = new FormData();
            if (file !== null)
                fd.append('file', file);
            
            // send file to service
            // @RequestMapping(value="/updateAsset/{gameID}/{assetId}/{assettType}/{assetText}/{answerId}/{imageFileId}", method = RequestMethod.POST)
            requestPath = '/ESIa/updateAsset/'+hash+'/'+currentGameID+'/'+assetId+'/'+assetType+'/'+assetText+'/'+answerId+'/'+imageFileId;
            return $http.post(requestPath, fd,
            {
                transformRequest:angular.identity,
                headers:{'Content-Type':undefined}
            })
             // if sucessful set data to the model
            .success(function(responseData){
                    console.log("REQUEST:\n"     + "> CurrentGameID: " + currentGameID + "\n" +
                                                   "> AssetID: "       + assetId + "\n" +
                                                   "> AssetType: "     + assetType + "\n" +
                                                   "> AnwerId: "       + answerId +"\n" +
                                                   "> AssetText: "     + assetText + "\n" +
                                                   "> ImageFileId"     + imageFileId);
                    console.log(responseData);
                    // set a promise for the data about the file uploaded
                    deferred.resolve(responseData);
                    return deferred.promise;
             });
         },
         //***************************************
        getGameData: function(gameId,hash){
            var deferred = $q.defer();
             
            requestPath = '/ESIa/GetGameData/'+hash+'/'+gameId;
            return $http.get(requestPath)
            // if sucessful set data to the model
            .success(function(responseData){
                deferred.resolve(responseData);
                return deferred.promise;
                });
            },
            //************************
        deleteAssetbyId: function(assetId,hash){
            var deferred = $q.defer();
                requestPath = '/ESIa/deleteAsset/'+hash+'/'+assetId;
                return $http.get(requestPath)
                // if sucessful set data to the model
                .success(function(responseData){
                    deferred.resolve(responseData);
                    return deferred.promise;
                });
         },
              //************************
        deleteGameById: function(gameID,hash){
            var deferred = $q.defer();
                requestPath = '/ESIa/deleteGame/'+hash+'/'+gameID;
                return $http.get(requestPath)
                // if sucessful set data to the model
                .success(function(responseData){
                    deferred.resolve(responseData);
                    return deferred.promise;
                });
         },
         ////****************************//
         getFullGameList: function(hash){
                var deferred = $q.defer();

                requestPath = '/ESIa/GetGameList/'+hash+'/'+"mine";
                return $http.get(requestPath)
                // if sucessful set data to the model
                .success(function(responseData){
                    deferred.resolve(responseData);
                    return deferred.promise;
                });
         },
         //****************************//
         createGame: function(hash){
                var deferred = $q.defer();
                requestPath = '/ESIa/createNewGame/'+hash+'/'
                return $http.get(requestPath)
                // if sucessful set data to the model
                .success(function(responseData){
                    deferred.resolve(responseData);
                    return deferred.promise;
                });
         },
         //****************************//
         logUser: function(nickname,password){
                var deferred = $q.defer();
                requestPath = '/ESIa/login/'+nickname+'/'+password
                return $http.get(requestPath)
                // if sucessful set data to the model
                .success(function(responseData){
                    deferred.resolve(responseData);
                    return deferred.promise;
                });
         },
         //****************************//
         createUser: function(nickname,password){
                var deferred = $q.defer();
                requestPath = '/ESIa/createUser/'+nickname+'/'+password
                return $http.get(requestPath)
                // if sucessful set data to the model
                .success(function(responseData){
                    deferred.resolve(responseData);
                    return deferred.promise;
                });
         }
         /// moar?
    }
}]);
