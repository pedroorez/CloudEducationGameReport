var ESIapp = angular.module('ESIapp',['uploaderApp', 'ngRoute','services','ngAnimate']);

ESIapp.config(['$routeProvider', function($routeProvider){
    // roteador
    $routeProvider
    // editor case
    .when('/editor',
    {
        templateUrl: '/ESIa/res/htmlpartials/gameeditor.html',
        controller: 'GameEditor'
    })
    // gameslist case
    .when('/gamelist',
    {
        templateUrl: '/ESIa/res/htmlpartials/gamelist.html',
        controller: 'gamelister'
    })
    // gameslist case
    .when('/about',
    {
        templateUrl: '/ESIa/res/htmlpartials/about.html'
    })
    // gameslist case
    .when('/API',
    {
        templateUrl: '/ESIa/res/htmlpartials/API.html'
    })
     // home case
    .when('/home',
    {
        templateUrl: '/ESIa/res/htmlpartials/home.html'
    })
     // home case
    .when('/signup',
    {
        templateUrl: '/ESIa/res/htmlpartials/signup.html',
        controller: 'signup'
    })
     // Game Case
    .when('/game',
    {
        templateUrl: '/ESIa/res/htmlpartials/game.html',
    })
    // root case
//    .when('/',{
//        templateUrl: '/ESIa/res/htmlpartials/displayAll.html',
//        controller: 'DisplayAll'
//    })
    .otherwise({ redirectTo: '/gamelist' });
}]);

ESIapp.controller('mainapp', function($scope,$location, DataManager){
    
    // starts the main controller and get the cookies
    DataManager.getcookies();
    // tab function
    $scope.isActive = function(route) {
        return route === $location.path();
    }
    // bind the logged variable
    $scope.gamestatus = DataManager.isLogged();
    
    // go to initial
    if($scope.gamestatus.logged === false){
        $location.path( "/home" );
        console.log("not logged> to home")
    }
     // login
    $scope.loguser = function(){
         DataManager.loguser($scope.nickname, $scope.password);
     }
     // logoff
     $scope.logoffuser = function(){
         DataManager.logoffuser();
         console.log("logged off")
     }

     
});

ESIapp.controller('signup', function($scope,$location, DataManager){
         // create user
    $scope.createUser= function(nick,pass,passagain, fullname){
        if(pass === passagain){
            DataManager.createUser(nick,pass, fullname);
            console.log("opa, senha okay, criando usuario")
        }}
});

ESIapp.controller('gamelister', function($scope,services,DataManager){
    
     $scope.fullgamelist = DataManager.getFullGameList();
     
     $scope.loadGame = function(gameID){
         DataManager.changeGame(gameID);
     }
     
    $scope.deleteGame = function(gameID,entryKey){
        if (window.confirm("Delete this Game? \n (Others will not be able to download it)")) {
            DataManager.deleteGame(gameID, entryKey);
          }
     }
     
     $scope.createNewGame = function(){
         DataManager.createGame();
     }
});