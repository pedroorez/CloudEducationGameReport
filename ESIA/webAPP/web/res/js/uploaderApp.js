var uploaderApp = angular.module('uploaderApp',['services','ngCookies']);

// datamanager service 
// Concentrate all data from the game beeing edited
uploaderApp.service('DataManager', function($parse,services,$location,$q,$cookieStore){
    // Game Data structure
    var Game = [];
    Game.List = []
    Game.Data = {'gameID':'1',
                'backgroundAsset':[{}],
                'playerAsset':[{}],
                'answerList':[ ],
                'enemyList':[ ] // { 'id':'1', 'filename':'', 'text':'Enemy 1', 'answerID':'1'}
            };
    // fix the data structure
    fixDataStructure = function(){
        if(Game.Data.answerList === null)
            Game.Data.answerList = []
        while(Game.Data.answerList.length < 3)
        {
            i = Game.Data.answerList.length;
            i++
            Game.Data.answerList.push({"assetText":"Answer " + i})
        }
        if(Game.Data.playerAsset === null)
            Game.Data.playerAsset = [{}];
        if(Game.Data.backgroundAsset === null)
            Game.Data.backgroundAsset = [{}];
    };
    // user login function
    loguser = function(nickname,password){
        services.logUser(nickname,password)
        .then(function(response){
            if(response.data.status === "goodpass"){
                $cookieStore.put('hash',response.data);
                Game.Status.userhash = response.data;
                Game.Status.logged = true
                $location.path( "/gamelist" );
                console.log(response.data)
            }
        })
    }
    // currente gameID
    Game.Status = []
    Game.Status.currentGameID = null;
    Game.Status.userhash = [];
    Game.Status.logged = false;
    // is logged?
    this.isLogged = function(){ return Game.Status ;}
    //set current gameId
    this.changeGame = function(gameID){
        Game.Status.currentGameID = gameID;
        services.getGameData(gameID,Game.Status.userhash.hash)
                .then(function(response){
                        Game.Data = [];
                        Game.Data = response.data;  
                        fixDataStructure();
                        console.log(response);
                        console.log("currentGameID: " + Game.Status.currentGameID)
                        $location.path( "/editor" );
                    });
    }
    //deleteGame
    this.deleteGame = function(gameID, key){
        services.deleteGameById(gameID,Game.Status.userhash.hash)
                .then(function(response){
                    Game.List.splice(key,1);
                });
    }
    //data getter
    this.dataGet = function() { return Game; };
    //delete an entry
    this.deleteNode= function(from,entryId,assetId){ 
        if(typeof assetId === 'undefined')
            Game.Data[from].splice(entryId,1)
        else{
            services.deleteAssetbyId(assetId,Game.Status.userhash.hash)
                    .then(function(response){
                         if(response.data) Game.Data[from].splice(entryId,1);   
                        console.log("o asset foi apagado: "+response.data);
                     })}
    }
    // add an empty node
    this.addNode = function(to) { 
        if(Game.Data[to] === null) Game.Data[to] = [];
        Game.Data[to].push( {} ) 
    }
    //update player
    this.updateAsset = function(file,assetId,assetType,assetText,answerId, imageFileId, targetList, targetEntry){
        // set the request parameter to -1 if undefined
        if(typeof assetId === 'undefined') assetId = -1;
        if(typeof answerId === 'undefined') answerId = -1;

        // upload the file
        // function(file, currentGameID, assetId, assetType, assetText, answerId, imageFileId)
        services.updateAsset(file, Game.Status.currentGameID, assetId, assetType, assetText, answerId, imageFileId, Game.Status.userhash.hash)
        // after the upload do this
        .then(function(response){
            // assign result data to the entry
            if (Game.Data[targetList] === null)
                Game.Data[targetList] = [];
            if(response.data !== null)
                Game.Data[targetList][targetEntry] = response.data;
        })
    }
    // log user
    this.loguser = function(nickname,password){
        services.logUser(nickname,password)
            .then(function(response){
                if(response.data.status === "goodpass"){
                    $cookieStore.put('hash',response.data);
                    Game.Status.userhash = response.data;
                    Game.Status.logged = true
                    $location.path( "/gamelist" );
                    console.log(response.data)
                }
                    else alert("Sorry, wrong password");
            });
    }
    // cookie getter
    this.getcookies = function(){
        var hash = $cookieStore.get('hash');
        if(typeof hash !== 'undefined'){
            Game.Status.userhash = hash;
            Game.Status.logged = true;
        }
    }
    // logoff user
    this.logoffuser = function(){
        $cookieStore.remove('hash')
        Game.Status.logged = false;
         $location.path( "/home" );
    }
    // get fullgame list
    this.getFullGameList = function(){
         services.getFullGameList(Game.Status.userhash.hash)
                 .then(function(response){
                    Game.List = response.data;
                }); 
        return Game;
    }
    // get fullgame list
    this.createUser = function(nick,pass, fullname){
        services.createUser(nick,pass,fullname)
                .then(function(promisse){
                    if(promisse.data.userID != -1)
                        {   console.log("logando")
                            loguser(nick,pass);}
                    else{console.log("nao foi possivel logar");
                        alert("Sorry, This username is already been used")}
                    }
                )}
    this.createGame = function(){
        services.createGame(Game.Status.userhash.hash)
                .then(function(response){
                    Game.List.push(response.data)
                })
    }
    this.updateGame = function(){
        services.updateGame(Game.Status.userhash.hash, Game.Status.currentGameID, Game.Data.gameName, Game.Data.description)
                .then(function(response){
                    console.log(response);
                    Game.Data.gameName = response.data.gameName;
                    Game.Data.gameDescription = response.data.description;
                })
        // updateGame: function(hash,gameID,gameName,gameDescription){
    }
    // moar or niet?
    });


// Main GameEditor Controller
uploaderApp.controller('GameEditor', [ '$scope', 'DataManager',function ($scope,DataManager) {
                
               // get the gamedata ref
               $scope.result = DataManager.dataGet() ;
               // aux functions
               $scope.addEnemy = function(){ DataManager.addNode('enemyList')}
               // updateGame
               $scope.updateGameInfo = function(){
                   DataManager.updateGame();
               }
    }
]);

// Directive to alter input file type
// After the file in the inout is changed a request to upload the asset is made
uploaderApp.directive('angularFile',function ($parse,DataManager) {
	return {
		restrict: 'A',
                link: function (scope, element, attrs) {
                    element.bind('change', function(){
                        // upload and set a new asset using the file on this input file
                        // this.setNewAsset = function(file,assetId,assetType,assetText,answerId,imageFileId, listtarge, entrytarget)
                        
                        DataManager.updateAsset(element[0].files[0],
                                                scope.result.Data[scope.attrs.targetList][scope.attrs.entryNo].id,
                                                attrs.assetType,
                                                -1,
                                                -1,
                                                -1, 
                                                scope.attrs.targetList,
                                                scope.attrs.entryNo
                                                )
                    })
		}
	};
})

// Directive to generate a AssetBox where the assets are edited
uploaderApp.directive('assetBox', function(DataManager){
   return {
       restrict: 'E',
       scope: true,
      // scope: {entryNo : '='}, // scope is active to isolate the assetboxes
       templateUrl: '/ESIa/res/htmlpartials/assetbox.html',
       link: function(scope,elem,attrs){
           // get references and add them to the scope
           scope.data = [];
           scope.data.assetEntry = scope.result.Data[attrs.targetList][attrs.entryNo];
           scope.attrs = attrs;
           
           
           // set a click() tot he upload button
           elem.find('button[class="uploadButton btn btn-sm btn-success"]').click(function(){ elem.find('input').click();} )
           // bing a function to the gallery button
           elem.find('button[class="galleryButton btn btn-sm btn-primary"]').click(function(){ console.log("GALLERY OPEN"); } )
           // set a bind of change to the answer 
           elem.find('select').bind('change', function(){ 
           //function(file,assetId,assetType,assetText,answerId,
           // imageFileId, targetList, targetEntry){
               console.log("atualizando");
               scope.updateAsset();
               
           });
           // set a click() to the deletebuttom
           elem.find('button[class="deleteButton btn btn-sm btn-danger"]').click(function(){ 
                if (window.confirm("Delete this Asset? \n (The image still will be available to use)")) {
                    DataManager.deleteNode(attrs.targetList,
                                           attrs.entryNo,
                                           scope.result.Data[attrs.targetList][attrs.entryNo].id);
                    scope.$apply();
            }
           })
           // update text field
           elem.find('input[type="text"]').focusout(function(){ scope.updateAsset(); });          
           
           // update asset function
           scope.updateAsset = function(){
               if(scope.result.Data[attrs.targetList][attrs.entryNo].assetText === null)
                   assetText = -1;
               else
                   assetText = scope.result.Data[attrs.targetList][attrs.entryNo].assetText;
               
               if(scope.result.Data[attrs.targetList][attrs.entryNo].rightans === null)
                   answerId = -1;
               else
                    answerId = scope.result.Data[attrs.targetList][attrs.entryNo].rightans.id
                    DataManager.updateAsset(null,
                                            scope.result.Data[attrs.targetList][attrs.entryNo].id,
                                            attrs.assetType,
                                            assetText,
                                            answerId,
                                            -1, 
                                            attrs.targetList,
                                            attrs.entryNo
            )
           }
           

        }    
   } 
});


