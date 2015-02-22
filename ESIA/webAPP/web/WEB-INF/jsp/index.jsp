<html ng-app="ESIapp" ng-controller="mainapp">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="/ESIa/res/css/style.css"/>
<link rel="stylesheet" href="/ESIa/res/css/Animation.css"/>
<header>
    <title>Educational Space Invaders - Advance Edition</title>
</header>
<body style="padding-top: 50px; 
             background-image: url('http://goo.gl/yaDTmz'); background-repeat: repeat;">
             <!--background-image: url('http://goo.gl/yaDTmz'); background-repeat: repeat;">-->

<!--Navbar-->
<nav class="navbar navbar-inverse navbar-fixed-top">
    <!--Navbar Container-->
    <div class="container-fluid">
        <!--Navbar Header-->
        <div class="navbar-header">
            <a class="navbar-brand">ESIa</a>
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" 
                    data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <!--Navbar Items-->
            <ul class="nav navbar-nav">
                <li ng-class="{active:isActive('/home')}"><a href="#/home">Home</a></li>
                <li ng-class="{active:isActive('/about')}"><a href="#/about">About</a></li>
                <li ng-class="{active:isActive('/API')}"><a href="#/API">API</a></li> 
                <li ng-class="{active:isActive('/game')}"><a href="#/game">Play</a></li> 
                <li ng-show="gamestatus.logged" ng-class="{active:isActive('/gamelist')}"><a href="#/gamelist">Your Games</a></li> 
                <li ng-show="gamestatus.logged && gamestatus.currentGameID" ng-class="{active:isActive('/editor')}"><a href="#/editor">Editor</a></li>
                <li ng-hide="gamestatus.logged" ng-class="{active:isActive('/signup')}"><a href="#/signup">Sign Up</a></li> 
            </ul>
            <!--Login Form-->
            <form class="navbar-form navbar-right" style="margin-right:15px;" ng-hide="gamestatus.logged">
                <div class="form-group">
                    <input type="text" ng-model="nickname" class="form-control" placeholder="Nickname" >
                    <input type="password" ng-model="password" class="form-control" placeholder="Password">
                </div>
                <button type="submit" ng-click="loguser()" class="btn btn-primary">Login</button>
            </form>
            <!--Logoff Button-->
            <form class="navbar-form navbar-right" style="margin-right:15px;" ng-show="gamestatus.logged">
                <button type="submit" ng-click="logoffuser()"  class="btn btn-danger">Logoff</button>
            </form>
            
        </div>
    </div>
</nav>
    
<div class="container-fluid view-animate-container" ng-controller="mainapp">
    <ng-view class="view-animate"></ng-view>
</div>


</body>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.0-beta.1/angular.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.0-beta.1/angular-route.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.0-beta.1/angular-animate.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.0-beta.1/angular-cookies.min.js"></script>
    <script src="/ESIa/res/js/ESIa-app.js"></script>
    <script src="/ESIa/res/js/uploaderApp.js"></script>
    <script src="/ESIa/res/js/services.js"></script>
</html>